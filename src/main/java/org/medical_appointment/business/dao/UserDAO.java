package org.medical_appointment.business.dao;

import org.medical_appointment.domain.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUserName(String username);

    void registerNewUser(User user);
}
