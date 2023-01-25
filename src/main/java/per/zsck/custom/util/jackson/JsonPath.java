package per.zsck.custom.util.jackson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author zsck
 * @date 2023/1/25 - 18:19
 */
@SuppressWarnings("unused")
public class JsonPath extends ArrayList<String> {

    private JsonPath(@NotNull Collection<? extends String> c) {
        super(c);
    }

    public static JsonPath of(String... paths) {
       return new JsonPath(Arrays.asList(paths));
   }
}
