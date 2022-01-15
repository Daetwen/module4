package com.epam.esm.dao;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * Find user by login.
     *
     * @param login the login for search
     * @return the result of search
     */
    User findByLogin(String login);

    /**
     * Find user by id.
     *
     * @param id the id for search
     * @return the optional result of search
     */
    Optional<User> findById(Long id);

    /**
     * Find all users.
     *
     * @param pageable parameters for pagination
     * @return the page of all users in the database
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Find count of user records.
     *
     * @return the number of user records
     */
    long count();
}
