package NotificationService.Notifciation.controller;

import NotificationService.Notifciation.dto.NotificationRequestDto;
import NotificationService.Notifciation.serviceimpl.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class NotificationController {
    private final  NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping("/notification")
    public void setNotificationService(@RequestBody NotificationRequestDto notificationRequestDto) {
        notificationService.sendOrderConfirmation(notificationRequestDto);
    }
}
