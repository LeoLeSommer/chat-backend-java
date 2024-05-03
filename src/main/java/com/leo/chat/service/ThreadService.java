package com.leo.chat.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.chat.repository.ThreadRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.leo.chat.dto.Paginated.response.PaginatedResponse;
import com.leo.chat.model.Thread;

@Service
public class ThreadService {

    @Autowired
    private ThreadRepository threadRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Get threads ordered by updated_at
    public PaginatedResponse<Thread, Long> paginate(Long cursor, int limit) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Thread.class);
        var root = query.from(Thread.class);
        query.select(root);
        query.orderBy(builder.desc(root.get("updatedAt")));

        if (cursor != null) {
            query.where(builder.lessThan(root.get("updatedAt"), cursor));
        }

        var threads = entityManager.createQuery(query)
                .setFirstResult(0)
                .setMaxResults(limit)
                .getResultList();

        var nextCursor = threads.isEmpty() ? null : threads.get(threads.size() - 1).getUpdatedAt().getTime();
        return new PaginatedResponse<>(threads, nextCursor);
    }

    public Thread getThreadById(Long id) {
        return threadRepository.findById(id).orElse(null);
    }

    public Thread createThread(String title) {
        var thread = new Thread(title);
        threadRepository.save(thread);
        return thread;
    }

    public Thread updateThread(Thread thread) {
        thread.setUpdatedAt(new Date());
        threadRepository.save(thread);
        return thread;
    }
}
