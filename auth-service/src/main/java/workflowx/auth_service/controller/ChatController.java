package workflowx.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import workflowx.auth_service.entity.ChatMessage;
import workflowx.auth_service.service.ChatMessageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        chatMessageService.saveMessage(message);
        return message;
    }

    @GetMapping("/api/chat/history/{groupId}")
    public ResponseEntity<?> getChatHistory(@PathVariable Long groupId) {
        return ResponseEntity.ok(chatMessageService.getMessagesByGroupId(groupId));
    }
}
