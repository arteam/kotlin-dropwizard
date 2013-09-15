package stats.db

import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.Bind
import stats.core.Watch
import java.util.Date

/**
 * Date: 9/15/13
 * Time: 10:15 PM
 *
 * @author Artem Prigoda
 */
trait StatsDao {

    SqlUpdate("""insert into watches(channel_id, program_id, household_id, start_time, duration)
    values (:channel_id, :program_id, :household_id, :start_time, :duration)""")
   fun insert(Bind("channel_id") channelId: Long, Bind("program_id") programId: Long,
              Bind("household_id") householdId: Long, Bind("start_time") startTime: Date,
              Bind("duration") duration: Int)
}