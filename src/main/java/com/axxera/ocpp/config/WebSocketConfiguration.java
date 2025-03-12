package com.axxera.ocpp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.axxera.ocpp.handler.WebSocketClientInterceptor;
import com.axxera.ocpp.handler.WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Autowired
	private WebSocketHandler webSocketHandler;

	@Autowired
	private WebSocketClientInterceptor addressInterCeptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "/ocpp/{clientId}").setAllowedOrigins("*")
				.addInterceptors(addressInterCeptor).addInterceptors(new HttpSessionHandshakeInterceptor());
	}
	
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
	ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(32768);
		container.setMaxBinaryMessageBufferSize(32768);
		container.setMaxSessionIdleTimeout(java.time.Duration.ofDays(1).toMillis());
	return container;
	}
	
	

}