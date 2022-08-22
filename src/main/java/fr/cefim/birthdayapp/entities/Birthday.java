package fr.cefim.birthdayapp.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="birthday")
public class Birthday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    @Getter(AccessLevel.NONE)
    private User user;

    private LocalDate date;

    private String firstname;

    private String lastname;

}
