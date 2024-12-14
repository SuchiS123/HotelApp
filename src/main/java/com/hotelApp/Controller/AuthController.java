package com.hotelApp.Controller;

import com.hotelApp.UserDto.JwtDto;
import com.hotelApp.UserDto.LoginDto;
import com.hotelApp.UserDto.UserDto;
import com.hotelApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService)
    {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto)
    {
        ResponseEntity<?> responseEntity=userService.addUser(userDto);
return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto)
    {
       String jwtToken= userService.verifyUser(loginDto);
       if(jwtToken!=null)
       {
           JwtDto jwtToken1=new JwtDto();
           jwtToken1.setToken(jwtToken);
           jwtToken1.setType("JWT");

           return new ResponseEntity<>(jwtToken1, HttpStatus.OK);
       }
       return  new ResponseEntity<>("Login Failed Invalid User and Password ", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
