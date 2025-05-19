package com.example.DataSample.repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DataSample.model.Data;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {

    boolean existsByDomainAndModeAndScanDepthAndRetrievedDateAndRegistrarAndIanaIdAndCreationDateAndExpirationDateAndNameserversAndEmails(
        String domain, 
        String mode, 
        int scan_depth, 
        LocalDateTime retrievedDate, 
        String registrar, 
        int iana_id, 
        OffsetDateTime creation, 
        OffsetDateTime expiration, 
        String nameservers, 
        String emails
    );

}
