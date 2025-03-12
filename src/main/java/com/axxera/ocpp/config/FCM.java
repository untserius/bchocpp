package com.axxera.ocpp.config;
 
import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
 
@Configuration
public class FCM {
	@Value("${google.service}")
	private String googleService;
	
	@Value("${total.Connections}")
	private final int MAX_TOTAL_CONNECTIONS;
	
	@Value("${connection.Per.Route}")
	private final int MAX_CONNECTIONS_PER_ROUTE ;
	
	@Autowired
	public FCM(@Value("${connection.Per.Route}") int MAX_CONNECTIONS_PER_ROUTE,@Value("${total.Connections}") int MAX_TOTAL_CONNECTIONS) {
	    this.MAX_CONNECTIONS_PER_ROUTE = MAX_CONNECTIONS_PER_ROUTE;
		this.MAX_TOTAL_CONNECTIONS = MAX_TOTAL_CONNECTIONS;
	}
 
    private PoolingHttpClientConnectionManager connectionManager;
    private CloseableHttpClient httpClient;
    public synchronized PoolingHttpClientConnectionManager getInstance() {
        try {
			if (connectionManager == null) {
				connectionManager = new PoolingHttpClientConnectionManager();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return connectionManager;
    }
 
    @PostConstruct
    public void initialize() {
        // Create a connection manager with pooling
    	connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_ROUTE);
 
        // Create the HTTP client using the connection manager
        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
        HttpTransport httpTransport = new ApacheHttpTransport((HttpClient) httpClient);
 
        try {
            if (FirebaseApp.getApps().isEmpty()) {
				FileInputStream serviceAccount = new FileInputStream(googleService);
				FirebaseOptions options = FirebaseOptions.builder()
						.setHttpTransport(httpTransport)
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.build();
				FirebaseApp.initializeApp(options);
			} else {
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Bean
    public FirebaseApp firebaseApp() {
        return FirebaseApp.getInstance();
    }
    
    @Bean
	public ApnsConfig apns() {
		return ApnsConfig.builder()
				.setAps(Aps.builder().setAlert(ApsAlert.builder().setTitle("BC Hydro").setBody("").build())
						.setMutableContent(true).setContentAvailable(true).setSound("default").build())
				.build();
	}
	
	@Bean
	public AndroidConfig android() {
		return AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build();
	}
 
//    @PreDestroy
//    public void cleanup() throws IOException {
//        httpClient.close(); // Close the HttpClient when the application context is destroyed
//    }
}