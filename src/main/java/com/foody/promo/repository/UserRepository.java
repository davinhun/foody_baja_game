package com.foody.promo.repository;

import com.foody.promo.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByOauthId(Long id);

    List<UserEntity> findAll(Sort sort);

    List<UserEntity> findAllByBannedIsFalse(Sort sort);

    Optional<UserEntity> findByEmailIgnoreCase(String email);
}
