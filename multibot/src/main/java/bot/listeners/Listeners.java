package bot.listeners;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.actions.OCRAction;
import bot.util.DiscordApiHelper;

@Component
public class Listeners implements InitializingBean {
	@Autowired
	private DiscordApiHelper helper;

	@Autowired
	private OCRAction ocrHelper;

	@Value("${helpCommand}")
	private String helpCommand;

	@Value("${ocrCommand}")
	private String ocrCommand;

	@Value("${shutDownCommand}")
	private String shutDownCommand;

	@Value("#{'${adminID}'.split(',')}") 
	private List<String> adminID;

	@Value("${prefix}")
	private String prefix;

	@Value("${helpText}")
	private String helpText;

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * Help command
		 */
		helper.getApi().addMessageCreateListener(event -> {
			if (event.getMessageContent().equalsIgnoreCase(prefix + helpCommand)) {
				event.getChannel().sendMessage(helpText);
			}
		});
		/**
		 * Extract text from image
		 */
		helper.getApi().addMessageCreateListener(event -> {
			if (event.getMessageContent().startsWith(prefix + ocrCommand)) {
				String url = event.getMessageContent().replace(prefix + ocrCommand + " ", "");
				String text = ocrHelper.getTextFromImage(url);
				event.getChannel().sendMessage(text);
			}
		});
		/**
		 * Shutdown bot
		 */
		helper.getApi().addMessageCreateListener(event -> {
			if (event.getMessageContent().startsWith(prefix + shutDownCommand)
					&& adminID.contains(event.getMessageAuthor().getId()+"")) {
				System.exit(0);
			}
		});
	}
}