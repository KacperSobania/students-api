package pl.kacper.studentsapi.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.kacper.studentsapi.dto.StudentDto;
import pl.kacper.studentsapi.dto.TeacherDto;
import pl.kacper.studentsapi.entity.Teacher;
import pl.kacper.studentsapi.service.TeacherService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static pl.kacper.studentsapi.mapper.StudentDtoMapper.mapStudentsToStudentDtos;
import static pl.kacper.studentsapi.mapper.TeacherDtoMapper.mapTeacherToTeacherDto;
import static pl.kacper.studentsapi.mapper.TeacherDtoMapper.mapTeachersToTeacherDtos;

@RestController
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/add-teacher")
    public TeacherDto saveTeacher(@RequestBody @Valid Teacher teacher){
        return mapTeacherToTeacherDto(teacherService.saveTeacher(teacher));
    }

    @PutMapping("/teacher")
    public TeacherDto editTeacher(@RequestBody @Valid Teacher teacher){
        return mapTeacherToTeacherDto(teacherService.editTeacher(teacher));
    }

    @DeleteMapping("/delete-teacher/{id}")
    public void deleteTeacher(@PathVariable Long id){
        teacherService.deleteTeacher(id);
    }

    @PostMapping("/teachers/{teacherId}/assign-student/{studentId}")
    public void assignStudentToTeacher(@PathVariable Long teacherId, @PathVariable Long studentId){
        teacherService.assignStudentToTeacher(teacherId, studentId);
    }

    @DeleteMapping("/teachers/{teacherId}/detach-student/{studentId}")
    public void detachStudentFromTeacher(@PathVariable Long teacherId, @PathVariable Long studentId){
        teacherService.detachStudentFromTeacher(teacherId, studentId);
    }

    @GetMapping("/teachers")
    public List<TeacherDto> getTeachers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id,asc") String[] sort){
        int pageNumber = page >= 0 ? page : 0;
        int pageSize = page > 0 ? page : 5;

        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] sortField = sortOrder.split(",");
                orders.add(new Sort.Order(Sort.Direction.fromString(sortField[1]), sortField[0]));
            }
        } else {
            orders.add(new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]));
        }
        return mapTeachersToTeacherDtos(teacherService.getTeachers(pageNumber, pageSize, orders));
    }

    @GetMapping("/teachers/{teacherId}/students")
    public List<StudentDto> getStudentsOfTeacher(@PathVariable Long teacherId){
        return mapStudentsToStudentDtos(teacherService.getStudentsOfTeacher(teacherId));
    }

    @GetMapping("/teacher")
    public List<TeacherDto> getTeacherByNameAndSurname(@RequestParam String name, @RequestParam String surname){
        return mapTeachersToTeacherDtos(teacherService.getTeacherByNameAndSurname(name, surname));
    }

    @GetMapping("/teachers/{id}")
    public TeacherDto getTeacherById(@PathVariable Long id){
        return mapTeacherToTeacherDto(teacherService.getTeacherById(id));
    }

}
