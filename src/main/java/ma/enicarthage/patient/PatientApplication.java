package ma.enicarthage.patient;

import ma.enicarthage.patient.entities.Patient;
import ma.enicarthage.patient.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientApplication.class, args);
    }

    // @Bean permet au demrage d'exuter la methode commandLineRunner()
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args ->  {
            patientRepository.save(
                    new Patient(null,"ahmede",new Date() , false ,100)
            ) ;
            patientRepository.save(
                    new Patient(null,"aladin",new Date() , true ,200)
            );
            patientRepository.save(
                    new Patient(null,"youssef",new Date() , false ,300)
            );
            patientRepository.save(
                    new Patient(null,"malek",new Date() , true ,400)
            );
            patientRepository.findAll().forEach(p-> {
                System.out.println(p.getNom());
            });
        };
    }
}
