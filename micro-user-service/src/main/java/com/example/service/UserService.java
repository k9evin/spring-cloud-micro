package com.example.service;


import com.example.domain.User;

import java.util.List;

public interface UserService {

    boolean createUser(User user);

    User getUser(Long id);

    void updateUser(User user);

    boolean deleteUser(Long id);

    User searchUserByUsername(String username);

    List<User> searchUsersByIds(List<Long> ids);
}
