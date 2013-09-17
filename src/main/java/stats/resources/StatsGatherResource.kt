package stats.resources

import java.util.concurrent.atomic.AtomicLong
import java.util.Date
import com.google.common.base.Optional
import javax.ws.rs.QueryParam
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed
import javax.ws.rs.Path
import javax.ws.rs.Produces
import stats.conf.StatsGatherConfiguration
import javax.inject.Inject
import javax.ws.rs.POST
import stats.core.Watch
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder
import com.yammer.dropwizard.db.DatabaseConfiguration
import stats.db.StatsDao

/**
 * Date: 9/13/13
 * Time: 11:03 PM
 *
 * @author Artem Prigoda
 */
Path("/addWatch")
Produces("application/json")
class StatsGatherResource
    [Inject] (val newConf: StatsGatherConfiguration, val newStatsDao: StatsDao)  {

    val conf: StatsGatherConfiguration = newConf
    val statsDao : StatsDao = newStatsDao
    val counter: AtomicLong = AtomicLong()

    POST
    fun sayHello(watch: Watch): Response {
        insert(watch)
        return Response.ok()?.build()!!
    }


    fun insert(watch: Watch) {
       statsDao.insert(watch.channelId, watch.programId, watch.householdId, Date(watch.startTime) ,watch.duration)
    }

}