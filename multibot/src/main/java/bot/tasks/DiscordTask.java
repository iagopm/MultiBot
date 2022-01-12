package bot.tasks;

import org.springframework.scheduling.annotation.Async;

public interface DiscordTask {
	@Async
	public void perform();
}
