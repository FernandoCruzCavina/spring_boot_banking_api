package org.example.bankup.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "zipcode")
public class ZipCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String zipcode;

    @Column(nullable = false, name = "postal_code", length = 15)
    private String postCode;
    @Column(nullable = false, length = 20)
    private String country;
    @Column(nullable = false, name = "country_abbreviation", length = 3)
    private String countryAbbreviation;
    @Column(nullable = false, length = 20)
    private String state;
    @Column(nullable = false, name = "state_abbreviation", length = 3)
    private String stateAbbreviation;
    @Column(nullable = false, length = 50)
    private String city;
    @Column(nullable = false, length = 50)
    private String latitude;
    @Column(nullable = false, length = 50)
    private String longitude;

    public ZipCode(String postCode, String country, String countryAbbreviation, String state, String stateAbbreviation, String city, String latitude, String longitude) {
        this.postCode = postCode;
        this.country = country;
        this.countryAbbreviation = countryAbbreviation;
        this.state = state;
        this.stateAbbreviation = stateAbbreviation;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipcode = postCode + "-" + countryAbbreviation + "-" + stateAbbreviation;
    }
}
