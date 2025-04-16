package org.example.bankup.exception;

import org.example.bankup.entity.Customer;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public static EntityAlreadyExistException customerFounded(Customer customer){
        return new EntityAlreadyExistException("Customer with "+ customer.getEmail() +" already exists");
    }

}
