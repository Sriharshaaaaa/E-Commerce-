package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.model.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

}
