package bot.listeners;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.discord.DiscordApiHelper;
import bot.util.OCRHelper;

@Component
public class Listeners implements InitializingBean {
	@Autowired
	private DiscordApiHelper helper;

	@Autowired
	private OCRHelper ocrHelper;
	
	@Value("${helpCommand}")
	private String helpCommand;

	@Value("${ocrCommand}")
	private String ocrCommand;
	
	@Value("${prefix}")
	private String prefix;

	@Value("${helpText}")
	private String helpText;

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * Help command
		 */
		helper.api.addMessageCreateListener(event -> {
			if (event.getMessageContent().equalsIgnoreCase(prefix + helpCommand)) {
				event.getChannel().sendMessage(helpText);
			}
		});
		/**
		 * Extract text from image
		 */
		helper.api.addMessageCreateListener(event -> {
			if (event.getMessageContent().startsWith(prefix + ocrCommand)) {
				String url =event.getMessageContent().replace(prefix + ocrCommand+" ", "");
				String text = ocrHelper.getTextFromImage(url);
				event.getChannel().sendMessage(text);
			}
		});
	}
}