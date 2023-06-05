package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloworldApplication {

  @RestController
  class HelloworldController {
    @GetMapping("/")
    String hello() {
      return "Hello Actions! v2";
    }
  }

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}

}
