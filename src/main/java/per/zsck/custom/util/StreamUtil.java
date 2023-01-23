package per.zsck.custom.util;


import cn.hutool.core.collection.CollectionUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zsck
 * @date 2023/1/14 - 13:27
 */
public class StreamUtil {
    public static <T, R> Set<R> getSetOf(Collection<T> collection, Function<T, R> function) {
        return CollectionUtil.isEmpty(collection) ? Collections.emptySet()
                : collection.stream()
                .map(function)
                .collect(Collectors.toSet());
    }
    public static <T, R> List<R> getListOf(Collection<T> collection, Function<T, R> function) {
        return CollectionUtil.isEmpty(collection) ? Collections.emptyList()
                : collection.stream()
                .map(function)
                .collect(Collectors.toList());
    }

    /**
     * K, V 都在同一集合中
     */
    public static <S, K, V> Map<K, V> getMapOf(Collection<S> collection, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        return CollectionUtil.isEmpty(collection) ? Collections.emptyMap()
                : collection.stream()
                .collect(Collectors.toMap(keyFunction, valueFunction));
    }
}
