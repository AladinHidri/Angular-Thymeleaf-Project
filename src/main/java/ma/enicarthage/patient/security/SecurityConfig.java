package ma.enicarthage.patient.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

// Spring quand il demarre les premieres classes qu'il va l'extencier sont les classe de type @Configuration
@Configuration

// pour dire a SpringWeb que je veux activer la securité
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // on injecté la base de données declaré dans le fichier application.properties
    private DataSource dataSource;

    @Override
    // Preciser la technologie pour que SpringSecurity va chercher les utilisateurs
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /* on a utilisé inMemory Authentication
        PasswordEncoder passwordEncoder = passwordEncoder() ;
        String encodedPWD1  = passwordEncoder.encode("1234") ;
        String encodedPWD2  = passwordEncoder.encode("2345") ;
        String encodedPWD3  = passwordEncoder.encode("3456") ;

        // les utilisateurs qui ont le droit d'acceder a l'application seront stockés en memoire
        auth.inMemoryAuthentication()

                .withUser("user1").password(encodedPWD1).roles("USER")
                .and()
                .withUser("user2").password(encodedPWD2).roles("USER")
                .and()
                .withUser("admin").password(encodedPWD3).roles("USER","ADMIN");
        */

        // on a utilisé JDBC authentication
        PasswordEncoder passwordEncoder = passwordEncoder() ;
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username = ?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception  {

        // specifier a Spring Security qu'on veut utiliser un formulaire d'authentification
        http.formLogin();

        //http.authorizeHttpRequests().antMatchers("/").permitAll();

        // toutes les urls qui contiennent "/delete/..." ou "/edit/.." ou "/save/..." ou "/formPatient/.." necessite d'avoir un role "ADMIN"
        http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN");

        http.authorizeHttpRequests().antMatchers("/user/**").hasRole("USER");

        // toutes les requettes http necessite une authentification
        http.authorizeHttpRequests().anyRequest().authenticated();

        //Configurer les exceptions
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    PasswordEncoder passwordEncoder () {

            // utiliser le cryptage BCrypt
            return new BCryptPasswordEncoder() ;
    }
}
