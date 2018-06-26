import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
/*
 * �����Ƴ�������

����ͨ��CacheBuilder.removalListener(RemovalListener)�����ǿ�������һ����������
�Ӷ������ڻ��汻�Ƴ�ʱ��һЩ�����Ĳ����������汻�Ƴ�ʱ��
RemovalListener���ȡ�Ƴ�bing֪ͨ[RemovalNotification]��
���а����Ƴ���key��value��RemovalCause��
 */
public class TestGuavaCache3 {
    @Test
    public void testListener() throws ExecutionException {
        CacheLoader<String, Person> loader = new CacheLoader<String, Person>() {
            @Override
            // �����ػ�����û����ʱ������load������ȡ��������������
            public Person load(String key) throws ExecutionException {
                System.out.println(key + " load in cache");
                return getPerson(key);
            }
            // ��ʱһ�����ǻ������ش����絽���ݿ�ȥ��ѯ
            private Person getPerson(String key) throws ExecutionException {
                System.out.println(key + " query");
                return new Person(key, "zhang" + key);
            }
        };

        // remove listener
        RemovalListener<String, Person> removalListener = new RemovalListener<String, Person>() {
            public void onRemoval(RemovalNotification<String, Person> removal) {
                System.out.println("cause:" + removal.getCause() + " key:" + removal.getKey() + " value:"
                        + removal.getValue());
            }
        };

        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()//
                .expireAfterWrite(2, TimeUnit.MINUTES).maximumSize(1024).removalListener(removalListener).build(loader);
        cache.get("1");// ���뻺��
        cache.get("1");// �ڶ��λ�ȡ(��ʱ�ӻ����л�ȡ)
        cache.invalidate("1");// �Ƴ�����
        cache.get("1");// ���»�ȡ
        cache.get("1");// �ٴλ�ȡ(��ʱ�ӻ����л�ȡ)
    }
}