package workflowx.auth_service.config;


import jakarta.xml.bind.annotation.W3CDomHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
/**
 * Clients to connect to a WebSocket server at /ws
 * Clients to send messages to the server at /app
 * Clients to subscribe to messages from the server at /topic and /video
 */
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //Connect to this url to establish a WebSocket connection
        //Allow all origins to connect
        //Use SockJS to allow fallback options for browsers that do not support WebSocket
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/video"); //Client subscribes here
        registry.setApplicationDestinationPrefixes("/app"); //Client sends messages here
    }
}
