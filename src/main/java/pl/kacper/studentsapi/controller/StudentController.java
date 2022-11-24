package pl.kacper.studentsapi.controller;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.*;
import pl.kacper.studentsapi.dto.StudentDto;
import pl.kacper.studentsapi.dto.TeacherDto;
import pl.kacper.studentsapi.entity.Student;
import pl.kacper.studentsapi.service.StudentService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static pl.kacper.studentsapi.mapper.StudentDtoMapper.mapStudentToStudentDto;
import static pl.kacper.studentsapi.mapper.StudentDtoMapper.mapStudentsToStudentDtos;
import static pl.kacper.studentsapi.mapper.TeacherDtoMapper.mapTeachersToTeacherDtos;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add-student")
    public StudentDto saveStudent(@RequestBody @Valid Student student){
        return mapStudentToStudentDto(studentService.saveStudent(student));
    }

    @PutMapping("/students")
    public StudentDto editStudent(@RequestBody @Valid Student student){
        return mapStudentToStudentDto(studentService.editStudent(student));
    }

    @DeleteMapping("/delete-student/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

    @PostMapping("/students/{studentId}/assign-teacher/{teacherId}")
    public void assignTeacherToStudent(@PathVariable Long studentId, @PathVariable Long teacherId){
        studentService.assignTeacherToStudent(studentId, teacherId);
    }

    @DeleteMapping("/students/{studentId}/detach-teacher/{teacherId}")
    public void detachTeacherFromStudent(@PathVariable Long studentId, @PathVariable Long teacherId){
        studentService.detachTeacherFromStudent(studentId, teacherId);
    }

    @GetMapping("/students")
    public List<StudentDto> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id,asc") String[] sort){
        int pageNumber = page >= 0 ? page : 0;
        int pageSize = page > 0 ? page : 5;

        List<Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] sortField = sortOrder.split(",");
                orders.add(new Order(Sort.Direction.fromString(sortField[1]), sortField[0]));
            }
        } else {
            orders.add(new Order(Sort.Direction.fromString(sort[1]), sort[0]));
        }
        return mapStudentsToStudentDtos(studentService.getStudents(pageNumber, pageSize, orders));
    }

    @GetMapping("/students/{studentId}/teachers")
    public List<TeacherDto> getTeachersOfStudent(@PathVariable Long studentId){
        return mapTeachersToTeacherDtos(studentService.getTeachersOfStudent(studentId));
    }

    @GetMapping("/student")
    public List<StudentDto> getStudentByNameAndSurname(@RequestParam String name, @RequestParam String surname){
        return mapStudentsToStudentDtos(studentService.getStudentByNameAndSurname(name, surname));
    }

    @GetMapping("/students/{id}")
    public StudentDto getStudentById(@PathVariable Long id){
        return mapStudentToStudentDto(studentService.getStudentById(id));
    }



}
