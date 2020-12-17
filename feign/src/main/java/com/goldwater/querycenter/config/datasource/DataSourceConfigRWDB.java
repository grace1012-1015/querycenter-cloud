package com.goldwater.querycenter.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(basePackages = "com.goldwater.querycenter.dao.rwdb", sqlSessionTemplateRef  = "rwdbSqlSessionTemplate")
public class DataSourceConfigRWDB {
    @Bean(name = "rwdbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.rwdb")
    public DataSource rwdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "rwdbSqlSessionFactory")
    public SqlSessionFactory rwdbSqlSessionFactory(@Qualifier("rwdbDataSource") DataSource dataSource, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/rwdb/*.xml"));
        //bean.setConfiguration(mybatisProperties.getConfiguration());
        return bean.getObject();
    }

    @Bean(name = "rwdbTransactionManager")
    public DataSourceTransactionManager rwdbTransactionManager(@Qualifier("rwdbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "rwdbSqlSessionTemplate")
    public SqlSessionTemplate rwdbSqlSessionTemplate(@Qualifier("rwdbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
