package com.goldwater.querycenter.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.mapper.autoconfigure.MybatisProperties;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.goldwater.querycenter.dao.ycdb", sqlSessionTemplateRef  = "ycdbSqlSessionTemplate")
public class DataSourceConfigYCDB {
    @Bean(name = "ycdbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ycdb")
    public DataSource ycdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ycdbSqlSessionFactory")
    public SqlSessionFactory ycdbSqlSessionFactory(@Qualifier("ycdbDataSource") DataSource dataSource, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/ycdb/*.xml"));
        //bean.setConfiguration(mybatisProperties.getConfiguration());
        return bean.getObject();
    }

    @Bean(name = "ycdbTransactionManager")
    public DataSourceTransactionManager ycdbTransactionManager(@Qualifier("ycdbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ycdbSqlSessionTemplate")
    public SqlSessionTemplate ycdbSqlSessionTemplate(@Qualifier("ycdbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
