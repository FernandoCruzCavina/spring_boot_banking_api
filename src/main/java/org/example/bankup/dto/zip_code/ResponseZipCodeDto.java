package org.example.bankup.dto.zip_code;

import lombok.Data;
import org.example.bankup.entity.ZipCode;

import java.util.List;
import java.util.Map;


@Data
public class ResponseZipCodeDto {

    private static class Query{
        public List<String> codes;
        public String country;
    };

    private Query query;
    private Map<String, List<ZipCode>>  results;
}
