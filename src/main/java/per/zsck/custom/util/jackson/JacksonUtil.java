package per.zsck.custom.util.jackson;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zsck
 * @date 2023/1/24 - 0:51
 */
@Slf4j
@SuppressWarnings("unused")
@Component
public class JacksonUtil implements ApplicationRunner {
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

    public static <T> T readValue(Object content, JavaType valueType) {
        return readValue(content, valueType, JsonOperateFunction.emptyOperate());
    }


    public static <T> T readValue(Object content, JavaType valueType, JsonOperateFunction operate) {

        try {
            String realContent = operate == null ? toJsonString(content) : toJsonString(operate.apply(readTree(content)));
            return StrUtil.isBlank(realContent)? null : objectMapper.readValue(realContent, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(Object content, JavaType valueType, JsonPath path){
        return  readValue(content, valueType, JsonOperateFunction.fromPath(path));
    }


    public static <T> List<T> readListValue(Object content, Class<T> valueType) {
        return readValue(content, getListOf(valueType));
    }
    public static <T> List<T> readListValue(Object content, Class<T> valueType, JsonOperateFunction operate) {
        return readValue(content, getListOf(valueType), operate);
    }
    public static <T> List<T> readListValue(Object content, Class<T> valueType, JsonPath path) {
        return readValue(content, getListOf(valueType), path);
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

    @Override
    public void run(ApplicationArguments args) {
        SpringUtil.unregisterBean("objectMapper");
        SpringUtil.unregisterBean("jacksonUtil");
        log.info("JacksonUtil init success, unregister objectMapper and jacksonUtil");
    }

}
