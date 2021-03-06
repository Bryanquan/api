package com.cn.api;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.cn.api.mapper")
@EnableCaching
@EnableScheduling
@ServletComponentScan
public class ApplicationStarter implements CommandLineRunner {
    @Value("${server.httpPort}")
    private Integer httpPort;

    @Value("${server.port}")
    private Integer httpsPort;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                //Due to CONFIDENTIAL and /*, this will cause Tomcat to redirect every request to HTTPS.
                //You can configure multiple patterns and multiple constraints if you need more control over what is and is not redirected.
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;

    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        //Set the scheme that will be assigned to requests received through this connector
        //@param scheme The new scheme
        connector.setScheme("http");
        //Set the port number on which we listen for requests.
        // @param port The new port number
        connector.setPort(httpPort);

        //Set the secure connection flag that will be assigned to requests received through this connector.
        //@param secure The new secure connection flag
        //if connector.setSecure(true),the http use the http and https use the https;else if connector.setSecure(false),the http redirect to https;
        connector.setSecure(true);

        //redirectPort The redirect port number (non-SSL to SSL)
        //connector.setRedirectPort(httpsPort);
        return connector;
    }

    @Override
    public void run(String... arg0) {
        // TODO Auto-generated method stub
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
