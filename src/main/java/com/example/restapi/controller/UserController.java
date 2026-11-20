package com.example.restapi.controller;

import com.example.restapi.entity.User;
import com.example.restapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserController klassi (API / Controller Layer).
 * 
 * Bu klass tashqi dunyodan (Frontend, Postman, Mobil ilova) keladigan HTTP so'rovlarni qabul qiladi
 * va ularni mos servis metodlariga yo'naltiradi, so'ngra natijani JSON formatida qaytaradi.
 */
@RestController // Ushbu klass REST API controller ekanligini bildiradi (javoblar avtomatik JSON formatga o'giriladi)
@RequestMapping("/api/users") // Barcha metodlar uchun umumiy boshlang'ich URL: http://localhost:8080/api/users
public class UserController {

    // Servis qatlamini chaqirib olish
    private final UserService userService;

    /**
     * Konstruktor orqali UserService ni ineksiya qilish (Constructor Injection).
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 1. Barcha foydalanuvchilarni olish.
     * URL: GET http://localhost:8080/api/users
     * 
     * @GetMapping - HTTP GET so'rovini ushlab oladi.
     * ResponseEntity - HTTP javobi (Status kodi + Body qismi)ni chiroyli boshqarish uchun qobiq (wrapper).
     * 
     * @return 200 OK kodi bilan foydalanuvchilar ro'yxati (JSON)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        // HttpStatus.OK -> 200 kodini qaytaradi
        return ResponseEntity.ok(users);
    }

    /**
     * 2. ID bo'yicha bitta foydalanuvchini olish.
     * URL: GET http://localhost:8080/api/users/1
     * 
     * @PathVariable - URL ichidagi o'zgaruvchini ({id}) metod parametriga bog'laydi.
     * 
     * @param id olinayotgan foydalanuvchi ID si
     * @return 200 OK kodi bilan User obyekti
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 3. Yangi foydalanuvchi yaratish (qo'shish).
     * URL: POST http://localhost:8080/api/users
     * 
     * @PostMapping - HTTP POST so'rovini ushlab oladi.
     * @RequestBody - mijozdan kelayotgan JSON ma'lumotni Java obyektiga (User ga) aylantirib beradi.
     * 
     * @param user so'rov tanasida (body) kelgan ma'lumot
     * @return 201 CREATED status kodi bilan yangi yaratilgan User obyekti
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        // Yangi ma'lumot yaratilganda 201 Created status kodi qaytarish REST standartiga mos keladi
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * 4. Foydalanuvchi ma'lumotlarini tahrirlash (yangilash).
     * URL: PUT http://localhost:8080/api/users/1
     * 
     * @PutMapping - HTTP PUT so'rovini ushlab oladi (to'liq yangilash uchun).
     * 
     * @param id yangilanayotgan foydalanuvchi ID si
     * @param user yangi qiymatlar (JSON body dan keladi)
     * @return 200 OK kodi bilan yangilangan User obyekti
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * 5. Foydalanuvchini o'chirish.
     * URL: DELETE http://localhost:8080/api/users/1
     * 
     * @DeleteMapping - HTTP DELETE so'rovini ushlab oladi.
     * 
     * @param id o'chirilishi kerak bo'lgan ID
     * @return 204 NO_CONTENT yoki 200 OK (xabar bilan)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        // Foydalanuvchi muvaffaqiyatli o'chirilgani haqida xabar qaytaramiz
        return ResponseEntity.ok("Foydalanuvchi (ID: " + id + ") muvaffaqiyatli o'chirildi!");
    }
}
