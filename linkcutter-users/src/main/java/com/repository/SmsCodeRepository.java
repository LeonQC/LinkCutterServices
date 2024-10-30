package com.repository;

import com.entity.SmsCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCodeEntity, Long> {

    SmsCodeEntity findFirstByPhoneNumberOrderByCreatedTimeDesc(String phoneNumber);
}
