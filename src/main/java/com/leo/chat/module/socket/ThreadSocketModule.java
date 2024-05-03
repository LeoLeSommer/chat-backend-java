package com.leo.chat.module.socket;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.leo.chat.dto.Thread.response.MessageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadSocketModule {

    private static final Logger log = LoggerFactory.getLogger(ThreadSocketModule.class);

    private final SocketIONamespace namespace;

    @Autowired
    public ThreadSocketModule(SocketIOServer server) {
        this.namespace = server.addNamespace("/threads");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.debug("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(),
                    handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
        };
    }

    public void broadcastMessage(MessageResponse message) {
        log.debug("Broadcasting chat message '{}'", message);
        namespace.getBroadcastOperations().sendEvent("new_message", message);
    }
}
