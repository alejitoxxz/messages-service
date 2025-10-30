package co.edu.uco.messageservice.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.messageservice.catalog.Message;
import co.edu.uco.messageservice.catalog.MessageCatalog;

@RestController
@RequestMapping("/messages/api/v1/messages")
public class MessageController {

    @GetMapping("/{code}")
    public ResponseEntity<Message> getMessage(@PathVariable String code) {
        var msg = MessageCatalog.get(code);
        return new ResponseEntity<>(msg, (msg == null) ? HttpStatus.NOT_FOUND : HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Message>> getAllMessages() {
        return new ResponseEntity<>(MessageCatalog.getAll(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Message> upsertMessage(@PathVariable String code, @RequestBody Message message) {
        message.setCode(code);
        MessageCatalog.upsert(message);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Message> deleteMessage(@PathVariable String code) {
        var msg = MessageCatalog.remove(code);
        return new ResponseEntity<>(msg, (msg == null) ? HttpStatus.NOT_FOUND : HttpStatus.ACCEPTED);
    }
}
