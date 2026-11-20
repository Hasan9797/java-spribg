package com.example.restapi.repository;

import com.example.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository interfeysi.
 * 
 * Bu qatlam (Repository Layer) ma'lumotlar bazasi bilan to'g'ridan-to'g'ri ishlaydi (CRUD operatsiyalari).
 * JpaRepository<User, Long> dan meros olgani uchun bizga quyidagi tayyor metodlar keladi:
 *  - save(User user) -> Saqlash yoki yangilash
 *  - findById(Long id) -> ID bo'yicha qidirish
 *  - findAll() -> Barcha yozuvlarni olish
 *  - deleteById(Long id) -> ID bo'yicha o'chirish
 *  - count() -> Jami sonini bilish
 * va boshqa ko'plab qulay metodlar. Biz ularni qo'lda yozib o'tirmaymiz!
 */
@Repository // Bu Spring komponenti ekanligini va ma'lumotlar ombori bilan ishlashini bildiradi
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Foydalanuvchini email orqali topish.
     * Spring Data JPA metod nomiga qarab avtomatik SQL generatsiya qiladi:
     * "SELECT * FROM users WHERE email = ?"
     */
    Optional<User> findByEmail(String email);

    /**
     * Ushbu email bazada bor-yo'qligini tekshirish (true/false qaytaradi).
     * SQL: "SELECT COUNT(*) > 0 FROM users WHERE email = ?"
     */
    boolean existsByEmail(String email);
}
