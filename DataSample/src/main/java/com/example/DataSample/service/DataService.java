package com.example.DataSample.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.DataSample.exception.*;
import com.example.DataSample.model.*;
import com.example.DataSample.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DataService {

    private final DataRepository dataRepo;
    private final UserServiceImpl userServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    public DataService(DataRepository dataRepo, UserServiceImpl userServiceImpl) {
        this.dataRepo = dataRepo;
        this.userServiceImpl = userServiceImpl;
    }

    //*****UTILISE TRY-CATCH*****
    
    public Data addData(Data data) {
        if (dataRepo.existsByDomainAndModeAndScanDepthAndRetrievedDateAndRegistrarAndIanaIdAndCreationDateAndExpirationDateAndNameserversAndEmails(
            data.getDomain(), 
            data.getMode(), 
            data.getScanDepth(), 
            data.getRetrievedDate(), 
            data.getRegistrar(), 
            data.getIanaId(), 
            data.getCreationDate(), 
            data.getExpirationDate(), 
            data.getNameservers(), 
            data.getEmails())) {
                logger.info("exists");

            throw new AlreadyExistsException("data already exists!");
        }
        logger.info("data ssaved");
        return dataRepo.save(data);
    }


    public Data updateData(Data data) {
        if (!dataRepo.existsById(data.getId())) {
            logger.info("notfuond");
            throw new NotFoundException("data " + data.getId() + " not found!");
        }
        logger.info("updated");
        return dataRepo.save(data);
    }

    public List<Data> findAllData() {
        return dataRepo.findAll();
    }
    
    public Data findDataById(Long id) {
        return dataRepo.findById(id).orElseThrow(() -> new NotFoundException("id" + id + "not found"));
    }

    public void deleteData(Long id) {
        dataRepo.deleteById(id);
    }

    public void createDataForUser(String username, Data data) {
        User user = userServiceImpl.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user : " + username + " not found");
        }
        data.setUser(user);
        dataRepo.save(data);
    }
}
