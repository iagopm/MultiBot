package bot.util;

import java.time.LocalDateTime;
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
}