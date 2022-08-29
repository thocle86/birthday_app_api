package fr.cefim.birthdayapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.cefim.birthdayapp.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

//    private JwtUtils mJwtUtils;

    private UserDetailsService mUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    public AuthTokenFilter(
//            JwtUtils jwtUtils,
            UserDetailsService userDetailsService
    ) {
//        mJwtUtils = jwtUtils;
        mUserDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = parseJwt(request);
            if (token != null) {
//                String username = mJwtUtils.getUserNameFromJwtToken(token);

                /////FIXME: JWT peter
                System.out.println("token : " + token);

                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();

                System.out.println("token : " + username);

                // FIXME: idem peter
                UserDetails userDetails = mUserDetailsService.loadUserByUsername(username);
                // FIXME: idem peter
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // FIXME: idem peter
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // FIXME: idem peter
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        // FIXME: idem peter
        filterChain.doFilter(request, response);
    }
    private String parseJwt(HttpServletRequest request) {
        // FIXME: idem peter
        String headerAuth = request.getHeader("Authorization");
        System.out.println("headerAuth: " + headerAuth);
        if (
                StringUtils.hasText(headerAuth) &&
                        headerAuth.startsWith("Bearer ")// FIXME: idem peter
        ) {
            System.out.println(headerAuth.substring(7, headerAuth.length()));
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}