package com.nexcodemm.lms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nexcodemm.lms.model.entities.Role;
import com.nexcodemm.lms.model.entities.Setting;
import com.nexcodemm.lms.model.entities.User;
import com.nexcodemm.lms.repository.RoleRepository;
import com.nexcodemm.lms.repository.SettingRepository;
import com.nexcodemm.lms.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DefaultSettingInitializer implements CommandLineRunner {

	private final SettingRepository settingRepository;// Assuming you have a SettingRepository for database operations

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		// Check if the settings are already present in the database
		if (!settingRepository.existsById(1L)) {
			// Create and set default values for the Setting entity
			Setting defaultSetting = new Setting();
			defaultSetting.setBookLimits(0); // Set your default values here
			defaultSetting.setRentableDays(0);
			defaultSetting.setExtendableDays(0);
			defaultSetting.setExtendableTimes(0);

			settingRepository.save(defaultSetting);
		}

//		if (!roleRepository.existsById(1L)) {
//			Role role = new Role();
//			role.setName("ADMIN");
//			roleRepository.save(role);
//		}

		User user = userRepository.findByUsername("admin").orElse(null);
		if (user == null) {
			user = new User();
			user.setUsername("admin");

			List<Role> roles = new ArrayList<>();

			Role adminRole = roleRepository.findByName("ADMIN");
			if (adminRole == null) {
		       
		        adminRole = new Role();
		        adminRole.setName("ADMIN");
		        roleRepository.save(adminRole);
		    }
		
			roles.add(adminRole);

			user.setRoles(roles);

			String encodedPassword = passwordEncoder.encode("Admin@123");
			user.setPassword(encodedPassword);

			userRepository.save(user);
		}
	}
}
