package com.creativelabs.scriptscreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ScriptsCreatorApp /*extends SpringBootServletInitializer*/ {

	public static void main(String[] args) {
		SpringApplication.run(ScriptsCreatorApp.class, args);
	}

	/*public static void main(String[] args) {
		SpringApplication.run(ScriptsCreatorApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder application) {
		return application.sources(ScriptsCreatorApp.class);
	}*/

}
