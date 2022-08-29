package fr.cefim.birthdayapp.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "birthdays")
public class Birthday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "firstname", columnDefinition = "VARCHAR(50)", nullable = false)
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(50)", nullable = false)
    private String lastname;

    public Birthday() {}

    public Birthday(User user, LocalDate date, String firstname, String lastname) {
        this.user = user;
        this.date = date;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
