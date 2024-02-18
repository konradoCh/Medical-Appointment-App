package org.medical_appointment.infrastructure.randomData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.medical_appointment.business.dao.RandomUserDao;
import org.medical_appointment.domain.RandomUser;
import org.medical_appointment.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@AllArgsConstructor
public class RandomUserClientImpl implements RandomUserDao {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Override
    public RandomUser getRandomUser() {
        String response = webClient
                .get()
                .uri("")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            RandomUser userApi = objectMapper.readValue(response, RandomUser.class);
            return userApi;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new NotFoundException("Could not find any user.");
        }
    }
}
