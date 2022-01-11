package bot.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.util.DiscordApiHelper;

@Component
public class DiscordController {

	@Value("${defaultChannelID}")
	private String defaultChannelID;
	
	@Value("${adminID}")
	private String adminID;
	
	@Autowired
	private DiscordApiHelper helper;

	public void simpleMessage(String message) {
		helper.api.getTextChannelById(defaultChannelID).get().sendMessage(message);
	}
	
	public void simpleMessageAndNotifyOwner(String message) {
		try {
			helper.api.getUserById(adminID).get().openPrivateChannel().get().sendMessage(message);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		helper.api.getTextChannelById(defaultChannelID).get().sendMessage(message);
	}
}