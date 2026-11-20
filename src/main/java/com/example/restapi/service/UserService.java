package com.example.restapi.service;

import com.example.restapi.entity.User;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService klassi (Xizmat ko'rsatish qatlami / Business Logic Layer).
 * 
 * Bu qatlamda asosiy mantiq (biznes logika) yoziladi:
 * - Ma'lumotlarni tekshirish (validatsiya)
 * - Repository bilan bog'lanish
 * - Xatoliklarni boshqarish
 */
@Service // Ushbu klass Spring servisi ekanligini bildiradi (Spring IoC Container buni Bean sifatida boshqaradi)
public class UserService {

    // UserRepository bilan bog'lanish uchun o'zgaruvchi
    private final UserRepository userRepository;

    /**
     * Konstruktor orqali Dependency Injection (Qaramlikni yuklash).
     * Izoh: Spring da @Autowired dan ko'ra konstruktor orqali injection qilish tavsiya etiladi (Best Practice).
     * Chunki bu test yozishni osonlashtiradi va obyektning o'zgarmasligini (final) ta'minlaydi.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * READ (Hammasini olish):
     * Bazadagi barcha foydalanuvchilar ro'yxatini qaytaradi.
     * 
     * @return User lar ro'yxati (List<User>)
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * READ (Bitta foydalanuvchini olish):
     * ID bo'yicha foydalanuvchini qidiradi.
     * Agar topilmasa, ResourceNotFoundException xatoligini otadi.
     * 
     * @param id qidirilayotgan foydalanuvchi ID si
     * @return topilgan User obyekti
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi! ID: " + id));
    }

    /**
     * CREATE (Yangi foydalanuvchi yaratish):
     * Yangi kelgan User obyektini bazaga saqlaydi.
     * 
     * @param user saqlanishi kerak bo'lgan ma'lumotlar
     * @return bazada saqlangan va ID berilgan yangi User obyekti
     */
    public User createUser(User user) {
        // Biznes qoida: agar bu email bilan avval ro'yxatdan o'tilgan bo'lsa, xatolik beramiz
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ushbu email (" + user.getEmail() + ") allaqachon mavjud!");
        }
        return userRepository.save(user);
    }

    /**
     * UPDATE (Mavjud foydalanuvchini yangilash):
     * Berilgan ID bo'yicha bazadan userni topadi va uning qiymatlarini yangilab saqlaydi.
     * 
     * @param id yangilanayotgan foydalanuvchi ID si
     * @param userDetails yangi kelgan ma'lumotlar
     * @return yangilangan User obyekti
     */
    public User updateUser(Long id, User userDetails) {
        // 1. Avval mavjud foydalanuvchini topamiz (topilmasa xatolik beradi)
        User existingUser = getUserById(id);

        // 2. Email o'zgartirilayotgan bo'lsa va yangi email boshqa birovda bor bo'lsa, tekshiramiz
        if (!existingUser.getEmail().equals(userDetails.getEmail()) && 
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalArgumentException("Ushbu email (" + userDetails.getEmail() + ") band!");
        }

        // 3. Maydonlarni yangilaymiz
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setAge(userDetails.getAge());

        // 4. Bazada yangilangan holatini saqlab qaytaramiz
        return userRepository.save(existingUser);
    }

    /**
     * DELETE (Foydalanuvchini o'chirish):
     * Berilgan ID bo'yicha foydalanuvchini bazadan o'chirib tashlaydi.
     * 
     * @param id o'chirilishi kerak bo'lgan ID
     */
    public void deleteUser(Long id) {
        // Avval foydalanuvchi bazada bor-yo'qligini tekshirib olamiz
        User user = getUserById(id);
        // O'chirish
        userRepository.delete(user);
    }
}
