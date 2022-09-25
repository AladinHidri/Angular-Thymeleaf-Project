package ma.enicarthage.patient.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotEmpty // l'attribut nom ne doit pas etre vide
    @Size(min = 4 , max = 10) // le nom du patient doit avoir au minimum 4 characteres et au maximum 10
    private String nom ;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern= "yyyy-MM-dd")
    private Date date_de_naissance;
    private boolean malade ;

    @DecimalMin("100") // le score doit etre supérirur à 1001
    private int score ;

}
