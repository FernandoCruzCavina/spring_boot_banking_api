package org.example.bankup.repository;

import org.example.bankup.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findFirstByAccountId(Long accountId);
    Optional<Account> findFirstByCustomer_CustomerId(Long customerId);

}
