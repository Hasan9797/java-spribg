package com.example.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * User Entity (Model) klassi.
 * 
 * Bu klass ma'lumotlar bazasidagi "users" jadvaliga (table) mos keladi.
 * Har bir obyekt (User instance) bazadagi bitta qator (row) ma'lumotni ifodalaydi.
 */
@Entity // Bu klass ma'lumotlar bazasidagi jadval bilan bog'lanishini bildiradi
@Table(name = "users") // Baza jadvalining nomi "users" bo'ladi (odatiy holatda class nomi olinadi)
public class User {

    /**
     * Foydalanuvchining unikal identifikatori (Primary Key).
     * @Id - jadvaldagi asosiy kalit (primary key) ekanligini bildiradi.
     * @GeneratedValue - ID qiymati avtomatik ravishda 1, 2, 3... qilib oshib boradi (Auto Increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Foydalanuvchi ismi.
     * nullable = false - bu maydon bo'sh (null) bo'lishi mumkin emas.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Foydalanuvchi elektron pochtasi.
     * unique = true - bazada ikkita bir xil email bo'lishi mumkin emas.
     * nullable = false - majburiy maydon.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Foydalanuvchi yoshi.
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Standart bo'sh konstruktor.
     * JPA (Hibernate) bazadan ma'lumotlarni o'qib, obyekt yasashida bo'sh konstruktor shart!
     */
    public User() {
    }

    /**
     * Parametrli konstruktor.
     * Yangi User yaratishda qulaylik uchun.
     */
    public User(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    /**
     * Barcha maydonlar bilan konstruktor.
     */
    public User(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // ==========================================
    // Getter va Setter metodlari
    // (Maydonlar private bo'lgani uchun ularga qiymat berish va olish uchun kerak)
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Obyektni matn (String) shaklida ko'rish uchun toString metodi.
     * Konsolda log chiqarishda juda qo'l keladi.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
