package per.zsck.custom.util.mybatis

import cn.hutool.core.collection.CollectionUtil
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import per.zsck.custom.util.StreamUtil
import java.util.function.Function
import kotlin.reflect.KProperty1

@Suppress("unused")
object ServiceUtilKt{
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
     * @author zsck
     * @date   2023/2/9 - 14:15
     */

    inline fun <T, K, reified R : Any> IService<R>.getBatchMapByAttributeOf(
        source: Collection<T>,
        functionToGetAttribute: Function<T, K>,
        functionToGetKey: KProperty1<R, K>,
    ):  MutableMap<K, R> {
        val desSet = StreamUtil.getSetOf(source, functionToGetAttribute)

        return if (CollectionUtil.isEmpty(desSet)) {
            HashMap()
        } else {
            // 根据functionToGetKey从R中获取属性值作为Key
            return this.list(KtQueryWrapper(R::class.java).`in`(functionToGetKey, desSet)).associateByTo(HashMap(), functionToGetKey)
        }
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
    inline fun <T, reified R: Any, K> IService<R>.getBatchListByAttributeOf(
        source: Collection<T>?,
        functionToGetAttribute: Function<T, K>,
        functionToGetKey: KProperty1<R, K>
    ): MutableList<R> {
        val desSet = StreamUtil.getSetOf(source, functionToGetAttribute)
        return if (CollectionUtil.isEmpty(desSet)) {
            ArrayList()
        } else {
            // 根据functionToGetKey从R中获取属性值作为Key
            return this.list(KtQueryWrapper(R::class.java).`in`(functionToGetKey, desSet))
        }
    }

    fun IService<*>.test(){
        println("test")
    }
}
