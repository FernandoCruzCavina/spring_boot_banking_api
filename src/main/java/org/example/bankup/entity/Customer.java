package org.example.bankup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.bankup.constants.CustomerRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(schema = "bank", name = "customer")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "email")
    private String mail;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(nullable = false, name = "country")
    private String country;

    @Column(nullable = false, name = "state")
    private String state;

    @Column(nullable = false, name = "city")
    private String city;

    @Column(nullable = false, name = "customer_role")
    @Enumerated(EnumType.STRING)
    private CustomerRole customerRole;

    public Customer(long customerId){
        this.customerId = customerId;
    }

    public Customer(String name, String email, String password, String country, String state, String city, CustomerRole customerRole) {
        this.name = name;
        this.mail = email;
        this.password = password;
        this.country = country;
        this.state = state;
        this.city = city;
        this.customerRole = customerRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.customerRole==CustomerRole.ADMINISTRATOR) return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"), new SimpleGrantedAuthority("ROLE_STANDARD_CUSTOMER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_STANDARD_CUSTOMER"));
    }

    @Override
    public String getUsername() {
        return "";
    }
}
