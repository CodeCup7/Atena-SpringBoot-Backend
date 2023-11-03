package server.atena.repositories;

import org.springframework.data.repository.CrudRepository;

import server.atena.models.Queue;

public interface QueueRepository extends CrudRepository<Queue, Long> {

}
