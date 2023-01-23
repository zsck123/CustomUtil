package per.zsck.custom.config;

import cn.hutool.core.io.file.PathUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 关于表的createDate和updateDate字段自动填充的默认实现
 * @author zsck
 * @date 2023/1/16 - 19:56
 */
@ConditionalOnProperty(prefix = "per.zsck.custom-util.table-operation-date", name = "enable", havingValue = "true")
@EnableConfigurationProperties(TableOperationDateProperties.class)
public class TableOperationDateAutoConfiguration {
    private static TableOperationDateProperties properties;

    public TableOperationDateAutoConfiguration(TableOperationDateProperties properties) {
        TableOperationDateAutoConfiguration.properties = properties;
    }

    @Configuration
    static class TableOperationDateMetaObjectHandler implements MetaObjectHandler {


        @Override  //在执行mybatisPlus的insert()时，为我们自动给某些字段填充值，这样的话，我们就不需要手动给insert()里的实体类赋值了
        public void insertFill(MetaObject metaObject) {
            //其中方法参数中第一个是前面自动填充所对应的字段，第二个是要自动填充的值。第三个是指定实体类的对象
            this.setFieldValByName(properties.getCreateDateField(), new Date(), metaObject);
            this.setFieldValByName(properties.getUpdateDateField(), new Date(), metaObject);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName(properties.getUpdateDateField(), new Date(), metaObject);
        }
    }
}
