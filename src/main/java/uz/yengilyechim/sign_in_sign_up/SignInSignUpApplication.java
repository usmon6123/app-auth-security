package uz.yengilyechim.sign_in_sign_up;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("uz.yengilyechim.sign_in_sign_up.entity")
@EnableJpaRepositories("uz.yengilyechim.sign_in_sign_up.repository")
public class SignInSignUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignInSignUpApplication.class, args);
    }

}
