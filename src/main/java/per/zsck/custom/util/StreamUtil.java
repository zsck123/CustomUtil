package per.zsck.custom.util;


import cn.hutool.core.collection.CollectionUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zsck
 * @date 2023/1/14 - 13:27
 */
@SuppressWarnings("unused")
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
     * K从集合中取出，V为集合中的元素
     */
    public static <S, K> Map<K, S> getMapOf(Collection<S> collection, Function<S, K> keyFunction) {
        return CollectionUtil.isEmpty(collection) ? Collections.emptyMap()
                : collection.stream()
                .collect(Collectors.toMap(keyFunction, Function.identity()));
    }

    /**
     * K, V 都从集合中取出
     */
    public static <S, K, V> Map<K, V> getMapOf(Collection<S> collection, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        return CollectionUtil.isEmpty(collection) ? Collections.emptyMap()
                : collection.stream()
                .collect(Collectors.toMap(keyFunction, valueFunction));
    }

    /**
     * K, V 都从集合中取出, K可重复
     */
    public static <S, K, V> Map<K, Set<V>> getKeysSetMapOf(Collection<S> collection, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        return CollectionUtil.isEmpty(collection) ? Collections.emptyMap()
                : collection.stream()
                .collect(Collectors.toMap(keyFunction, entity->{
                    Set<V> set = new HashSet<>();
                    set.add(valueFunction.apply(entity));
                    return set;
                }, (oldValue, newValue)->{
                    oldValue.addAll(newValue);
                    return oldValue;
                }));
    }
    /**
     * K, V 都从集合中取出, K可重复
     */
    public static <S, K, V, VK> Map<K, Map<VK, V>> getKeysMapMapOf(Collection<S> collection,
                                                           Function<S, K> keyFunction,
                                                           Function<S, V> valueFunction,
                                                           Function<V, VK> valueKeyFunction) {
        return CollectionUtil.isEmpty(collection) ? Collections.emptyMap()
                : collection.stream()
                .collect(Collectors.toMap(keyFunction, entity->{
                    Map<VK, V> map = new HashMap<>();
                    V v = valueFunction.apply(entity);
                    map.put(valueKeyFunction.apply(v), v);
                    return map;
                }, (oldValue, newValue)->{
                    oldValue.putAll(newValue);
                    return oldValue;
                }));
    }
}
