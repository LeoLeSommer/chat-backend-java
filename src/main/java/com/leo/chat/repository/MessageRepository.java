package com.leo.chat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leo.chat.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
