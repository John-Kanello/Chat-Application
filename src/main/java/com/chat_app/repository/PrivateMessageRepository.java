package com.chat_app.repository;

import com.chat_app.model.entity.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    @Query(value = """
            SELECT * FROM private_messages
            WHERE (sender = :sender AND receiver = :receiver)
            OR (sender = :receiver AND receiver = :sender)
            ORDER BY timestamp ASC
            """, nativeQuery = true)
    List<PrivateMessage> findAllForSenderAndReceiver(@Param("sender") String sender, @Param("receiver") String receiver);
}
