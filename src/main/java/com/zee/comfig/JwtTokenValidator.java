package com.zee.comfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// before goint to othere filter first jwt token validator will be called 
// then basic authentication filter will be called 


// OncePerRequestFilter makes sure that the filter is executed only once per request
// we are extending OncePerRequestFilter because we want to validate the jwt token for every request

@Component
public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 // get the jwt token from the request header
		String jwt = request.getHeader("Authorization");
		
		if(jwt != null) {
			// Bearer jwt we need to remove the Bearer part there for start with 7
			jwt = jwt.substring(7);
			
			try {
				// we need to validate the jwt token
				// we will use the secret key to validate the jwt token
				// hmacShaKeyFor method will generate the secret key from the secret key string
				SecretKey key = Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes());
				// claims these claims will be provided by jwt token
				Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();
				// NEW, RECOMMENDED VERSION
//				Claims claims = Jwts.parser()                 // 1
//				                        .verifyWith(key)      // 2
//				                        .build()              // 3
//				                        .parseSignedClaims(jwt) // 4
//				                        .getPayload();        // 5
				
				// to get the email from the claims
				String email = String.valueOf(claims.get("email"));
				// authorities means roles of the user like ROLE_ADMIN, ROLE_CUSTOMER
				// to get the authorities from the claims
				String authorities = String.valueOf(claims.get("authorities"));
				
				// converting the comma separated string to list of granted authorities
				
				List<GrantedAuthority> auths = AuthorityUtils
						.commaSeparatedStringToAuthorityList(authorities);


				// After successfully validating the JWT, we create a standard Spring Security Authentication object.
				// This object represents the user's identity for the rest of the application.
				//
				// - The first argument ('email') is the "principal," which is the user's identifier.
				// - The second argument ('null') is the "credentials" (usually the password). We set it to null
				//   because the user has already been authenticated by the JWT, so we don't need the password anymore.
				// - The third argument ('auths') is a collection of the user's authorities (e.g., roles like 'ROLE_USER').
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				
				// The SecurityContextHolder is a thread-local storage that holds the security information for the current request.
				// By setting the Authentication object in the context, we are officially marking the current user as "authenticated."
				// 
				// From this point forward, for the duration of this request, Spring Security knows who the user is
				// and can perform authorization checks (e.g., checking if the user has a specific role).
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			}
			catch(Exception e) {
				throw new BadCredentialsException("Invallid JWT tlken.. ");
			}
			
		}
		
		filterChain.doFilter(request, response);
		// now we have to create jwt provider
		
	}

}
