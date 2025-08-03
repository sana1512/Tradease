package com.letstrade.service;

import com.letstrade.domain.VerificationType;
import com.letstrade.model.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt);

    public User findUserByEmail(String email);

    public User findUserById(Long id);

    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);

    User updatePassword(User user, String newPassword);
}
