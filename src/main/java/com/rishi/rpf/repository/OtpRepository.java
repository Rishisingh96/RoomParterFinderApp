package com.rishi.rpf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rishi.rpf.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    
    Optional<Otp> findByEmailAndUsedFalseOrderByExpiryTimeDesc(String email);
    
    Optional<Otp> findByEmailAndCodeAndUsedFalse(String email, String code);
    
    List<Otp> findByEmailAndUsedFalse(String email);
    
    @Modifying
    @Transactional
    @Query("UPDATE Otp o SET o.used = true WHERE o.email = :email AND o.used = false")
    void invalidateAllUnusedOtpsByEmail(@Param("email") String email);
    
}

