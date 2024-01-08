package com.ra.service;

import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JWTProvider;
import com.ra.security.user_principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements IUserService{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private IRoleService roleService;
    @Override
    public User register(User user) {
        // ma hoa mat khau
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // role
        Set<Role> roles = new HashSet<>();
        // register cua user thi coi no la USER
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName("USER"));
        }else {
            // Tao tai khoan va phan quyen thi phaia co quyen ADMIN
            user.getRoles().forEach(role -> {
                roles.add(roleService.findByRoleName(role.getName()));
            });
        }
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setRoles(roles);
        newUser.setStatus(user.getStatus());
        return userRepository.save(newUser);
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        Authentication authentication;
        authentication = authenticationProvider.
                authenticate(new UsernamePasswordAuthenticationToken(userRequestDTO.getUserName(),userRequestDTO.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return UserResponseDTO.builder()
                .token(jwtProvider.generateToken(userPrinciple)).
                userName(userPrinciple.getUsername()).
                roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }
}
