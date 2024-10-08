package ru.dima.solovev.authservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.dima.solovev.authservice.models.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
