package com.hotelApp.repository;

import com.hotelApp.Userentity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByUsername(String username);
  Optional<UserEntity> findByPassword(String password);
  Optional<UserEntity> findByMobile(String mobile);
  }