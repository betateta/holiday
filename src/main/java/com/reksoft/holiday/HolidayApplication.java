package com.reksoft.holiday;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HolidayApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(HolidayApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
		}


}
