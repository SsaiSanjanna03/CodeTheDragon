package com.example.demo02.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import com.example.demo02.websocket.GameWebSocketHandler;
import com.example.demo02.websocket.LeaderBoardWebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameWebSocketHandler gameWebSocketHandler;
    private final LeaderBoardWebSocketHandler leaderBoardWebSocketHandler;

    @Autowired
    public WebSocketConfig(GameWebSocketHandler gameWebSocketHandler, LeaderBoardWebSocketHandler leaderBoardWebSocketHandler) {
        this.gameWebSocketHandler = gameWebSocketHandler;
        this.leaderBoardWebSocketHandler = leaderBoardWebSocketHandler;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameWebSocketHandler, "/api/{user_name}")
                .addHandler(leaderBoardWebSocketHandler, "/leaderboard/{user_name}")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        try {
                            String path = request.getURI().getPath();
                            String userName = path.substring(path.lastIndexOf('/') + 1);
                            attributes.put("user_name", userName);
                            return super.beforeHandshake(request, response, wsHandler, attributes);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                });
    }

}


