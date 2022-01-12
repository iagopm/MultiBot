package bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import bot.listeners.Listeners;
import bot.routines.Routines;

@EnableScheduling
@SpringBootApplication
public class MultibotApplication {
	/**
	 * All the routines the bot perform
	 */
	@SuppressWarnings("unused")
	@Autowired
	private Routines routines;
	/**
	 * All the commands the bot recieves
	 */
	@SuppressWarnings("unused")
	@Autowired
	private Listeners listeners;

	public static void main(String[] args) {
		logo();
		SpringApplication.run(MultibotApplication.class, args);
	}

	private static void logo() {
		System.out.println("-----------------------------------------------\n"
				+ "              .__   __  ._____.           __   \n"
				+ "  _____  __ __|  |_/  |_|__\\_ |__   _____/  |_ \n"
				+ " /     \\|  |  \\  |\\   __\\  || __ \\ /  _ \\   __\\\n"
				+ "|  Y Y  \\  |  /  |_|  | |  || \\_\\ (  <_> )  |  \n"
				+ "|__|_|  /____/|____/__| |__||___  /\\____/|__|  \n"
				+ "      \\/                        \\/             \n"
				+ "-----------------------------------------------");
	}
}