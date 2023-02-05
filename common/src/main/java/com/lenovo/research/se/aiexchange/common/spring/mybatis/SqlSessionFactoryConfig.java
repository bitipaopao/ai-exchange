package com.lenovo.research.se.aiexchange.common.spring.mybatis;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
@ConditionalOnProperty(prefix = "mw.mybatis", name = "enable", havingValue = "true")
public class SqlSessionFactoryConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${mw.mybatis.config-location:config/mybatis-config.xml}")
    private String myBatisConfigPath;
    @Value("${mw.mybatis.mapper-locations:classpath:mapper/*.xml}")
    private String mapperXMLConfigPath;
    @Value("${mw.mybatis.type-aliases-package}")
    private String mapperPackagePath;

    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource", name = "url")
    public SqlSessionFactoryBean createSqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        mybatisLocalConfig(sqlSessionFactoryBean, myBatisConfigPath);
        mybatisMapperConfig(sqlSessionFactoryBean, mapperXMLConfigPath);
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(mapperPackagePath);

        return sqlSessionFactoryBean;
    }

    private void mybatisMapperConfig(SqlSessionFactoryBean sqlSessionFactoryBean, String resourcePath) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(resourcePath);
            sqlSessionFactoryBean.setMapperLocations(resources);
        } catch (FileNotFoundException e) {
            logger.warn("No resource files available for mybatis mapper " +
                    "with resource path:[" + resourcePath + "]");
        }
    }

    private void mybatisLocalConfig(SqlSessionFactoryBean sqlSessionFactoryBean, String resourcePath) {
        if (resourcePath.startsWith(PathMatchingResourcePatternResolver.CLASSPATH_URL_PREFIX)) {
            resourcePath = resourcePath.replaceFirst(PathMatchingResourcePatternResolver.CLASSPATH_URL_PREFIX, "");
        }
        ClassPathResource locationConfigResource = new ClassPathResource(resourcePath);
        if (locationConfigResource.exists()) {
            sqlSessionFactoryBean.setConfigLocation(locationConfigResource);
        } else {
            logger.warn("No resource files available for mybatis location config " +
                    "with resource path:[" + resourcePath + "]");
        }
    }
}
