package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Models.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
