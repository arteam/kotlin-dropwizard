package stats.db

import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.Bind
import stats.core.Watch
import java.util.Date
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.sqlobject.customizers.Mapper

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

    SqlQuery("""select max(start_time) as max_start_time from watches""")
    Mapper(javaClass<DateMapper>())
    fun getLastTime(): Date
}

class DateMapper : ResultSetMapper<Date> {
    public override fun map(index: Int, r: ResultSet?, ctx: StatementContext?): Date? {
        return r?.getDate("max_start_time")
    }

}