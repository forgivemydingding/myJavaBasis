import java.util.*;

/**
 * @author by liu.hongda
 * @Description TODO
 * @Date 2019/6/24 16:35
 */

public class AllMap {

    public static void main(String[] args) {

        // 定义HashMap,用来测试，结果是随机无序的，线程不同步，不安全
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("d", "ddd");
        map.put("b", "bbb");
        map.put("a", "aaa");
        map.put("c", "ccc");
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            System.out.println("map.get(key) is :" + map.get(key));
        }

        // 定义HashTable,用来测试,结果是随机无序的,但它支持线程同步
        Hashtable<String, String> tab = new Hashtable<String, String>();
        tab.put("d", "ddd");
        tab.put("b", "bbb");
        tab.put("a", "aaa");
        tab.put("c", "ccc");
        Iterator<String> iterator_1 = tab.keySet().iterator();
        while (iterator_1.hasNext()) {
            Object key = iterator_1.next();
            System.out.println("tab.get(key) is :" + tab.get(key));
        }

        // 定义TreeMap,用来测试,结果是有序的
        TreeMap<String, String> tmp = new TreeMap<String, String>();
        tmp.put("d", "ddd");
        tmp.put("b", "bbb");
        tmp.put("a", "aaa");
        tmp.put("c", "ccc");
        Iterator<String> iterator_2 = tmp.keySet().iterator();
        while (iterator_2.hasNext()) {
            Object key = iterator_2.next();
            System.out.println("tmp.get(key) is :" + tmp.get(key));
        }
    }
}
