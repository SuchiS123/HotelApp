package com.hotelApp.service;


import com.hotelApp.UserDto.LoginDto;
import com.hotelApp.UserDto.UserDto;
import com.hotelApp.Userentity.UserEntity;
import com.hotelApp.repository.UserRepository;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.kerberos.EncryptionKey;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
private UserRepository userRepository;
private ModelMapper modelMapper;
private JwtService jwtService;

public UserService(UserRepository userRepository,ModelMapper modelMapper,JwtService jwtService)
{
    this.userRepository=userRepository;
    this.modelMapper=modelMapper;
    this.jwtService=jwtService;
}

public ResponseEntity<?> addUser(UserDto userDto) {
    UserEntity userEntity = MapToEntity(userDto);
    Optional<UserEntity> opUsername=userRepository.findByUsername(userEntity.getUsername());
    if(opUsername.isPresent())
    {
        return new ResponseEntity<>("Username is Already Exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    Optional<UserEntity> opEmail=userRepository.findByEmail(userEntity.getEmail());
    if(opEmail.isPresent())
    {
        return new ResponseEntity<>("Email is Already Exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    Optional<UserEntity> opPassword=userRepository.findByPassword(userEntity.getPassword());
    if(opPassword.isPresent())
    {
        return new ResponseEntity<>("Password is Already Exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    Optional<UserEntity> opMobile=userRepository.findByPassword(userEntity.getMobile());
    if(opMobile.isPresent())
    {
        return new ResponseEntity<>("Mobile is Already Exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(),BCrypt.gensalt(10)));
    UserEntity saveUser = userRepository.save(userEntity);
    UserDto userDto1 = MapToDto(saveUser);
    return new ResponseEntity<>(userDto1.toString(), HttpStatus.OK);

}


public UserEntity MapToEntity(UserDto userDto) {

    UserEntity user = modelMapper.map(userDto, UserEntity.class);
    return user;
}

public UserDto MapToDto(UserEntity userEntity)
{
    UserDto userDto = modelMapper.map(userEntity, UserDto.class);
    return userDto;
}

    public String verifyUser(LoginDto loginDto) {

        Optional<UserEntity> opUsername = userRepository.findByUsername(loginDto.getUsername());
        if(opUsername.isPresent())
        {
            UserEntity user=opUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(), opUsername.get().getPassword()))
            {
                String jwtToken =jwtService.generateToken(user.getUsername());
                return jwtToken;
            }
           else{ return null;}
        }
        return null;

    }
}
