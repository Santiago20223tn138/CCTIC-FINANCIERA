package financiera.CCTIC.security.controller;

import financiera.CCTIC.security.jwt.JwtProvider;
import financiera.CCTIC.user.service.UserService;
import financiera.CCTIC.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/financiera/auth")
@CrossOrigin(origins = {"*"})
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtprovider;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Message> login(@Valid @RequestBody LoginUserDTO loginUserDTO, BindingResult result) {
        if (result.hasErrors())
            return new ResponseEntity<>(new Message("Usuario y/o contraseña  incorrectos", true, null),
                    HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtprovider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", userDetails);
        data.put("userInfo", userService.getByUsername(userDetails.getUsername()));
        return new ResponseEntity<>(new Message("ok", false, data), HttpStatus.OK);
    }

}