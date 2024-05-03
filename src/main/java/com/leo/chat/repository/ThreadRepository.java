package com.leo.chat.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leo.chat.model.Thread;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
}
