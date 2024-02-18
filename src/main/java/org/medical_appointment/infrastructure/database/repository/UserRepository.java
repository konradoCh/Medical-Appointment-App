package org.medical_appointment.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.infrastructure.database.repository.jpa.UserJpaRepository;
import org.medical_appointment.domain.User;
import org.medical_appointment.business.dao.UserDAO;
import org.medical_appointment.infrastructure.database.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByUserName(String username) {
        return userJpaRepository.findByUserName(username)
                .map(userEntityMapper::mapFromEntity);
    }

    @Override
    public void registerNewUser(User user) {
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        userJpaRepository.saveAndFlush(userEntity);
    }
}
