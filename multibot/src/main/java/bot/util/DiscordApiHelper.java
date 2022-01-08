package bot.util;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class DiscordApiHelper implements InitializingBean{
	
	@Value("${token}")
	private String token;
	
	public DiscordApi api;

	@Override
	public void afterPropertiesSet() throws Exception {
		api = new DiscordApiBuilder().setToken(token).login().join();
	}
}
