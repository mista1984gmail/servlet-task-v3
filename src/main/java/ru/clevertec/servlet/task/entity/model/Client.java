package ru.clevertec.servlet.task.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Client implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private LocalDate birthday;
    private LocalDateTime registrationDate;

}
