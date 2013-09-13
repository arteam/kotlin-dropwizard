package stats.health

import com.yammer.metrics.core.HealthCheck
import com.yammer.metrics.core.HealthCheck.Result
import javax.inject.Inject
import stats.conf.StatsGatherConfiguration
import org.slf4j.LoggerFactory

/**
 * Date: 9/13/13
 * Time: 11:57 PM
 *
 * @author Artem Prigoda
 */
class TemplateHealthCheck [Inject] (val conf: StatsGatherConfiguration) : HealthCheck("template") {

    val log = LoggerFactory.getLogger(javaClass<TemplateHealthCheck>())!!

    protected override fun check(): HealthCheck.Result? {
        val result = conf.template.format("TEST")
        log.info(result)
        if(!result.contains("TEST"))
            return Result.unhealthy("template doesn't include a name")
        return Result.healthy()
    }
}


