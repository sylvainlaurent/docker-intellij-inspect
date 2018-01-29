package example;

import java.util.HashMap;
import java.util.Map;

public class Main {
        public static void main(String[] args) {
            System.out.println("Hello World!");

            Map<String, String> map = new HashMap<>();
            map.put("key1", "value2");
            System.out.println(map.get("notExistingKey").toLowerCase());
        }
}
