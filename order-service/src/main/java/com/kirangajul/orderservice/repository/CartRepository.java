package com.kirangajul.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kirangajul.orderservice.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}