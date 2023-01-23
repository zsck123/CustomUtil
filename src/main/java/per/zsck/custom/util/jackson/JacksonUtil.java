package per.zsck.custom.util.jackson;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @author zsck
 * @date 2023/1/24 - 0:51
 */
@SuppressWarnings("unused")
@Component
public class JacksonUtil {
    public static ObjectMapper objectMapper;

    public JacksonUtil(ObjectMapper objectMapper) {
        JacksonUtil.objectMapper = objectMapper;
    }

    public static CollectionType getListOf(Class<?> elementClasses) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, elementClasses);
    }

    public static JsonNode readTree(Object content){
        try {
            String realContent = toJsonString(content);
            return objectMapper.readTree(realContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return NullNode.getInstance();
    }


    public static <T> T readValue(Object content, Class<T> valueType) {

        try {
            String  realContent = JacksonUtil.toJsonString(content);
            return StrUtil.isBlank(realContent)? null : objectMapper.readValue(realContent, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(Object content, JavaType valueType, Function<JsonNode, JsonNode> operate) {

        try {
            JsonNode jsonNode = readTree(content);
            String realContent = toJsonString(operate.apply(jsonNode));
            return StrUtil.isBlank(realContent)? null : objectMapper.readValue(realContent, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(Object content, JavaType valueType) {

        try {
            String  realContent = JacksonUtil.toJsonString(content);
            return StrUtil.isBlank(realContent)? null : objectMapper.readValue(realContent, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T readListValue(Object content, Class<T> valueType) {
        return readValue(content, getListOf(valueType));
    }


    public static String toJsonString(Object object) {

        if (object == null) {
            return StrUtil.EMPTY_JSON;
        }

        try {
            return object instanceof String ?(String) object : objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StrUtil.EMPTY_JSON;
    }

}
