package pl.kacper.studentsapi.mapper;

import pl.kacper.studentsapi.dto.TeacherDto;
import pl.kacper.studentsapi.entity.Teacher;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherDtoMapper {
    public static TeacherDto mapTeacherToTeacherDto(Teacher teacher){
        return new TeacherDto(teacher.getId(), teacher.getName(), teacher.getSurname(), teacher.getAge(), teacher.getEmail(), teacher.getSubject());
    }

    public static List<TeacherDto> mapTeachersToTeacherDtos(List<Teacher> teachers){
        return teachers.stream()
                .map(teacher -> new TeacherDto(teacher.getId(), teacher.getName(), teacher.getSurname(), teacher.getAge(), teacher.getEmail(), teacher.getSubject()))
                .collect(Collectors.toList());
    }
}
