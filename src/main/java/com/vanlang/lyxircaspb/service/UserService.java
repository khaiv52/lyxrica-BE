package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.UserException;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.request.UpdatePasswordRequest;

import java.util.List;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public List<User> findAllUsers() throws UserException;

    public User findUserByEmail(String email);

    public void assignDefaultRole(User user);

    public void updateUser(Long userId, User updatedUser) throws UserException;

    public User resetUserPassword(UpdatePasswordRequest req) throws UserException;


}
