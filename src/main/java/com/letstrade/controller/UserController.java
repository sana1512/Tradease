package com.letstrade.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.letstrade.domain.VerificationType;
import com.letstrade.model.ForgotPasswordToken;
import com.letstrade.model.User;
import com.letstrade.model.VerificationCode;
import com.letstrade.response.ApiResponse;
import com.letstrade.response.AuthResponse;
import com.letstrade.service.EmailService;
import com.letstrade.service.ForgotPasswordService;
import com.letstrade.service.UserService;
import com.letstrade.service.VerificationCodeService;
import com.letstrade.utils.OtpUtils;
import com.letstrade.request.ForgotPasswordTokenRequest;
import com.letstrade.request.ResetPasswordRequest;

import jakarta.mail.MessagingException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    private String jwt;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * @param jwt
     * @param verificationType
     * @return
     * @throws MessagingException
     */
    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt, 
                                                    @PathVariable VerificationType verificationType) throws MessagingException{
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if(verificationCode==null){
            verificationCode=verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<>("verification otp sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
        @PathVariable String otp,
        @RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);

            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        }
        throw new RuntimeException("Invalid OTP was entered");
    }

    @PostMapping("/auth/users/reset-password/{verificationType}/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest req) throws MessagingException{
        
        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

        if(token==null){
            token=forgotPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
        }

        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset otp sent!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
        @RequestParam String id,
        @RequestBody ResetPasswordRequest req,
        @RequestHeader("Authorization") String jwt){

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("password update successfull!");
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }
        throw new RuntimeException("Wrong OTP");
    }
}
