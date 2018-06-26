import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
/*
 * Callback��ʽ
 */
public class TestGuavaCache2 {
    

    @Test
    public void testUserCallback() throws ExecutionException {
        // ģ������
        final List<Person> list = new ArrayList<Person>(5);
        list.add(new Person("1", "zhangsan"));
        list.add(new Person("2", "lisi"));
        list.add(new Person("3", "wangwu"));

        final String key = "1";
        Cache<String, Person> cache2 = CacheBuilder.newBuilder().maximumSize(1000).build();
        /**
         * �û����е�get����������������ʱֱ�ӷ��ؽ��;����ͨ��������Callable��call������ȡ�������������档<br/>
         * ������һ��cache���󻺴���ֲ�ͬ�����ݣ�ֻ�贴����ͬ��Callable���󼴿ɡ�
         */
        Person person = cache2.get(key, new Callable<Person>() {
            public Person call() throws ExecutionException {
                System.out.println(key + " load in cache");
                return getPerson(key);
            }

            // ��ʱһ�����ǻ������ش����絽���ݿ�ȥ��ѯ
            private Person getPerson(String key) throws ExecutionException {
                System.out.println(key + " query");
                for (Person p : list) {
                    if (p.getId().equals(key))
                        return p;
                }
                return null;
            }
        });
        System.out.println("======= sencond time  ==========");
        person = cache2.getIfPresent(key);
        person = cache2.getIfPresent(key);
    }
}