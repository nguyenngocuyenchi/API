package com.example.DataSample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.DataSample.model.Data;
import com.example.DataSample.model.User;
import com.example.DataSample.repository.DataRepository;
import com.example.DataSample.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final DataRepository dataRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, DataRepository dataRepo) {
        this.userRepo = userRepo;
        this.dataRepo = dataRepo;
    }

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("not found with email: " + username));
        return new UserDetailsImpl(user);
    }


    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public List<User> findAllUser() {
        return userRepo.findAll();
    }

    public void createDataForUser(String email, Data data) {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        data.setUser(user);
        dataRepo.save(data);    
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByEmail(username);
    }

    

}
 