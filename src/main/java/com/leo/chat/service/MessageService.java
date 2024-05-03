package com.leo.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.chat.dto.Paginated.response.PaginatedResponse;
import com.leo.chat.model.Message;
import com.leo.chat.repository.MessageRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResponse<Message, Long> paginateByThreadId(Long threadId, Long cursor, int limit) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Message.class);
        var root = query.from(Message.class);
        query.select(root);
        query.where(builder.equal(root.get("threadId"), threadId));
        query.orderBy(builder.desc(root.get("createdAt")));

        if (cursor != null) {
            query.where(builder.lessThan(root.get("createdAt"), cursor));
        }

        var messages = entityManager.createQuery(query)
                .setFirstResult(0)
                .setMaxResults(limit)
                .getResultList();

        var nextCursor = messages.isEmpty() ? null : messages.get(messages.size() - 1).getCreatedAt().getTime();
        return new PaginatedResponse<>(messages, nextCursor);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}
