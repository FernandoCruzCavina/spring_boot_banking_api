package org.example.bankup.mapper;

import org.example.bankup.dto.zip_code.ResponseZipCodeDto;
import org.example.bankup.entity.ZipCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ZipCodeMapper {

//    @Mapping(target = "city", source = "places", qualifiedByName = "getFirstPlace")
//    @Mapping(target = "state", source = "places", qualifiedByName = "getFirstPlace")
//    @Mapping(target = "stateAbbreviation", source = "places", qualifiedByName = "getFirstPlace")
//    @Mapping(target = "latitude", source = "places", qualifiedByName = "getFirstPlace")
//    @Mapping(target = "longitude", source = "places", qualifiedByName = "getFirstPlace")
//    ZipCode ResponseZipCodeDtoToZipCode(ResponseZipCodeDto responseZipCodeDto);

    default ResponseZipCodeDto.Place getFirstPlace(List<ResponseZipCodeDto.Place> places) {
        return (places != null && !places.isEmpty()) ? places.get(0) : null;
    }

}
