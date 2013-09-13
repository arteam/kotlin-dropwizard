package stats.resources

import java.util.concurrent.atomic.AtomicLong
import com.google.common.base.Optional
import stats.core.Saying
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import javax.ws.rs.Path
import javax.ws.rs.Produces
import stats.conf.StatsGatherConfiguration
import javax.inject.Inject

/**
 * Date: 9/13/13
 * Time: 11:03 PM
 *
 * @author Artem Prigoda
 */
Path("/stats-gather")
Produces("application/json")
 class StatsGatherResource [Inject] (
    val confParam: StatsGatherConfiguration)  {
    val conf: StatsGatherConfiguration = confParam
    val counter: AtomicLong = AtomicLong()

    GET
    Timed
    fun sayHello(QueryParam("name") name : String?) : Saying {
        return Saying(counter.incrementAndGet(),
                conf.template.format(name?:conf.defaultName))
    }

}