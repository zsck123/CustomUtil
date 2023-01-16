package per.zsck.custom.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery;

import java.util.*;
import java.util.function.Function;

/**
 * @author zsck
 * @date 2023/1/14 - 13:42
 */
public class ServiceUtil {

    /**
     * 根据key集合获取实体Map集合
     * key集合从{@link Collection source}中根据{@link Function functionToGetAttribute}获取
     * mp查询的字段和Map的key相同，都由{@link SFunction functionToGetKey}获取
     *
     * @param source                 能获取key的集合
     * @param functionToGetAttribute 从source中获取key的方法
     * @param functionToGetKey       从实体中获取key的方法
     * @param <T>                    source的类型
     * @param <R>                    实体的类型
     * @param <K>                    key的类型
     * @return 实体Map集合
     */
    public static <T, R, K> Map<K, R> getBatchMapByAttributeOf(
            Collection<T> source,
            Function<T, K> functionToGetAttribute,
            SFunction<R, K> functionToGetKey) {

        Set<K> desSet = StreamUtil.getSetOf(source, functionToGetAttribute);

        if (CollectionUtil.isEmpty(desSet)) {
            return Collections.emptyMap();
        }

        return SimpleQuery.keyMap(new LambdaQueryWrapper<R>().in(functionToGetKey, desSet), functionToGetKey);
    }

    /**
     * 根据key集合获取实体List集合
     * key集合从{@link Collection source}中根据{@link Function functionToGetAttribute}获取
     * mp查询的字段为key，由{@link SFunction functionToGetKey}获取
     *
     * @param source                 能获取key的集合
     * @param functionToGetAttribute 从source中获取key的方法
     * @param functionToGetKey       从实体中获取key的方法
     * @param <T>                    source的类型
     * @param <R>                    实体的类型
     * @param <K>                    key的类型
     * @return 实体List集合
     */
    public static <T, R, K> List<R> getBatchListByAttributeOf(
            Collection<T> source,
            Function<T, K> functionToGetAttribute,
            SFunction<R, K> functionToGetKey) {

        Set<K> desSet = StreamUtil.getSetOf(source, functionToGetAttribute);

        if (CollectionUtil.isEmpty(desSet)) {
            return Collections.emptyList();
        }
        return SimpleQuery.selectList(SimpleQuery.getType(functionToGetKey), new LambdaQueryWrapper<R>().in(functionToGetKey, desSet));
    }
}