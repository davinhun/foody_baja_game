package com.foody.promo.repository;

import com.foody.promo.entity.TeamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<TeamEntity, Long> {


    Optional<TeamEntity> findByName(String name);

    Optional<TeamEntity> findByNameAndIdNot(String name, Long id);
}
