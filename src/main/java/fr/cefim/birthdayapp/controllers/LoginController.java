package fr.cefim.birthdayapp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.cefim.birthdayapp.dtos.LoginDTO;
import fr.cefim.birthdayapp.models.JwtResponse;
import fr.cefim.birthdayapp.security.UserDetailsImpl;
import fr.cefim.birthdayapp.services.UserService;
import fr.cefim.birthdayapp.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    private final UserService mUserService;

    private final UserDetailsService mUserDetailsService;

    private final AuthenticationManager mAuthenticationManager;

    private final JwtUtils mJwtUtils;

    public LoginController(
            UserService userService,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils
    ) {
        mUserService = userService;
        mUserDetailsService = userDetailsService;
        mAuthenticationManager = authenticationManager;
        mJwtUtils = jwtUtils;
    }

//    @PostMapping("/signin")
//    public UserDetails getLogin(@RequestBody LoginDTO loginDTO) {
//        Optional<User> user = mUserService.getUserByCredentials(loginDTO.getUsername(), loginDTO.getPassword());
//        return mUserDetailsService.loadUserByUsername(user.get().getUsername());
//    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = mAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer("signin")
                .sign(algorithm);


//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(access_token, userDetails.getUser()));
    }

}
