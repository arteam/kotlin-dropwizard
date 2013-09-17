package stats.health

import com.yammer.metrics.core.HealthCheck
import com.yammer.metrics.core.HealthCheck.Result
import javax.inject.Inject
import stats.conf.StatsGatherConfiguration
import org.slf4j.LoggerFactory
import stats.db.StatsDao

/**
 * Date: 9/13/13
 * Time: 11:57 PM
 *
 * @author Artem Prigoda
 */
class DbDataHealthCheck [Inject] (
        val conf: StatsGatherConfiguration,
        val statsDao: StatsDao) : HealthCheck("dbData") {

    val log = LoggerFactory.getLogger(javaClass<DbDataHealthCheck>())!!

    protected override fun check(): HealthCheck.Result? {
        try {
            val lastTime = statsDao.getLastTime();
            log.info("{}", lastTime)
            return Result.healthy()
        } catch(e: Exception){
            log.error("Health check failed", e)
            return Result.unhealthy("template doesn't include a name")
        }
    }
}


