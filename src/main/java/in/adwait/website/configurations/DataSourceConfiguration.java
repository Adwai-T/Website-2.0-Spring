package in.adwait.website.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "${sqlDataSource:classpath:config/datasource}.properties")
public class DataSourceConfiguration {

    @Value("${postgre.username}")
    private String postgre_userName;
    @Value("${postgre.password}")
    private String postgre_password;
    @Value("${postgre.url}")
    private String postgre_url;

//    --- Configure for postgre sql Database
    @Bean(name = "transactionManager")
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactory().getObject());
        return manager;
    }
    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(postgreDataSource());
        factory.setPackagesToScan("in.adwait.website") ;
        factory.setHibernateProperties(hibernateProperites());
        return factory;
    }
    @Bean
    public DataSource postgreDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder
                .url(postgre_url)
                .password(postgre_password)
                .username(postgre_userName);

        return builder.build();
    }
    private final Properties hibernateProperites() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        return hibernateProperties;
    }
}
