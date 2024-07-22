package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.UserException;
import com.vanlang.lyxrica_springb.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

}
