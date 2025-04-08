package org.example.bankup.dto.zip_code;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ResponseZipCodeDto(
    @JsonProperty("post code")
    String postCode,
    String country,
    @JsonProperty("country abbreviation")
    String countryAbbreviation,
    List<Place> places
) {
    public static class Place{
        @JsonProperty("place name")
        public String city;
        public String state;
        @JsonProperty("state abbreviation")
        public String stateAbbreviation;
        public String latitude;
        public String longitude;
    }
}
