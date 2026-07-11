package genesys.code.orderManagement.service;

import genesys.code.orderManagement.dto.requestDto.NotificationRequestDto;
import genesys.code.orderManagement.dto.responseDto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
@FeignClient(name = "notification-service", url = "${notification.service.url}")
public interface EmailFeignClient {
    @PostMapping("/notification")
    void sendNotification(@RequestBody NotificationRequestDto notificationRequestDto
    );


}
