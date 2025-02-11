package com.foody.promo.repository;

import com.foody.promo.entity.BetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BetRepository extends CrudRepository<BetEntity, Long> {

    List<BetEntity> findAllByAppUserId(Long id);

    List<BetEntity> findAllByMatchId(Long id);

    Optional<BetEntity> findByMatchIdAndAppUserId(Long matchId, Long appUserId);

}
