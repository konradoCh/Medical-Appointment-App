package org.medical_appointment.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.medical_appointment.infrastructure.database.entity.UserRoleEntity;
import org.medical_appointment.infrastructure.database.entity.UserRoleEntityId;
import org.medical_appointment.infrastructure.database.repository.jpa.UserRoleJpaRepository;
import org.medical_appointment.business.dao.UserRoleDAO;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRoleRepository implements UserRoleDAO {

    private final UserRoleJpaRepository userRoleJpaRepository;

    @Override
    public void saveUserRole(Integer userId, String role) {
        Integer roleId = 0;
        if ("patient".equals(role)) {
            roleId = 1;
        } else if ("doctor".equals(role)) {
            roleId = 2;
        }

        UserRoleEntity entity = UserRoleEntity.builder()
                .id(UserRoleEntityId.builder()
                        .userId(userId)
                        .roleId(roleId)
                        .build())
                .build();

        userRoleJpaRepository.saveAndFlush(entity);
    }
}
