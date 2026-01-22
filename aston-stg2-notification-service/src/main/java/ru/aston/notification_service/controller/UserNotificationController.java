package ru.aston.notification_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.kafka.dto.UserNotifyDTO;
import ru.aston.notification_service.service.UserNotificationService;

@RestController
@RequestMapping("/user-notification")
public class UserNotificationController {

    private final UserNotificationService notificationService;

    public UserNotificationController(UserNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String>  createNotify(@PathVariable Long id, @RequestBody UserNotifyDTO userDTO) {
        notificationService.notifyForCreation(id,  userDTO);
        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>   update(@PathVariable Long id, @RequestBody UserNotifyDTO userDTO) {
        notificationService.notifyForDeletion(id,  userDTO);
        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }
}
