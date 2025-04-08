package org.example.bankup.repository;

import org.example.bankup.entity.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipCodeRepository extends JpaRepository<ZipCode, String> {

}
