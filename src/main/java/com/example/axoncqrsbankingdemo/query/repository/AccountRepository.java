package com.example.axoncqrsbankingdemo.query.repository;

import com.example.axoncqrsbankingdemo.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}