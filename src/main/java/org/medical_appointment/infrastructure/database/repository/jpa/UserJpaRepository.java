package org.medical_appointment.infrastructure.database.repository.jpa;


import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<UserEntity, Integer> {

    @Query("""
            SELECT u FROM UserEntity u 
            WHERE u.userName = :username
            """)
    Optional<UserEntity> findByUserName(@Param("username") String username);
}
