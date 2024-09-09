package com.exemplo.forum.repository;

import com.exemplo.forum.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
