package com.cxx.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存泄露例子
 */
public class KeyLessEntry {
    static class Key {
        Integer id;

        Key(Integer id) {
            this.id = id;
        }

        /**
         * 这里会导致OOM
         * 因为这里用的是Integer 对象，对象的hashCode 即使是同一个数值，但是是不同的对象
         * 这个解决方案就是：重写equals 方法
         * @return
         */
        @Override
        public int hashCode() {
            return id.hashCode();
        }


        @Override
        public boolean equals(Object obj) {
            // 解决方法
            boolean response  = false;
            if ( obj instanceof  Key) {
                response =  ((Key) obj).id.equals(this.id);
            }
            return  response;
        }
    }

    public static void main(String[] args) {
        Map m = new HashMap<>();
        while (true) {
            for (int i = 0; i < 10000; i++) {
                // 由于hashCode 的问题，这里会将所有的放置进去。（不会匹配到一起的情况） => 解决方案：重写equals 方法。
                if (!m.containsKey(new Key(0))) {
                    m.put(new Key(i), "Number:" + i);
                }
            }
            System.out.println("m.size() = " + m.size());
        }
    }
}
