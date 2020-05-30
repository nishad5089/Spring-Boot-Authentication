package com.bookshop.bookshop.io.repository;

import com.bookshop.bookshop.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String Email);
    UserEntity findByUserId(String userId);
}
