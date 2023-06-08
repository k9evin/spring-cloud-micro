package com.example.service.impl;


import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private List<User> userList;

    @Override
    public boolean createUser(User user) {
        return userList.add(user);
    }

    @Override
    public User getUser(Long id) {
        List<User> findUserList = userList.stream().filter(userItem -> userItem.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        userList.stream().filter(userItem -> userItem.getId().equals(user.getId())).forEach(userItem -> {
            userItem.setUsername(user.getUsername());
            userItem.setPassword(user.getPassword());
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        boolean flag = false;
        User user = getUser(id);
        if (user != null) {
            flag = userList.remove(user);
        }
        return flag;
    }

    @Override
    public User searchUserByUsername(String username) {
        List<User> findUserList = userList.stream().filter(userItem -> userItem.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        }
        return null;
    }

    @Override
    public List<User> searchUsersByIds(List<Long> ids) {
        return userList.stream().filter(userItem -> ids.contains(userItem.getId())).collect(Collectors.toList());
    }

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User(1L, "macro", "123456"));
        userList.add(new User(2L, "andy", "123456"));
        userList.add(new User(3L, "mark", "123456"));
        userList.add(new User(4L, "kevin", "123456"));
    }
}
