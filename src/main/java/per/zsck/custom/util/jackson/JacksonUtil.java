package per.zsck.custom.util.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zsck
 * @date 2023/1/24 - 0:51
 */
@Component
public class JacksonUtil {
    public static ObjectMapper objectMapper;

    public JacksonUtil(ObjectMapper objectMapper) {
        JacksonUtil.objectMapper = objectMapper;
    }

    public static JavaType getListOf(Class<?> elementClasses) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, elementClasses);
    }
}
