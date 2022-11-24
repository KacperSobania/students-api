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
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public Teacher saveTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public Teacher editTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    @Transactional
    public void deleteTeacher(Long teacherId){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        for(Student student : teacher.getStudents()){
            student.getTeachers().remove(teacher);
        }
        teacherRepository.deleteById(teacherId);
    }

    @Transactional
    public void assignStudentToTeacher(Long teacherId, Long studentId){
        Teacher teacher = getTeacherById(teacherId);
        Student student = studentRepository.findById(studentId).orElseThrow();
        teacher.getStudents().add(student);
        student.getTeachers().add(teacher);
        studentRepository.save(student);
        teacherRepository.save(teacher);
    }

    @Transactional
    public void detachStudentFromTeacher(Long teacherId, Long studentId){
        Teacher teacher = getTeacherById(teacherId);
        Student student = studentRepository.findById(studentId).orElseThrow();
        teacher.getStudents().remove(student);
        student.getTeachers().remove(teacher);
        teacherRepository.save(teacher);
        studentRepository.save(student);
    }

    public List<Teacher> getTeachers(int page, int pageSize, List<Order> orders){
        return teacherRepository.findAll(PageRequest.of(page, pageSize, Sort.by(orders))).stream().toList();
    }

    public List<Student> getStudentsOfTeacher(Long id){
        return teacherRepository.findById(id).orElseThrow().getStudents();
    }

    public List<Teacher> getTeacherByNameAndSurname(String name, String surname){
        return teacherRepository.findAllByNameAndSurname(name, surname);
    }

    public Teacher getTeacherById(Long teacherId){
        return teacherRepository.findById(teacherId).orElseThrow();
    }
}
