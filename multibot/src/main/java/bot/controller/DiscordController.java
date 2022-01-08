package bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.util.DiscordApiHelper;

@Component
public class DiscordController {

	@Value("${defaultChannelID}")
	private String defaultChannelID;
	
	@Autowired
	private DiscordApiHelper helper;

	public void simpleMessage(String message) {
		helper.api.getTextChannelById(defaultChannelID).get().sendMessage(message);
	}
}