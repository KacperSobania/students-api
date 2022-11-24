package pl.kacper.studentsapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentDto {
    private final long id;
    private final String name;
    private final String surname;
    private final int age;
    private final String email;
    private final String specialization;
}
