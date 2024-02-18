package org.medical_appointment.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.medical_appointment.infrastructure.database.entity.RoleEntity;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.infrastructure.database.mapper.UserEntityMapper;
import org.medical_appointment.infrastructure.database.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MedicalAppointmentUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Set<RoleEntity> roles = userRepository.findByUserName(userName).get().getRoles();
        org.medical_appointment.domain.User user = userRepository.findByUserName(userName).get();
        UserEntity userEntity = userEntityMapper.mapToEntity(user);

        List<GrantedAuthority> authorities = getUserAuthority(roles);
        return buildUserForAuthentication(userEntity, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .distinct()
                .collect(Collectors.toList());
    }

    private UserDetails buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
        return new User(
                user.getUserName(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities
        );
    }
}