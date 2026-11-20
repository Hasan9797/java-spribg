package com.example.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Ma'lumot topilmagan holatlar uchun maxsus Exception (xatolik) klassi.
 * 
 * @ResponseStatus(HttpStatus.NOT_FOUND) annotatsiyasi tufayli,
 * bu xatolik yuz berganda server avtomatik ravishda HTTP 404 (Not Found) status kodini qaytaradi.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Xatolik xabarini qabul qiluvchi konstruktor.
     * Masalan: "User not found with id: 1"
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
