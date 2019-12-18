package com.egebakken.springdemo.person;

import org.springframework.data.jpa.repository.JpaRepository;

interface PersonRepo extends JpaRepository<Person, Long> {
}
