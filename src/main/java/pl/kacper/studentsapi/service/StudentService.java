package pl.kacper.studentsapi.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import pl.kacper.studentsapi.entity.Student;
import pl.kacper.studentsapi.entity.Teacher;
import pl.kacper.studentsapi.repository.StudentRepository;
import pl.kacper.studentsapi.repository.TeacherRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    @Transactional
    public Student editStudent(Student student){
        Student studentEdited = studentRepository.findById(student.getId()).orElseThrow();
        studentEdited.setName(student.getName());
        studentEdited.setSurname(student.getSurname());
        studentEdited.setAge(student.getAge());
        studentEdited.setEmail(student.getEmail());
        studentEdited.setSpecialization(student.getSpecialization());
        return studentRepository.save(studentEdited);
    }

    public void deleteStudent(Long studentId){
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void assignTeacherToStudent(Long studentId, Long teacherId){
        Student student = getStudentById(studentId);
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        student.getTeachers().add(teacher);
        studentRepository.save(student);
    }

    @Transactional
    public void detachTeacherFromStudent(Long studentId, Long teacherId){
        Student student = getStudentById(studentId);
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        student.getTeachers().remove(teacher);
        studentRepository.save(student);
    }

    public List<Student> getStudents(int page, int pageSize, List<Order> orders){
        return studentRepository.findAll(PageRequest.of(page, pageSize, Sort.by(orders))).stream().toList();
    }

    public List<Teacher> getTeachersOfStudent(Long studentId){
        return studentRepository.findById(studentId).orElseThrow().getTeachers();
    }

    public List<Student> getStudentByNameAndSurname(String name, String surname){
        return studentRepository.findAllByNameAndSurname(name, surname);
    }

    public Student getStudentById(Long studentId){
        return studentRepository.findById(studentId).orElseThrow();
    }

}
