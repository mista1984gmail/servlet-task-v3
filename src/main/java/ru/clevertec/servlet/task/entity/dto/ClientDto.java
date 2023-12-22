package ru.clevertec.servlet.task.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private LocalDate birthday;
    private LocalDateTime registrationDate;

}
