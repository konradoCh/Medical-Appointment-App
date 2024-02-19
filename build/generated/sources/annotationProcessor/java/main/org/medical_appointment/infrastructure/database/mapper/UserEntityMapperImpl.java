package org.medical_appointment.infrastructure.database.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.medical_appointment.domain.User;
import org.medical_appointment.infrastructure.database.entity.RoleEntity;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-19T19:37:42+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public User mapFromEntity(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( entity.getId() );
        user.userName( entity.getUserName() );
        user.email( entity.getEmail() );
        user.password( entity.getPassword() );
        Set<RoleEntity> set = entity.getRoles();
        if ( set != null ) {
            user.roles( new LinkedHashSet<RoleEntity>( set ) );
        }
        user.active( entity.getActive() );

        return user.build();
    }

    @Override
    public UserEntity mapToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        if ( user.getId() != null ) {
            userEntity.id( user.getId() );
        }
        userEntity.userName( user.getUserName() );
        userEntity.email( user.getEmail() );
        userEntity.password( user.getPassword() );
        userEntity.active( user.getActive() );
        Set<RoleEntity> set = user.getRoles();
        if ( set != null ) {
            userEntity.roles( new LinkedHashSet<RoleEntity>( set ) );
        }

        return userEntity.build();
    }
}
