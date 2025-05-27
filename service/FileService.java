package com.example.DataSample.service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.DataSample.model.Data;
import com.example.DataSample.repository.DataRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private DataRepository dataRepo;

    
    public List<Data> importer(MultipartFile file) throws IOException {

        List<Data> list = new ArrayList<>();

        int invalid = 0;
        int row = 1;

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] col;
            csvReader.readNext();


            while ((col = csvReader.readNext()) != null) {
                row++;
                try {
                    //logger.debug("row {}", row);
                    if (col.length < 10) continue;

                    Data data = new Data();


                    data.setDomain(col[0].trim());
                    data.setMode(col[1].trim());
                    data.setScanDepth(Integer.parseInt(col[2].trim()));
                    data.setRegistrar(col[4].trim());
                    data.setIanaId(Integer.parseInt(col[5].trim()));
                    data.setNameservers(col[8].trim());
                    data.setEmails(col[9].trim());

                    String retrieved = col[3].trim().replace("\"", "").replace(" ", "T");
                    data.setRetrievedDate(LocalDateTime.parse(retrieved));
                    String created = col[6].trim().replace("\"", "").replace(" ", "T");
                    data.setCreationDate(OffsetDateTime.parse(created));
                    String expired = col[7].trim().replace("\"", "").replace(" ", "T");
                    data.setExpirationDate(OffsetDateTime.parse(expired));


                    if (dataRepo.existsByDomainAndModeAndScanDepthAndRetrievedDateAndRegistrarAndIanaIdAndCreationDateAndExpirationDateAndNameserversAndEmails(
                        data.getDomain(), data.getMode(), 
                        data.getScanDepth(), data.getRetrievedDate(), 
                        data.getRegistrar(), data.getIanaId(), 
                        data.getCreationDate(), data.getExpirationDate(), 
                        data.getNameservers(), data.getEmails()) 
                        || list.contains(data)) {
                            logger.info("exists");
                        continue;
                    }


                    list.add(data);
                    logger.info("added list");
                } catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
                    invalid++;
                    logger.warn("Invalid row at line {}: {}", row);
                    logger.warn("Reason: {}", e.getMessage());

                    //e.printStackTrace();
                }
            }
        } catch (CsvValidationException e) {
            logger.error("CSV validation error: {}", e.getMessage());
            throw new IOException("parsing error", e);
        }

        //***** AJOUTE GLOBALEXCEPTION ******/

        logger.info("Total valid rows: {}", list.size());
        logger.info("Total invalid rows skipped: {}", invalid);        
         
        List<Data> savedData = dataRepo.saveAll(list);

        
        logger.debug("Saved {} rows to the database", savedData.size());
        return savedData;

         
        //return dataRepo.saveAll(list);
    }
}