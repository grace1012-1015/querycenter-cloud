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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.mapper.autoconfigure.MybatisProperties;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.goldwater.querycenter.dao.rw", sqlSessionTemplateRef  = "rwSqlSessionTemplate")
public class DataSourceConfigRW {
    @Bean(name = "rwDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.rw")
    @Primary
    public DataSource rwDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "rwSqlSessionFactory")
    @Primary
    public SqlSessionFactory rwSqlSessionFactory(@Qualifier("rwDataSource") DataSource dataSource, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/rw/*.xml"));
        //bean.setConfiguration(mybatisProperties.getConfiguration());
        return bean.getObject();
    }

    @Bean(name = "rwTransactionManager")
    @Primary
    public DataSourceTransactionManager rwTransactionManager(@Qualifier("rwDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "rwSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate rwSqlSessionTemplate(@Qualifier("rwSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
