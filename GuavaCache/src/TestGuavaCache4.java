
public class TestGuavaCache4 {
	/*其他相关方法

	　　显式插入:该方法可以直接向缓存中插入值，如果缓存中有相同key则之前的会被覆盖。

	cache.put(key, value);
	　　显式清除:我们也可以对缓存进行手动清除。

	cache.invalidate(key); //单个清除
	cache.invalidateAll(keys); //批量清除
	cache.invalidateAll(); //清除所有缓存项
	　　基于时间的移除： 

	expireAfterAccess(long, TimeUnit); 该键值对最后一次访问后超过指定时间再移除
	expireAfterWrite(long, TimeUnit) ;该键值对被创建或值被替换后超过指定时间再移除
	　　基于大小的移除:指如果缓存的对象格式即将到达指定的大小，就会将不常用的键值对从cache中移除。

	cacheBuilder.maximumSize(long)
	　　 size是指cache中缓存的对象个数。当缓存的个数开始接近size的时候系统就会进行移除的操作

	　　缓存清除执行的时间

	　　使用CacheBuilder构建的缓存不会"自动"执行清理和回收工作，也不会在某个缓存项过期后马上清理，
	  也没有诸如此类的清理机制。它是在写操作时顺带做少量的维护工作(清理);如果写操作太少，
	  读操作的时候也会进行少量维护工作。因为如果要自动地持续清理缓存，就必须有一个线程，
	  这个线程会和用户操作竞争共享锁。在某些环境下线程创建可能受限制，这样CacheBuilder就不可用了。*/
}
