package fr.cefim.birthdayapp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.cefim.birthdayapp.dtos.RequestLoginDTO;
import fr.cefim.birthdayapp.dtos.ResponseLoginDTO;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.security.MyPrincipalUser;
import fr.cefim.birthdayapp.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class LoginController {

    private final UserServiceImpl mUserService;

    private final AuthenticationManager mAuthenticationManager;

    public LoginController(
            UserServiceImpl userService,
            AuthenticationManager authenticationManager
    ) {
        mUserService = userService;
        mAuthenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> getLogin(@RequestBody RequestLoginDTO requestLoginDTO) {
        Optional<User> user = mUserService.getUserByCredentials(requestLoginDTO.getUsername(), requestLoginDTO.getPassword());

        Authentication authentication = mAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLoginDTO.getUsername(), requestLoginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MyPrincipalUser myPrincipalUser = (MyPrincipalUser) mUserService.loadUserByUsername(user.get().getUsername());

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(myPrincipalUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer("/login")
                .sign(algorithm);

        ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO(access_token, myPrincipalUser);

        return ResponseEntity.ok(responseLoginDTO);
    }

}
