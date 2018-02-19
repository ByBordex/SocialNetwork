package com.uniovi.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.uniovi.entites.User;

public interface UsersRepository extends Neo4jRepository<User, Long> {

}
