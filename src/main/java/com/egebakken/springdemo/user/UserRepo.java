package com.egebakken.springdemo.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

interface UserRepo extends CrudRepository<User, Long> {

    @Query("select case when count(*)> 0 then true else false end from user where username = :username")
    boolean existsByUserName(String username);
}
