package com.task.tracker.notificationimpl.mapper;

import com.task.tracker.notificationapi.dto.NotificationResponse;
import com.task.tracker.notificationimpl.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);
}

