package org.example.bankup.service;

import org.example.bankup.dto.zip_code.ResponseZipCodeDto;
import org.example.bankup.entity.ZipCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
    name = "zippopotam",
    url = "api.zippopotam.us/"
)
public interface ZipCodeService {

    @GetMapping(value = "{country}/{postalCode}")
    public ResponseZipCodeDto searchZipCodeByCodes(@PathVariable(name = "country") String country, @PathVariable(name = "postalCode") String postalCode);

}
