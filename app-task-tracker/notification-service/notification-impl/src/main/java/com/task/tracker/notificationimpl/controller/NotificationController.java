package com.task.tracker.notificationimpl.controller;

import com.task.tracker.notificationapi.dto.NotificationResponse;
import com.task.tracker.notificationapi.controller.NotificationApi;
import com.task.tracker.notificationimpl.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationController implements NotificationApi {
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<List<NotificationResponse>> findAllByAccountId(@PathVariable UUID accountId) {
        log.info("Find all notifications by account id: {}", accountId);
        return ResponseEntity.ok(notificationService.findAllByAccountId(accountId));
    }

    @Override
    public ResponseEntity<Void> markAsSend(
            @PathVariable UUID accountId,
            @PathVariable UUID notificationId
    ) {
        notificationService.markAsRead(accountId, notificationId);
        return ResponseEntity.noContent().build();
    }
}
