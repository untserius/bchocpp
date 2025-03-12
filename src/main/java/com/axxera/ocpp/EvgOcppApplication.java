package com.axxera.ocpp;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class })
@EnableTransactionManagement
public class EvgOcppApplication {

	@Value("${http.port}")
	private int httpPort;
	
	public static void main(String[] args) {
		SpringApplication.run(EvgOcppApplication.class, args);
	}
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createStandardConnector());
		return tomcat;
	}

	private Connector createStandardConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setPort(httpPort);
		return connector;
	}
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
	    return (tomcat) -> tomcat.addConnectorCustomizers((connector) -> {
	        if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
	            AbstractHttp11Protocol<?> protocolHandler = (AbstractHttp11Protocol<?>) connector.getProtocolHandler();
	            protocolHandler.setKeepAliveTimeout(600);
	            protocolHandler.setMaxKeepAliveRequests(750);
	            protocolHandler.setUseKeepAliveResponseHeader(true);
	        }
	    });
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());
	}
	private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // Set the connect timeout (time taken to establish a connection)
        factory.setConnectTimeout(5000); // 60 seconds
        // Set the read timeout (time taken to receive data once the connection is established)
        factory.setReadTimeout(5000);    // 60 seconds
        return factory;
    }
	@Bean
	public RestTemplate transactionrestTemplate1() {
		return new RestTemplate(newclientHttpRequestFactory());
	}
	private ClientHttpRequestFactory newclientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // Set the connect timeout (time taken to establish a connection)
        // Set the read timeout (time taken to receive data once the connection is established)
        //factory.setReadTimeout(10000);    // 60 seconds
        return factory;
    }
}
