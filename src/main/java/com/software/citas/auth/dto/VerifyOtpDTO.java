//package com.software.citas.auth.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class VerifyOtpDTO {
//
//    @NotBlank
//    @Email
//    private String email;
//
//    @NotBlank
//    @Pattern(regexp = "\\d{6}", message = "El OTP debe contener 6 números")
//    private String otp;
//}