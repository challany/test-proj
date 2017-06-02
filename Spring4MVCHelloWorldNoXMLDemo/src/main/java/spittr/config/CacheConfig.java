package spittr.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	@CacheEvict
	public EhCacheCacheManager cacheManager(CacheManager cm) {
		return new EhCacheCacheManager();
	}

	@Bean
	@Cacheable(value="spittleCache", unless="#result.message.contains('NoCache')", condition="#id >= 10")
	public EhCacheManagerFactoryBean cache() {
		return null;

	}
}

