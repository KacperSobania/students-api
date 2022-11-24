package pl.kacper.studentsapi.mapper;

import pl.kacper.studentsapi.dto.StudentDto;
import pl.kacper.studentsapi.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StudentDtoMapper {

    public static StudentDto mapStudentToStudentDto(Student student){
        return new StudentDto(student.getId(), student.getName(), student.getSurname(), student.getAge(), student.getEmail(), student.getSpecialization());
    }

    public static List<StudentDto> mapStudentsToStudentDtos(List<Student> students){
        return students.stream()
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getSurname(), student.getAge(), student.getEmail(), student.getSpecialization()))
                .collect(Collectors.toList());
    }
}
