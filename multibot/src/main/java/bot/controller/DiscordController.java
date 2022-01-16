package bot.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.util.DiscordApiHelper;

@Component
public class DiscordController {

	@Value("${defaultChannelID}")
	private String defaultChannelID;

	@Value("#{'${adminID}'.split(',')}") 
	private List<String> adminID;
	
	@Value("${wishlist}")
	private String wishlist;
	
	@Autowired
	private DiscordApiHelper helper;

	public void simpleMessage(String message) {
		helper.getApi().getTextChannelById(defaultChannelID).ifPresent(th -> th.sendMessage(message));
	}

	public void simpleMessageAndNotifyOwner(String message) {
			adminID.forEach(a->{
				try {
					helper.getApi().getUserById(a).get().openPrivateChannel().get().sendMessage(message+" "+wishlist);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			});

		helper.getApi().getTextChannelById(defaultChannelID).ifPresent(th -> th.sendMessage(message));
	}
}