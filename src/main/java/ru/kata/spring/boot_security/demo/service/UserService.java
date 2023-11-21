package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> findAll();
    User findOne(int id);
    void update(int id, User updatedUser);
    void updateWithoutPassword(int id, User updatedUser);
    void delete(int id);
    User findByName(String name);
    User findByEmail(String email);
}
