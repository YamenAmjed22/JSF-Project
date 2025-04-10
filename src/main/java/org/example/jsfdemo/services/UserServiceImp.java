package org.example.jsfdemo.services;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.jsfdemo.entities.User;

import java.util.Collections;
import java.util.List;

@Stateless
public class UserServiceImp implements UserService {
    @PersistenceContext(unitName = "TOBY_PU")
    private EntityManager entityManager;

    @Override
    public void addUser(User newUser) {
            entityManager.persist(newUser);
    }

    @Override
    public User findUserByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }    }

    @Override
    public List<User> findAllUsers() {
        try {
            return entityManager.createQuery("SELECT u FROM User u", User.class)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void updateUser(User loggedInUser) {
        entityManager.merge(loggedInUser);


    }

}
