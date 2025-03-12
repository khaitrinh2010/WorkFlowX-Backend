package workflowx.auth_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflowx.auth_service.entity.ChatMessage;
import workflowx.auth_service.entity.User;
import workflowx.auth_service.repository.ChatMessageRepository;
import workflowx.auth_service.repository.UserRepository;

import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ChatMessage> getMessagesByGroupId(Long groupId) {
        return chatMessageRepository.findByStudyGroup_IdOrderByTimestampAsc(groupId);
    }

    public void saveMessage(ChatMessage message) {
        Long senderId = message.getSenderId();
        User sender = userRepository.findById(senderId).orElse(null);
        String username = sender != null ? sender.getUsername() : "Unknown";
        message.setSenderName(username);
        chatMessageRepository.save(message);
    }
}
