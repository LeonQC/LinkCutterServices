package com.repository;

import com.entity.UrlTableEntity;
import jakarta.persistence.OneToMany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@OneToMany("")
public interface EventTypeRepository extends JpaRepository<UrlTableEntity, Long> {

    UrlTableEntity findByOriginalUrl(String sourceLink);

    Optional<UrlTableEntity> findByShortCode(String shortCode);

    Page<UrlTableEntity> findAllByUsernameOrderByCreateTimeDesc(String username, Pageable pageable);
}
