package com.example.DataSample.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.DataSample.model.Data;
import com.example.DataSample.service.*;

@RestController
@RequestMapping("/data")
public class DataController {
    
    private final DataService dataServive;
    private final FileService fileService;
    private final UserServiceImpl userServiceImpl;


    @Autowired
    public DataController(DataService dataServive, FileService fileService, UserServiceImpl userServiceImpl) {
        this.dataServive = dataServive;
        this.fileService = fileService;
        this.userServiceImpl = userServiceImpl;
    }
    
    @GetMapping({"/all", "/"})
    public ResponseEntity<List<Data>> getAllData() {
        List<Data> data = dataServive.findAllData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Data> searchIdData(@PathVariable("id") Long id) {
        Data data = dataServive.findDataById(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Data> saveData(@RequestBody Data data) {
        boolean isNew = (data.getId() == null); 
        Data savedData = isNew ? dataServive.addData(data) : dataServive.updateData(data);
        return new ResponseEntity<>(savedData, isNew ? HttpStatus.CREATED : HttpStatus.OK);
    }    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable("id") Long id) {
        dataServive.deleteData(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> importCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<Data> list = fileService.importer(file);
            return new ResponseEntity<>(list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createData(@RequestBody Data data, @RequestParam String username) {
        userServiceImpl.createDataForUser(username, data);
        return new ResponseEntity<>("data created !", HttpStatus.CREATED);
    }

    //Exception handlers
    /*
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException notfound) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), notfound.getMessage());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ErrorResponse handleAlreadyExistsException(AlreadyExistsException exists) {
        return new ErrorResponse(HttpStatus.ALREADY_REPORTED.value(), exists.getMessage());
    }
     * 
     */
}
