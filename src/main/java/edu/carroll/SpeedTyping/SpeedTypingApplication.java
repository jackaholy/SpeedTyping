package edu.carroll.SpeedTyping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeedTypingApplication {
	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
}