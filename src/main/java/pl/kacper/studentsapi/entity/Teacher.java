package pl.kacper.studentsapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 3)
    private String name;
    private String surname;
    @Min(18)
    private int age;
    @Email
    private String email;
    private String subject;
    @ManyToMany(mappedBy = "teachers")
    private List<Student> students;
}
