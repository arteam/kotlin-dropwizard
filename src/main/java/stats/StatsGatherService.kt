package stats

import com.yammer.dropwizard.Service
import stats.conf.StatsGatherConfiguration
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import stats.resources.StatsGatherResource
import stats.health.TemplateHealthCheck
import com.google.inject.Injector
import com.google.inject.AbstractModule
import com.google.inject.Guice

/**
 * Date: 9/13/13
 * Time: 11:25 PM
 *
 * @author Artem Prigoda
 */
class StatsGatherService : Service<StatsGatherConfiguration>() {

    public override fun initialize(p0: Bootstrap<StatsGatherConfiguration>?) {
        p0?.setName("stats-gather")
    }
    public override fun run(p0: StatsGatherConfiguration?, p1: Environment?) {
        val conf = p0!!
        val env = p1!!
        val injector = Guice.createInjector(object : AbstractModule() {
            protected override fun configure() {
                bind(javaClass<StatsGatherConfiguration>())?.toInstance(conf)
            }
        })!!
        val resource = injector.getInstance(javaClass<StatsGatherResource>())
        val healthCheck = injector.getInstance(javaClass<TemplateHealthCheck>())
        env.addResource(resource)
        env.addHealthCheck(healthCheck)
    }

}

fun main(args: Array<String>) {
    StatsGatherService().run(array("server", "statsDropwizard.yaml"))
}