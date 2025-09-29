package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.model.HomeCatagory;
@Repository
public interface HomeCategoryRepository extends JpaRepository<HomeCatagory, Long> {

}
