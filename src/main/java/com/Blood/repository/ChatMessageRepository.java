package com.Blood.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.Blood.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    @Query("SELECT c FROM ChatMessage c WHERE (c.sender = ?1 AND c.receiver = ?2) OR (c.sender = ?2 AND c.receiver = ?1) ORDER BY c.timestamp ASC")
    List<ChatMessage> findChatHistory(String user1, String user2);

    @Query("SELECT c.sender, c.senderName, MAX(c.timestamp) FROM ChatMessage c WHERE c.sender != 'admin' GROUP BY c.sender, c.senderName ORDER BY MAX(c.timestamp) DESC")
    List<Object[]> findActiveChatUsers();
}
