package org.example.bankup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bankup.constants.CustomerRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "bank", name = "customer")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "email")
    private String email;

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
    private CustomerRole customer_role;

    public Customer(long id){
        this.id = id;
    }

    public Customer(String name, String email, String password, String country, String state, String city, CustomerRole customer_role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.country = country;
        this.state = state;
        this.city = city;
        this.customer_role = customer_role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.customer_role ==CustomerRole.ADMINISTRATOR) return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"), new SimpleGrantedAuthority("ROLE_STANDARD_CUSTOMER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_STANDARD_CUSTOMER"));
    }

    @Override
    public String getUsername() {
        return "";
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
