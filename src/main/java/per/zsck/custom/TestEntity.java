package per.zsck.custom;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zsck
 * @date 2023/1/17 - 14:50
 */
@Data
public class TestEntity implements TestInterface, Serializable {
    private String name;
}
