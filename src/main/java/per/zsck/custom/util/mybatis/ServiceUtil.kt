package per.zsck.custom.util.mybatis

import cn.hutool.core.collection.CollectionUtil
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.toolkit.support.SFunction
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery
import per.zsck.custom.util.StreamUtil
import java.util.function.Function
import kotlin.reflect.KProperty

/**
 * @author zsck
 * @date   2023/2/9 - 14:15
 */
//inline fun <T, K, reified R : Any> IService<R>.getBatchMapByAttributeOf(
//    source: Collection<T>,
//    functionToGetAttribute: Function<T, K> ,
//    functionToGetKey: KProperty<R>,
//): Map<K, R> {
//    val desSet = StreamUtil.getSetOf(source, functionToGetAttribute)
//
//
//    return if (CollectionUtil.isEmpty(desSet)) {
//        emptyMap()
//    } else {
////        y<R, K>
//        return this.list(KtQueryWrapper(R::class.java).`in`(functionToGetKey, desSet))
//            .associateBy {
//                functionToGetKey.
//            }
////        SimpleQuery.keyMap(KtQueryWrapper(R::class.java).`in`(functionToGetKey, desSet), functionToGetKey)
//    }
//
//}