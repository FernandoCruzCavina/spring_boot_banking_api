package org.example.bankup.service;

import org.example.bankup.dto.zip_code.ResponseZipCodeDto;
import org.example.bankup.entity.ZipCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "zipcode",
url = "https://app.zipcodebase.com/api/v1/"
)
public interface ZipCodeService {

    final String api = "75c95820-0685-11f0-a5f6-3d7f712ef344";

    @GetMapping(value = "search?apikey=" + api + "&codes={codes}")
    public ResponseZipCodeDto searchZipCodeByCodes(@PathVariable String codes);

}
