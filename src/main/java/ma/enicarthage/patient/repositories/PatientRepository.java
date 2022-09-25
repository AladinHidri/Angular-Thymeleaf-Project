package ma.enicarthage.patient.repositories;

import ma.enicarthage.patient.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    // une methode qui retourne une page contenant le nom keyword
    Page<Patient> findByNomContains(String keyword, Pageable pageable) ;
}
