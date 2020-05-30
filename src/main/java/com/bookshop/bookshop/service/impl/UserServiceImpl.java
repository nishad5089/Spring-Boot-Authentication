package com.bookshop.bookshop.service.impl;

import com.bookshop.bookshop.exceptions.RecordExistsException;
import com.bookshop.bookshop.exceptions.UserNotFoundException;
import com.bookshop.bookshop.io.entity.UserEntity;
import com.bookshop.bookshop.io.repository.UserRepository;
import com.bookshop.bookshop.service.UserService;
import com.bookshop.bookshop.shared.Utils;
import com.bookshop.bookshop.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RecordExistsException("Record already exists");
        }
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UserNotFoundException(email);
        }
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null)
            throw new UserNotFoundException("User with ID: " + userId + " not found");
        ModelMapper modelMapper = new ModelMapper();
        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UserNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
