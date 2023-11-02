package server.atena.repositories;

import org.springframework.data.repository.CrudRepository;

import server.atena.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}