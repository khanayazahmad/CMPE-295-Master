package com.sjsu.masters.webservice.service;

import com.sjsu.masters.webservice.dao.UserDao;
import com.sjsu.masters.webservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findByEmail(String email)
    {
        return userDao.findByEmail(email);
    }

    public User findById(long id)
    {
        return userDao.findById(id);
    }
}
