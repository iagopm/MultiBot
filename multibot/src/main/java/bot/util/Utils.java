package bot.util;

import java.util.Calendar;
import org.springframework.stereotype.Component;

@Component
public class Utils {
	public int toMinutes(int number) {
		return number * 60 * 1000;
	}

	public boolean isHour(int hour) {
		return (Calendar.HOUR_OF_DAY == hour);
	}

	public boolean isMinute(int minute) {
		return (Calendar.MINUTE == minute);
	}
}