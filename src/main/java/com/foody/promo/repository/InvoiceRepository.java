package com.foody.promo.repository;

import com.foody.promo.entity.InvoiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Long> {

    List<InvoiceEntity> findAllByUserId(Long id);

    Optional<InvoiceEntity> findByApCodeAndBlockCode(String apCode, String blockCode);


}
