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

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.accountMapper = accountMapper;
    }

    @CachePut(value = "accounts", key = "#accountDto.customerId()")
    public ViewAccountDto createAccount(CreateAccountDto accountDto) {

        Customer customer = customerRepository.findFirstById(accountDto.customerId())
                .orElseThrow(EntityNotFoundException::customerNotFound);

        Account account = new Account(
                accountDto.balance(),
                AccountType.STANDARD_USER,
                AccountStatus.ACTIVE,
                Timestamp.from(Instant.now()),
                customer
        );

        accountRepository.save(account);

        return accountMapper.accountToViewAccountDto(account);
    }

    @Cacheable(value = "accounts", key = "#customerId")
    public ViewAccountDto getAccountByCustomerId(long customerId) {
        Account account = accountRepository.findFirstByCustomer_CustomerId(customerId)
                .orElseThrow(EntityNotFoundException::accountNotFound);

        return accountMapper.accountToViewAccountDto(account);
    }

    @CacheEvict(value = "accounts", allEntries = true)
    public ViewAccountDto deleteAccountByCustomerId(long customer_id) {
        Customer customer = customerRepository.findFirstById(customer_id)
                .orElseThrow(EntityNotFoundException::customerNotFound);

        Account account = accountRepository.findFirstByCustomer_CustomerId(customer.getId())
                .orElseThrow(EntityNotFoundException::accountNotFound);

        accountRepository.delete(account);

        return accountMapper.accountToViewAccountDto(account);
    }
}
