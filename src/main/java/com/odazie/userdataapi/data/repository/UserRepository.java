package com.odazie.userdataapi.data.repository;


import com.odazie.userdataapi.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User , Long> {
}
