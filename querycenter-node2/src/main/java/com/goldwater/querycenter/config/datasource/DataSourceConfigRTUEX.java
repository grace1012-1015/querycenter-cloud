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
@MapperScan(basePackages = "com.goldwater.querycenter.dao.rtuex", sqlSessionTemplateRef  = "rtuexSqlSessionTemplate")
public class DataSourceConfigRTUEX {
    @Bean(name = "rtuexDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.rtuex")
    public DataSource rtuexDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "rtuexSqlSessionFactory")
    public SqlSessionFactory rtuexSqlSessionFactory(@Qualifier("rtuexDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/rtuex/*.xml"));
        //bean.setConfiguration(mybatisProperties.getConfiguration());
        return bean.getObject();
    }

    @Bean(name = "rtuexTransactionManager")
    public DataSourceTransactionManager rtuexTransactionManager(@Qualifier("rtuexDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "rtuexSqlSessionTemplate")
    public SqlSessionTemplate rtuexSqlSessionTemplate(@Qualifier("rtuexSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
