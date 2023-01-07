package com.cosmos.service;

import com.cosmos.model.Login;
import com.cosmos.response.JwtResponse;

public interface AuthService {

    JwtResponse authenticateUser(Login login);

    //SuccessResponse registerUser(SignupRequest signUpRequest);

}
