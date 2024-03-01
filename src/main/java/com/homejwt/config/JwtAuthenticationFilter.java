package com.homejwt.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger  = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private LoginDetailsService loginDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Authorization
        String requestHeader = request.getHeader("Authorization");
        //Bearer 2352345235sdfrsfgsdfsdf - token format
        logger.info(" Header :  {}", requestHeader);
        String username = null;
        String token = null;
        if (requestHeader == null || !requestHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = requestHeader.substring(7);
        username = jwtHelper.getUsernameFromToken(token);
        //we have token and username now
        //do authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //fetch user detail from username
            UserDetails userDetails = loginDetailsService.loadUserByUsername(username);
            if(jwtHelper.validateToken(token, userDetails)){
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
