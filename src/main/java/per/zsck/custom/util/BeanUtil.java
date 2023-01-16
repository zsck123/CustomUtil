package per.zsck.custom.util;

import org.springframework.beans.BeanUtils;

/**
 * @author zsck
 * @date 2023/1/14 - 15:36
 */
public class BeanUtil {

    public static <T> T copyProperties(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
