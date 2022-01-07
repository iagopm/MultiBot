package bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bot.listeners.Listeners;
import bot.routines.Routines;

@SpringBootApplication
public class MultibotApplication {
	@Autowired
	private Routines routines;
	
	@Autowired
	private Listeners listeners;
	
	public static void main(String[] args) {
		SpringApplication.run(MultibotApplication.class, args);
	}
}