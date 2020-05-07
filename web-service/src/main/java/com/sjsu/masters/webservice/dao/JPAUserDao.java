package com.sjsu.masters.webservice.dao;

import com.sjsu.masters.webservice.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JPAUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User store(User user) throws Exception {
        return entityManager.merge(user);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        Query query = entityManager.createNativeQuery("SELECT * FROM user where email LIKE :email", User.class);
        query.setParameter("email",email);
        if(query.getResultList().isEmpty()) return null;
        return (User)query.getResultList().get(0);
    }

    @Override
    @Transactional
    public User findById(long id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM user where id LIKE :id", User.class);
        query.setParameter("id",id);
        if(query.getResultList().isEmpty()) return null;
        return (User)query.getResultList().get(0);
    }

}
