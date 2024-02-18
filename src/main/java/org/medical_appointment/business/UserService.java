package org.medical_appointment.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medical_appointment.domain.User;
import org.medical_appointment.business.dao.UserDAO;
import org.medical_appointment.business.dao.UserRoleDAO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final UserRoleDAO userRoleDAO;
    private final PasswordEncoder passwordEncoder;

    public User findByUserName(String username) {
        Optional<User> user = userDAO.findByUserName(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Could not find user by user name: [%s]".formatted(username));
        }
        return user.get();
    }

    @Transactional
    public void registerNewUser(String userName, String password, String email, String role) {
        User newUser = User.builder()
                .userName(userName)
                .password(passwordEncoder.encode(password))
                .email(email)
                .active(true)
                .build();
        userDAO.registerNewUser(newUser);
        log.info("Registered a new user [{}]", userName);
        User user = userDAO.findByUserName(userName).get();
        userRoleDAO.saveUserRole(user.getId(), role);
    }
}
