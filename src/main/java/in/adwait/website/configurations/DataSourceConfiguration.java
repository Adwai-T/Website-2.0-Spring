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
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "${sqlDataSource:classpath:config/datasource}.properties")
public class DataSourceConfiguration {

//    --- from datasource.properties
    @Value("${postgre.username}")
    private String postgre_userName;
    @Value("${postgre.password}")
    private String postgre_password;
    @Value("${postgre.url}")
    private String postgre_url;
    @Value("${postgre.uri}")
    private String postgreUri;

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

//        String url = "jdbc:postgresql://Host:Port+Path?sslmode=require";
//        -- Local Postgre server
        String url = postgre_url;
        String password = postgre_password;
        String username = postgre_userName;

//        -- if on server use environment variables
        try{
            URI postgre = new URI(postgreUri);
            url = "jdbc:postgresql://" + postgre.getHost()
                    + ':' + postgre.getPort() + postgre.getPath()
                    + "?sslmode=require";
            password = postgre.getUserInfo().split(":")[1];
            username = postgre.getUserInfo().split(":")[0];

            System.out.println("-----> " + url);

        }catch (URISyntaxException e) {
            System.out.println("Postgre URL could not be fetched or is null.");
            e.printStackTrace();
        }

        DataSourceBuilder builder = DataSourceBuilder.create();
        builder
                .url(url)
                .password(password)
                .username(username);

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
