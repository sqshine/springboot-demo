# springboot-demo
spring boot 示例程序

配置druid多数据源的方法
application.yml
spring
    datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://192.168.1.71:3306/spring_boot_test
        username: root
        password: root
        initialSize: 5
        minIdle: 5
        maxActive: 20
        maxWait: 6000
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
    datasource1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://192.168.1.71:3306/spring_boot_test
        username: root
        password: root
        initialSize: 10
        minIdle: 10
        maxActive: 200
        maxWait: 600
        timeBetweenEvictionRunsMillis: 60
        minEvictableIdleTimeMillis: 300
        validationQuery: SELECT 'X'
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000

编写配置类
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DruidConfiguration {

    @Primary
    @Bean(name = "aaa")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                //additional customizations
                .build();
    }

    @Bean(name = "bbb")
    @ConfigurationProperties(prefix = "spring.datasource1")
    public DataSource dataSource2(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                //additional customizations
                .build();
    }
}