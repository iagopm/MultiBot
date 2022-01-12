package bot.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.controller.DiscordController;

@Component
public class CountdownTask implements DiscordTask {

	@Autowired
	DiscordController handler;

	@Value("${date}")
	private String date;

	public static final int SECONDS_IN_A_DAY = 24 * 60 * 60;

	@Override
	public void perform() {
		Date target = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			target = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date today = new Date();

		long difference = target.getTime() - today.getTime();

		long diffSec = difference / 1000;

		long days = diffSec / SECONDS_IN_A_DAY;
		long secondsDay = diffSec % SECONDS_IN_A_DAY;
		long seconds = secondsDay % 60;
		long minutes = (secondsDay / 60) % 60;
		long hours = (secondsDay / 3600);

		handler.simpleMessage(
				"[ELDEN RING] " + days + " días " + hours + " horas " + minutes + " minutos " + seconds + " segundos");
	}
}