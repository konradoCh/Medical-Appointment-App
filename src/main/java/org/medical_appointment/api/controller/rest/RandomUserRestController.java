package org.medical_appointment.api.controller.rest;

import lombok.AllArgsConstructor;
import org.medical_appointment.api.DTO.RandomUserDTO;
import org.medical_appointment.api.DTO.mapper.RandomUserMapper;
import org.medical_appointment.business.RandomUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(RandomUserRestController.API_RANDOM)
public class RandomUserRestController {

    public static final String API_RANDOM = "/api/randomUser";

    private final RandomUserService randomUserService;
    private final RandomUserMapper randomUserMapper;

    @GetMapping()
    public RandomUserDTO getRandomUser() {
        return randomUserMapper.map(randomUserService.findRandomUser());
    }
}
