import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
/*
 * CacheLoader��ʽ
 */
public class TestGuavaCache {

    @Test
    public void testUserCacheLoader() throws ExecutionException {
        // ģ������
        final List<Person> list = new ArrayList<Person>(5);
        list.add(new Person("1", "zhangsan"));
        list.add(new Person("2", "lisi"));
        list.add(new Person("3", "wangwu"));

        // ����cache
        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()//
                .refreshAfterWrite(1, TimeUnit.MINUTES)// ����ʱ����û�б���/д���ʣ�����ա�
                // .expireAfterWrite(5, TimeUnit.SECONDS)//����ʱ����û��д���ʣ�����ա�
                // .expireAfterAccess(3, TimeUnit.SECONDS)// �������ʱ��Ϊ3��
                .maximumSize(100).// ���û������
                build(new CacheLoader<String, Person>() {
                    @Override
                    /**  �����ػ�����û����ʱ������load������ȡ��������������
                     */
                    public Person load(String key) throws ExecutionException {
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

        System.out.println(cache.get("1"));
        cache.get("2");
        cache.get("3");
        System.out.println("======= sencond time  ==========");
        cache.get("1");
        cache.get("2");
        cache.get("3");
        try {
			Thread.sleep(61000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("======= third time  ==========");
        cache.get("1");
        cache.get("2");
        cache.get("3");
    }
}