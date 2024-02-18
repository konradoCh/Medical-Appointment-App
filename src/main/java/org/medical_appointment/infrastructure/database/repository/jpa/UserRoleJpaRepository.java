package org.medical_appointment.infrastructure.database.repository.jpa;


import org.medical_appointment.infrastructure.database.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<UserRoleEntity, Integer> {

}
