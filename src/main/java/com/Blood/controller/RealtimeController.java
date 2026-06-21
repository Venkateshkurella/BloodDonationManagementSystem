package com.Blood.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.Blood.entity.ChatMessage;
import com.Blood.repository.ChatMessageRepository;
import com.Blood.service.RealtimeService;

@RestController
@RequestMapping("/api")
public class RealtimeController {

    @Autowired
    private RealtimeService realtimeService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/realtime/subscribe")
    public SseEmitter subscribe() {
        return realtimeService.subscribe();
    }

    @PostMapping("/chat/send")
    public ChatMessage sendMessage(
            @RequestParam String sender,
            @RequestParam String receiver,
            @RequestParam String message,
            @RequestParam String senderName) {
        
        ChatMessage msg = new ChatMessage(0, sender, receiver, message, senderName, System.currentTimeMillis());
        ChatMessage saved = chatMessageRepository.save(msg);
        
        // Broadcast the chat message to all connected clients
        realtimeService.broadcast("chat-message", saved);
        return saved;
    }

    @GetMapping("/chat/history")
    public List<ChatMessage> getChatHistory(
            @RequestParam String user1,
            @RequestParam String user2) {
        return chatMessageRepository.findChatHistory(user1, user2);
    }

    @GetMapping("/chat/active-users")
    public List<Map<String, Object>> getActiveChatUsers() {
        List<Object[]> results = chatMessageRepository.findActiveChatUsers();
        List<Map<String, Object>> users = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("sender", row[0]);
            map.put("senderName", row[1]);
            map.put("lastTimestamp", row[2]);
            users.add(map);
        }
        return users;
    }
}
