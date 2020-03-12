package com.example.demo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class HashController {

    HashRepository repository;

    @PostMapping("/{documentId}/{key}/{content}")
    public ResponseEntity<String> saveLines(@PathVariable UUID documentId,
                                            @PathVariable String key,
                                            @PathVariable String content) {
        repository.upload(documentId.toString(), key, content);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<String> getLines(@PathVariable UUID documentId) {
        return new ResponseEntity(repository.get(documentId.toString()), HttpStatus.OK);
    }
}
