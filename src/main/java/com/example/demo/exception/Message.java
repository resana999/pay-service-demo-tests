package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Message extends RuntimeException {
    private String message;
    private String code;
}
