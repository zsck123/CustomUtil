package per.zsck.custom.util;


import cn.hutool.core.collection.CollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zsck
 * @date 2023/1/14 - 13:27
 */
public class StreamUtil {
    public static <T, R> Set<R> getSetOf(Collection<T> collection, Function<T, R> function) {
        if (CollectionUtil.isEmpty(collection)) {
            return Collections.emptySet();
        }
        return collection.stream().map(function).collect(Collectors.toSet());
    }
}
