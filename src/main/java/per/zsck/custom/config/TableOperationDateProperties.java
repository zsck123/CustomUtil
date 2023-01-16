package per.zsck.custom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zsck
 * @date 2023/1/16 - 20:00
 */
@Data
@ConfigurationProperties(prefix = "per.zsck.custom-util.table-operation-date")
public class TableOperationDateProperties {
    private boolean enable = false;
    private String createDateField = "createDate";
    private String updateDateField = "updateDate";
}
