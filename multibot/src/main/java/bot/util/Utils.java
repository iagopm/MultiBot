package bot.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	public int toMinutes(int number) {
		return number * 60 * 1000;
	}

	public boolean isHour(int hour) {
		return (LocalDateTime.now().getHour() == hour);
	}

	public boolean isMinute(int minute) {
		return (LocalDateTime.now().getMinute() == minute);
	}

	public static void log(Object message) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		System.out.println(formatter.format(new Date()) + "  -  " + message.toString());
	}
}