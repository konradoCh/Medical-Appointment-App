package org.medical_appointment.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medical_appointment.business.dao.RandomUserDao;
import org.medical_appointment.domain.RandomUser;
import org.medical_appointment.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;



import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RandomUserService {

    private final RandomUserDao randomUserDao;

    public RandomUser findRandomUser() {
        log.info("Looking for random user...");
        RandomUser foundUser = randomUserDao.getRandomUser();

        return Optional.ofNullable(foundUser)
                .orElseThrow(() -> new NotFoundException("Could not find random user"));
    }
}
