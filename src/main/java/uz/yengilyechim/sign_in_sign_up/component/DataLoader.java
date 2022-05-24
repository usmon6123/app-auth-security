package uz.yengilyechim.sign_in_sign_up.component;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.yengilyechim.sign_in_sign_up.entity.Role;
import uz.yengilyechim.sign_in_sign_up.entity.User;
import uz.yengilyechim.sign_in_sign_up.enums.PermissionEnum;
import uz.yengilyechim.sign_in_sign_up.repository.RoleRepository;
import uz.yengilyechim.sign_in_sign_up.repository.UserRepository;
import uz.yengilyechim.sign_in_sign_up.utils.AppConstant;

import java.util.Arrays;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Value("${dataLoaderMode}")
    private String dataLoaderMode;


    @Override
    public void run(String... args) throws Exception {
        if (dataLoaderMode.equals("always")) {
            Role admin = roleRepository.save(new Role(
                    AppConstant.ADMIN,
                    "bu admin",
                    new HashSet<>(Arrays.asList(PermissionEnum.values()))));

            roleRepository.save(new Role(
                    AppConstant.USER,
                    "bu client",
                    new HashSet<>()));

            userRepository.save(new User(
                    "superAdmin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true));
        }
    }
}
