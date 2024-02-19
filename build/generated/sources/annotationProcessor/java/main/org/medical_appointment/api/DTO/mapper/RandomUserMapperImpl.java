package org.medical_appointment.api.DTO.mapper;

import javax.annotation.processing.Generated;
import org.medical_appointment.api.DTO.RandomUserDTO;
import org.medical_appointment.domain.RandomUser;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-18T14:28:31+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class RandomUserMapperImpl implements RandomUserMapper {

    @Override
    public RandomUserDTO map(RandomUser randomUser) {
        if ( randomUser == null ) {
            return null;
        }

        RandomUserDTO.RandomUserDTOBuilder randomUserDTO = RandomUserDTO.builder();

        randomUserDTO.firstName( randomUser.getFirstName() );
        randomUserDTO.lastName( randomUser.getLastName() );
        randomUserDTO.email( randomUser.getEmail() );

        return randomUserDTO.build();
    }
}
