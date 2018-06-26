import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
/*
 * 关于移除监听器

　　通过CacheBuilder.removalListener(RemovalListener)，我们可以声明一个监听器，
从而可以在缓存被移除时做一些其他的操作。当缓存被移除时，
RemovalListener会获取移除bing通知[RemovalNotification]，
其中包含移除的key、value和RemovalCause。
 */
public class TestGuavaCache3 {
    @Test
    public void testListener() throws ExecutionException {
        CacheLoader<String, Person> loader = new CacheLoader<String, Person>() {
            @Override
            // 当本地缓存命没有中时，调用load方法获取结果并将结果缓存
            public Person load(String key) throws ExecutionException {
                System.out.println(key + " load in cache");
                return getPerson(key);
            }
            // 此时一般我们会进行相关处理，如到数据库去查询
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
        cache.get("1");// 放入缓存
        cache.get("1");// 第二次获取(此时从缓存中获取)
        cache.invalidate("1");// 移除缓存
        cache.get("1");// 重新获取
        cache.get("1");// 再次获取(此时从缓存中获取)
    }
}