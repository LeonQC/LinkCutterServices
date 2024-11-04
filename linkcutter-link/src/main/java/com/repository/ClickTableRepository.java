package com.repository;

import com.entity.ClickTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClickTableRepository extends JpaRepository<ClickTableEntity, Long> {

    Optional<ClickTableEntity> findByEventId(Long eventId);
}
