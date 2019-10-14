package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.DicoCDM.class.getName());
            createCache(cm, com.mycompany.myapp.domain.DicoCDM.class.getName() + ".pointEmports");
            createCache(cm, com.mycompany.myapp.domain.DicoCDM.class.getName() + ".sas");
            createCache(cm, com.mycompany.myapp.domain.DicoCDM.class.getName() + ".aircraftConfs");
            createCache(cm, com.mycompany.myapp.domain.DicoCDM.class.getName() + ".maintenanceConfs");
            createCache(cm, com.mycompany.myapp.domain.DicoCDM.class.getName() + ".maintenaceConfCombinations");
            createCache(cm, com.mycompany.myapp.domain.PointEmport.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PointEmport.class.getName() + ".eqpts");
            createCache(cm, com.mycompany.myapp.domain.Eqpt.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Eqpt.class.getName() + ".cdms");
            createCache(cm, com.mycompany.myapp.domain.Eqpt.class.getName() + ".bis");
            createCache(cm, com.mycompany.myapp.domain.Eqpt.class.getName() + ".tds");
            createCache(cm, com.mycompany.myapp.domain.Cdm.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cdm.class.getName() + ".libCdms");
            createCache(cm, com.mycompany.myapp.domain.Cdm.class.getName() + ".aircraftConfRefs");
            createCache(cm, com.mycompany.myapp.domain.LibCdm.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Bi.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Bi.class.getName() + ".tds");
            createCache(cm, com.mycompany.myapp.domain.Td.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Bus.class.getName());
            createCache(cm, com.mycompany.myapp.domain.AircraftConf.class.getName());
            createCache(cm, com.mycompany.myapp.domain.MaintenanceConf.class.getName());
            createCache(cm, com.mycompany.myapp.domain.MaintenanceConfCombination.class.getName());
            createCache(cm, com.mycompany.myapp.domain.MaintenanceConfCombination.class.getName() + ".maintenanceConfRefs");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
