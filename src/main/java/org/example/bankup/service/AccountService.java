package org.example.bankup.service;

import org.example.bankup.constants.AccountStatus;
import org.example.bankup.constants.AccountType;
import org.example.bankup.dto.account.CreateAccountDto;
import org.example.bankup.dto.account.ViewAccountDto;
import org.example.bankup.entity.Account;
import org.example.bankup.entity.Customer;
import org.example.bankup.mapper.AccountMapper;
import org.example.bankup.repository.AccountRepository;
import org.example.bankup.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createAccount(CreateAccountDto accountDto) {

        Optional<Customer> customer = customerRepository.findFirstByCustomerId(accountDto.customerId());


        Account account = new Account(
                accountDto.balance(),
                AccountType.STANDARD_USER,
                AccountStatus.ACTIVE,
                Timestamp.from(Instant.now()),
                customer.get()
        );

        accountRepository.save(account);
    }

    public ViewAccountDto getAccountByCustomerId(long customerId) {
        Optional<Account> account = accountRepository.findFirstByCustomer_CustomerId(customerId);

        return account.map(AccountMapper.INSTANCE::accountToViewAccountDto).orElse(null);
    }

    public boolean deleteAccountByCustomerId(long customer_id) {
        Optional<Customer> customer = customerRepository.findFirstByCustomerId(customer_id);
        if (customer.isPresent()) {
            Account account = accountRepository.findFirstByCustomer_CustomerId(customer_id).get();
            return true;
        } else {
            return false;
        }
    }
}
