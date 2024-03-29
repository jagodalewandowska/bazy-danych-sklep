package pbs.edu.rekrutacja.init;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pbs.edu.rekrutacja.models.ERole;
import pbs.edu.rekrutacja.models.Role;
import pbs.edu.rekrutacja.models.User;
import pbs.edu.rekrutacja.repository.RoleRepository;
import pbs.edu.rekrutacja.repository.UserRepository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeAdministrator();
    }

    private void initializeRoles() {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_MODERATOR");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {
        ERole roleEnum = ERole.valueOf(roleName);
        if (!roleRepository.existsByName(roleEnum)) {
            Role role = new Role(roleEnum);
            roleRepository.save(role);
            System.out.println("Role added: " + roleName);
        }
    }

    private void initializeAdministrator() {
        String adminUsername = "admin";
        String adminEmail = "administrator@gmail.com";
        String adminPassword = "admin123";
        String adminName = "Administrator";
        String adminSurname = "Administrator";
        if (!userRepository.existsByUsername(adminUsername)) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow());
            User admin = new User(adminUsername, adminEmail, passwordEncoder.encode(adminPassword), adminName, adminSurname, roles);
            userRepository.save(admin);
            System.out.println("Administrator added: " + adminUsername);
        }
    }

}
