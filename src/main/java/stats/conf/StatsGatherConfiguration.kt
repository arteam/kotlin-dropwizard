package stats.conf

import com.yammer.dropwizard.config.Configuration
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty

/**
 * Date: 9/1/13
 * Time: 9:38 PM
 *
 * @author Artem Prigoda
 */
class StatsGatherConfiguration : Configuration() {
    JsonProperty NotEmpty var template : String = ""
    JsonProperty NotEmpty var defaultName : String = "Stranger"
}

