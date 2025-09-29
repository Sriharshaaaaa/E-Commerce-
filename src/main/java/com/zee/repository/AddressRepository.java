package com.zee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zee.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
