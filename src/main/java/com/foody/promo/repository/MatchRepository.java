package com.foody.promo.repository;

import com.foody.promo.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    List<MatchEntity> findAllByStatusOrderByStartDate(String status);

    List<MatchEntity> findAllByStatusInOrderByStartDate(List<String> statuses);

}
