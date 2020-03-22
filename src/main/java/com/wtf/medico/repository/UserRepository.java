package com.wtf.medico.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wtf.medico.entity.User;

public interface UserRepository extends MongoRepository<User, String>{

}
