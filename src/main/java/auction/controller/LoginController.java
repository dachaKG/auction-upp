package auction.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auction.dto.LoginDTO;
import auction.user.CustomUserDetails;
import auction.user.User;
import auction.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	UserService userService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	IdentityService identityService;

	@SuppressWarnings("unchecked")
	@PostMapping
	public ResponseEntity<Map<String, Object>> authenticate(@RequestBody LoginDTO loginDTO) {

		String token = null;
		User user = userService.findOneByUsername(loginDTO.getUsername());

		Map<String, Object> tokenMap = new HashMap<String, Object>();
		// String password = encoder.encode(loginDTO.getPassword());
		if (user != null && encoder.matches(loginDTO.getPassword(), user.getPassword()) && user.isConfirmed()) {
			CustomUserDetails customUser = new CustomUserDetails(user);
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) customUser.getAuthorities();
			List<String> roles = new ArrayList<String>();
			for (GrantedAuthority authority : authorities) {
				roles.add(authority.getAuthority());
			}
			token = Jwts.builder().setSubject(user.getUsername()).claim("roles", roles).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey").compact();
			System.out.println(token);
			tokenMap.put("token", token);
			tokenMap.put("user", user.getUsername());
			tokenMap.put("role", user.getRole());
			org.activiti.engine.identity.User actUser = identityService.createUserQuery().userId(user.getUsername()).singleResult();
			
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
		} else {
			tokenMap.put("token", null);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
		}
	}

}
