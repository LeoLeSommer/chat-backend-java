package com.leo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.leo.chat.service.MessageService;
import com.leo.chat.service.ThreadService;

import jakarta.servlet.http.HttpServletRequest;

import com.leo.chat.exception.NotFoundException;
import com.leo.chat.dto.Paginated.response.PaginatedResponse;
import com.leo.chat.dto.Thread.body.CreateThreadBody;
import com.leo.chat.dto.Thread.body.SendMessageBody;
import com.leo.chat.dto.Thread.response.MessageResponse;
import com.leo.chat.dto.Thread.response.ThreadSummaryResponse;
import com.leo.chat.model.User;
import com.leo.chat.module.socket.ThreadSocketModule;
import com.leo.chat.model.Message;

@RestController
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ThreadSocketModule threadSocketModule;

    @GetMapping("/threads")
    public PaginatedResponse<ThreadSummaryResponse, Long> getThreads(
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam("limit") int limit) {
        return threadService.paginate(cursor, limit)
                .mapData((thread) -> new ThreadSummaryResponse(thread));
    }

    @PostMapping("/threads")
    public ThreadSummaryResponse createThread(@RequestBody CreateThreadBody body) {
        return new ThreadSummaryResponse(threadService.createThread(body.getTitle()));
    }

    @GetMapping("/threads/{id}/messages")
    public PaginatedResponse<MessageResponse, Long> getMessages(
            @PathVariable Long id,
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam("limit") int limit) {
        return messageService.paginateByThreadId(id, cursor, limit)
                .mapData((message) -> new MessageResponse(message));
    }

    @PostMapping("/threads/{id}/messages")
    public MessageResponse sendMessage(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody SendMessageBody body) {
        var user = (User) request.getAttribute("user");
        var thread = threadService.getThreadById(id);

        if (thread == null) {
            throw new NotFoundException();
        }

        threadService.updateThread(thread);
        var message = messageService.createMessage(new Message(body.getMessage(), user.getId(), thread.getId()));
        message.setSender(user);

        threadSocketModule.broadcastMessage(new MessageResponse(message));

        return new MessageResponse(message);
    }
}
