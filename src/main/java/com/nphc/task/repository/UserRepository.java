package com.nphc.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nphc.task.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
