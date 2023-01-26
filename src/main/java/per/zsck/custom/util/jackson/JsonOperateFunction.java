package per.zsck.custom.util.jackson;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.function.Function;

/**
 * @author zsck
 * @date 2023/1/25 - 18:32
 */
@FunctionalInterface
public interface JsonOperateFunction extends Function<JsonNode, JsonNode> {
    static JsonOperateFunction fromPath(JsonPath path) {
        return node -> {
            for (String s : path) {
                node = node.get(s);
            }
            return node;
        };
    }

    static JsonOperateFunction emptyOperate() {
        return node -> node;
    }
}
