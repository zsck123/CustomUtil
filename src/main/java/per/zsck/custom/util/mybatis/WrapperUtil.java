package per.zsck.custom.util.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * @author zsck
 * @date 2023/2/12 - 12:32
 */
@SuppressWarnings("unused")
public class WrapperUtil {

    public static <T, R> Wrapper<T> getWrapperByProperty(SFunction<T, R> function, Object value){
        return new LambdaQueryWrapper<T>().eq(function, value);
    }
}
