package com.exemplo.forum.config;

import com.exemplo.forum.model.Message;
import com.exemplo.forum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    // Conjunto sincronizado para armazenar sessões WebSocket ativas
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Adiciona a nova sessão ao conjunto
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove a sessão quando a conexão for fechada
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // Lógica para processar a mensagem recebida
        // Salve a mensagem no banco de dados
        Message msg = new Message();
        msg.setContent(payload);
        // Aqui você pode adicionar um nome de usuário ou outras informações se necessário
        messageService.saveMessage(msg);

        // Envie a mensagem para todos os clientes conectados
        synchronized (sessions) {
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage(payload));
            }
        }
    }
}
