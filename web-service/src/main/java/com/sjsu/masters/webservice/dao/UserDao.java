package com.example.webservice.dao;

import com.example.webservice.model.User;

public interface UserDao {

    public User store(User user) throws Exception;

    public User findByEmail(String email);

    public User findById(long id);

}
