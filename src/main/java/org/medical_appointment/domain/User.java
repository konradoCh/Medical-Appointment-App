package org.medical_appointment.domain;

import lombok.*;
import org.medical_appointment.infrastructure.database.entity.RoleEntity;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"userName", "id"})
@ToString(of = {"userName", "email", "active"})
public class User {

    Integer id;
    String userName;
    String email;
    String password;
    Set<RoleEntity> roles;
    Boolean active;
}
