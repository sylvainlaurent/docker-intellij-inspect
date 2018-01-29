package example;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value2");
        //inspection should detect a potential NPE if external annotations are taken into account for JDK
        System.out.println(map.get("notExistingKey").toLowerCase());

        //inspection should detect that firstNonNull always fails according to (external) contract
        System.out.println(MoreObjects.firstNonNull((String)null, null));
    }
}
