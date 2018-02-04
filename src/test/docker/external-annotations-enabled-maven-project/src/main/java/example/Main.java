package example;

import com.google.common.base.MoreObjects;
import org.apache.commons.collections4.ListUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value2");
        //inspection should detect a potential NPE if external annotations are taken into account for JDK
        System.out.println(map.get("notExistingKey").toLowerCase());

        //inspection should detect that firstNonNull always fails according to (external) contract
        System.out.println(MoreObjects.firstNonNull((String) null, null));

    }

    @org.eclipse.jdt.annotation.NonNull
    private static Object nullableMethod() {
        if (new Random().nextInt() % 2 == 0) {
            //if the next statement has the error "null is returned by the method declared as @NonNull",
            //then IJ correctly interprets eclipse annotation, as set in .idea/misc.xml

            //if the error is "null is returned by the method which is not declared as @Nullable",
            //then the eclipse annotation is not interpreted correctly
            return null;
        } else {
            return "not null";
        }
    }

    private static void foo() {
        System.out.println(ListUtils.defaultIfNull(null, null).size());
    }
}
