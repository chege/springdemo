package com.egebakken.springdemo.user;

import org.springframework.data.repository.CrudRepository;

interface UserRepo extends CrudRepository<User, Long> {

}
