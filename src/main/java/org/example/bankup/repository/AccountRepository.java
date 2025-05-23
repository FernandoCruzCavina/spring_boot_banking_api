package org.example.bankup.repository;

import org.example.bankup.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findFirstByAccountId(Long accountId);
    Optional<Account> findFirstByCustomer_Id(Long customerId);
    void deleteByCustomer_Id(Long customerId);
}
