package org.example.bankup.service;

import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.AccountType;
import org.example.bankup.dto.account.CreateAccountDto;
import org.example.bankup.dto.account.ViewAccountDto;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Customer;
import org.example.bankup.exception.EntityNotFoundException;
import org.example.bankup.mapper.AccountMapper;
import org.example.bankup.repository.AccountRepository;
import org.example.bankup.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @CachePut(value = "accounts")
    public void createAccount(CreateAccountDto accountDto) {

        Customer customer = customerRepository.findFirstByCustomerId(accountDto.customerId())
                .orElseThrow(EntityNotFoundException::customerNotFound);

        Account account = new Account(
                accountDto.balance(),
                AccountType.STANDARD_USER,
                AccountStatus.ACTIVE,
                Timestamp.from(Instant.now()),
                customer
        );

        accountRepository.save(account);
    }

    @Cacheable(value = "accounts")
    public ViewAccountDto getAccountByCustomerId(long customerId) {
        Account account = accountRepository.findFirstByCustomer_CustomerId(customerId)
                .orElseThrow(EntityNotFoundException::accountNotFound);

        return AccountMapper.INSTANCE.accountToViewAccountDto(account);
    }

    @CacheEvict(value = "acccounts", allEntries = true)
    public String deleteAccountByCustomerId(long customer_id) {
        Customer customer = customerRepository.findFirstByCustomerId(customer_id)
                .orElseThrow(EntityNotFoundException::customerNotFound);

        Account account = accountRepository.findFirstByCustomer_CustomerId(customer.getCustomerId())
                .orElseThrow(EntityNotFoundException::accountNotFound);

        accountRepository.delete(account);

        return "Account deleted";
    }
}
