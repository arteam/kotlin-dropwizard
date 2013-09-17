package stats

import com.yammer.dropwizard.Service
import stats.conf.StatsGatherConfiguration
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import stats.resources.StatsGatherResource
import stats.health.DbDataHealthCheck
import com.google.inject.Injector
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.yammer.dropwizard.jdbi.DBIFactory
import stats.db.StatsDao
import com.yammer.dropwizard.migrations.MigrationsBundle
import com.yammer.dropwizard.db.DatabaseConfiguration

/**
 * Date: 9/13/13
 * Time: 11:25 PM
 *
 * @author Artem Prigoda
 */
class StatsGatherService : Service<StatsGatherConfiguration>() {

    public override fun initialize(p0: Bootstrap<StatsGatherConfiguration>?) {
        val bootstrap = p0!!
        bootstrap.setName("stats-gather")
        bootstrap.addBundle(object : MigrationsBundle<StatsGatherConfiguration> (){
            override fun getDatabaseConfiguration(configuration: StatsGatherConfiguration?)
                    : DatabaseConfiguration? {
               return configuration?.database
            }
        })
    }

    public override fun run(p0: StatsGatherConfiguration?, p1: Environment?) {
        val conf = p0!!
        val env = p1!!
        val injector = Guice.createInjector(object : AbstractModule() {
            val jdbi = DBIFactory().build(env, conf.database, "stg")!!

            protected override fun configure() {
                bind(javaClass<StatsGatherConfiguration>())?.toInstance(conf)
                bind(javaClass<StatsDao>())?.toInstance(jdbi.onDemand(javaClass<StatsDao>()))
            }
        })!!
        val resource = injector.getInstance(javaClass<StatsGatherResource>())
        val healthCheck = injector.getInstance(javaClass<DbDataHealthCheck>())
        env.addResource(resource)
        env.addHealthCheck(healthCheck)

    }

}

fun main(args: Array<String>) {
    val dbParams = array("db", "migrate" , "statsDropwizard.yaml")
    val server = array("server" , "statsDropwizard.yaml")
    StatsGatherService().run(server)
}