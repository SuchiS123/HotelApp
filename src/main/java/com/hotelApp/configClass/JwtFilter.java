package com.hotelApp.configClass;

import com.hotelApp.Userentity.UserEntity;
import com.hotelApp.repository.UserRepository;
import com.hotelApp.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;
    private JwtService jwtService;
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)
        throws ServletException, IOException{

        String token = request.getHeader("Authorization");

        if(token!=null && token.startsWith("Bearer ")){

            String username=jwtService.getUsername(token);
            Optional<UserEntity> opUSer=userRepository.findByUsername(username);

            if(opUSer.isPresent())
            {
                UserEntity user=opUSer.get();

                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(user,null,null);
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


        }
            filterChain.doFilter(request,response);

    }
}
