package bot.routines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bot.tasks.CountdownTask;
import bot.tasks.VandalTask;
import bot.tasks.WishListTask;
import bot.util.Utils;

@Service
public class Routines {
	@Value("${vandalHour}")
	private Integer vandalHour;

	@Autowired
	private VandalTask vandalTask;

	@Autowired
	private CountdownTask countdownTask;

	@Autowired
	private WishListTask wishListTask;

	@Autowired
	private Utils utils;

	private boolean vandalPublished = false;

	/**
	 * Morning video game news task every day at 10:00 AM
	 */
	@Scheduled(fixedRateString = "${vandalDelay}")
	public void vandalTask() {
		if (utils.isHour(vandalHour) && !vandalPublished) {
			vandalTask.perform();
			vandalPublished = true;
		}
	}

	/**
	 * Countdown to anything
	 */
	@Scheduled(fixedRateString = "${countDownRotate}")
	public void countDownTask() {
		countdownTask.perform();
	}

	/**
	 * Check all wishlist prices
	 */
	@Scheduled(fixedRateString = "${wishlistDelay}")
	public void wishListTask() {
		wishListTask.perform();
	}

	/**
	 * Reset tasks checks
	 */
	@Scheduled(fixedRateString = "${resetDelay}")
	public void resetTask() {
		if (utils.isHour(0) && utils.isMinute(00)) {
			vandalPublished = false;
		}
	}
}