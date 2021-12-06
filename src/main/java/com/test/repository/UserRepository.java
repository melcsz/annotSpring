package com.test.repository;

import com.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Modifying
    @Query("UPDATE User SET email = ?1, name = ?2, password = ?3 where id = ?4")
    void update(String email,String name,String password,int id);

    @Query("select u from User u where u.email = ?1")
    User getByEmail(String email);

    List<User> getAllByName(String name);

    User getByPasswordAndEmail(String password, String email);
}
