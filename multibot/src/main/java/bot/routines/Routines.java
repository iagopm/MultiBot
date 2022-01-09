package bot.routines;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.tasks.CountdownTask;
import bot.tasks.VandalTask;
import bot.tasks.WishListTask;
import bot.util.Utils;

@Component
public class Routines implements InitializingBean {

	@Value("${vandalDelay}")
	private Integer vandalDelay;

	@Value("${resetDelay}")
	private Integer resetDelay;

	@Value("${vandalHour}")
	private Integer vandalHour;

	@Value("${countDownRotate}")
	private Integer countDownRotate;

	@Value("${wishlistDelay}")
	private Integer wishlistDelay;

	@Autowired
	private VandalTask vandalTask;

	@Autowired
	private CountdownTask countdownTask;

	@Autowired
	private WishListTask wishListTask;

	@Autowired
	private Utils utils;

	private boolean vandalPublished = false;

	final Timer timer = new Timer();

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * Check all amazon prices
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				wishListTask.perform();
			}
		}, 1000, utils.toMinutes(wishlistDelay));
		/**
		 * Countdown to anything
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				countdownTask.perform();
			}
		}, 1000, utils.toMinutes(countDownRotate));
		/**
		 * Morning video game news task every day at 10:00 AM
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (utils.isHour(10) && !vandalPublished) {
					vandalTask.perform();
					vandalPublished = true;
				}
			}
		}, 1000, utils.toMinutes(vandalDelay));
		/**
		 * It resets all routines at 00:00 AM
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (utils.isHour(0) && utils.isMinute(00)) {
					vandalPublished = false;
				}
			}
		}, 1000, utils.toMinutes(resetDelay));
	}
}