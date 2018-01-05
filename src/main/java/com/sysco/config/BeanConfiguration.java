package com.sysco.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class BeanConfiguration {

    @Value("${db.drivername}")
    private String db2Driver;

    @Value("${db.url}")
    private  String dbUrl;

    @Value("${db.username}")
    private String user;

    @Value("${db.password}")
    private  String password;

    @Value("${admin.svc.url}")
    private String adminServiceUrl;

    @Value("${image.svc.url}")
    private String imgSvcUrl;

    @Value("${nutrition.svc.url}")
    private String nutritionSvcUrl;

    @Value("${item.svc.url}")
    private String itemServiceUrl;

    @Value("${product.svc.url}")
    private String productServiceUrl;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setDriverClassName(db2Driver);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public RestTemplate getRestClient(){
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String application_env = StringUtils.isNotEmpty(System.getenv("APPLICATION_ENV")) ?
                        System.getenv("APPLICATION_ENV") : "DEV";
                if ( "PROD".equals(application_env.toUpperCase())){
                    registry.addMapping("/**").allowedOrigins(
                            "sysco.com",
                            "na.sysco.net",
                            "10.239.251.253"
                    ).allowedMethods("*");
                } else {
                    registry.addMapping("/**").allowedOrigins(CrossOrigin.DEFAULT_ORIGINS).allowedMethods("*");
                }

            }
        };
    }

    @Bean( name = "dbUrl")
    public String getDbUrl() {
        return dbUrl;
    }

    @Bean( name = "adminServiceUrl")
    public String getAdminServiceUrl() {
        return adminServiceUrl;
    }

    @Bean( name = "imgSvcUrl")
    public String getImgSvcUrl() {
        return imgSvcUrl;
    }

    @Bean( name = "nutritionSvcUrl")
    public String getNutritionSvcUrl() {
        return nutritionSvcUrl;
    }

    @Bean( name = "itemServiceUrl")
    public String getItemServiceUrl() {
        return itemServiceUrl;
    }

    @Bean( name = "productServiceUrl")
    public String getProductServiceUrl() {
        return productServiceUrl;
    }
}
