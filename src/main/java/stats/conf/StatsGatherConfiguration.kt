package stats.conf

import com.yammer.dropwizard.config.Configuration
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty
import com.yammer.dropwizard.db.DatabaseConfiguration
import javax.validation.constraints.NotNull
import javax.validation.Valid

/**
 * Date: 9/1/13
 * Time: 9:38 PM
 *
 * @author Artem Prigoda
 */
class StatsGatherConfiguration : Configuration() {
    JsonProperty NotNull Valid
            val database: DatabaseConfiguration = DatabaseConfiguration()
}

