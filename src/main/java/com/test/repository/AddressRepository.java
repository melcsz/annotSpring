package com.test.repository;

import com.test.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
    @Modifying
    @Query("UPDATE Address SET buildingNumber = ?1, city = ?2, street = ?3 where id = ?4")
    void update(String buildingNumber,String city,String street,int id);
}
