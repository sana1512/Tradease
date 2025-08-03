package com.letstrade.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.letstrade.config.JwtProvider;
import com.letstrade.domain.VerificationType;
import com.letstrade.model.TwoFactorAuth;
import com.letstrade.model.User;
import com.letstrade.repository.UserRepository;

@RestController
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new RuntimeException("User Not Found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new RuntimeException("User Not Found");
        }
        return user;

    }

    @Override
    public User findUserById(Long userId) {
        
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("user not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo,User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);

        user.setTwoFactorAuth(twoFactorAuth);

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }


}
