package com.sjsu.masters.webservice.dao;

import com.sjsu.masters.webservice.model.User;

public interface UserDao {

    public User store(User user) throws Exception;

    public User findByEmail(String email);

    public User findById(long id);

}
