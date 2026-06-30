package org.interviews.doodle;

import org.springframework.boot.SpringApplication;

public class TestDoodleApplication {

	public static void main(String[] args) {
		SpringApplication.from(DoodleApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
