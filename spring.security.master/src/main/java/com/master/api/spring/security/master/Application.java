package com.master.api.spring.security.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.master.api.spring.security.master.dto.SaveUser;
import com.master.api.spring.security.master.persistance.entity.User;
import com.master.api.spring.security.master.services.UserService;

@SpringBootApplication
public class Application{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Autowired
//	UserService userService;
//
//
//	User usersaved1 = new User();
//	User usersaved2 = new User();
//	User usersaved3 = new User();
//	@Override
//	public void run(String... args) throws Exception {
//		 SaveUser user = new SaveUser();
//		 user.setName("luis márquez");
//		 user.setUsername("lmarquez");
//		 user.setPassword("marquecito");
//		 user.setRepeatPassword("marquecito");
//
//		 this.usersaved1 = this.userService.createOneCustomer(user);
//
//		 SaveUser user2 = new SaveUser();
//		 user2.setName("fulano pérez");
//		 user2.setUsername("fperez");
//		 user2.setPassword("fulanito");
//		 user2.setRepeatPassword("fulanito");
//		 this.usersaved2 = this.userService.createOneCustomer(user2);
//
//		 SaveUser user3 = new SaveUser();
//		 user3.setName("Mariano Hernández");
//		 user3.setUsername("mhernandez");
//		 user3.setPassword("marianito");
//		 user3.setRepeatPassword("marianito");
//		 this.usersaved3 = this.userService.createOneCustomer(user3);
//	}

}


// contraseña correo bitforging KeIoseepyFYPAZ8  bitforging@gmail.com