package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Order dao.
 */
public interface OrderDao extends JpaRepository<Order, Long> {

    /**
     * Find by id one order.
     *
     * @param id the id for search
     * @return the optional result of search
     */
    Optional<Order> findById(Long id);

    /**
     * Find by order id and user id one order.
     *
     * @param orderId the order id
     * @param userId  the user id
     * @return the optional result of search
     */
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    /**
     * Find all orders.
     *
     * @param pageable parameters for pagination
     * @return the page of all orders in the database
     */
    Page<Order> findAll(Pageable pageable);

    /**
     * Find count of order records.
     *
     * @return the int number of order records
     */
    long count();

    /**
     * Find orders by user id.
     *
     * @param userId   the user id
     * @param pageable parameters for pagination
     * @return the page of all orders in the database for user
     */
    Page<Order> findByUserId(Long userId, Pageable pageable);

    /**
     * Find count of order records by user id.
     *
     * @param userId the user id
     * @return the int number of order records
     */
    long countByUserId(Long userId);
}
