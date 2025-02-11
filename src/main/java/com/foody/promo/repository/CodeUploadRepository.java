package com.foody.promo.repository;

import com.foody.promo.entity.CodeUploadEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeUploadRepository extends CrudRepository<CodeUploadEntity, String> {

    Optional<CodeUploadEntity> findById(Long id);

    Optional<CodeUploadEntity> findByIdAndCodeStartDateLessThanEqualAndCodeEndDateGreaterThanEqual(Long id, Long startDate, Long endDate);

}
