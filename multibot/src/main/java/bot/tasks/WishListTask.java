package bot.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bot.actions.PersistProductsAction;
import bot.controller.DiscordController;
import bot.util.Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class WishListTask implements DiscordTask {
	
	@Value("${wishlist}")
	private String wishlist;

	@Value("${priceChangedMessage}")
	private String priceChangedMessage;

	@Value("${listChangedMessage}")
	private String listChangedMessage;

	@Value("${botDetectedMessage}")
	private String botDetectedMessage;

	@Autowired
	private DiscordController handler;

	@Autowired
	private PersistProductsAction persistor;

	private List<String> products = new ArrayList<>();

	private boolean firstTime = true;

	@Override
	public void perform() {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(wishlist).method("GET", null)
				.addHeader("Cookie", "i18n-prefs=EUR; session-id=259-2705930-2834722; session-id-time=2082787201l")
				.build();
		Response response = null;
		String text = null;
		try {
			response = client.newCall(request).execute();
			text = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (firstTime && persistor.fileExists()) {
			setProducts(persistor.retrievePersisted());
			List<String> newProducts = loadAllProducts(text);
			compareProducts(newProducts);
			firstTime = false;
		} else {
			List<String> newProducts = loadAllProducts(text);
			compareProducts(newProducts);
			firstTime = false;
		}
		printProducts();
	}

	private void compareProducts(List<String> newProducts) {
		if (newProducts.isEmpty()) {
			handler.simpleMessageAndNotifyOwner(botDetectedMessage);
			return;
		}
		if (newProducts.size() != products.size()) {
			handler.simpleMessageAndNotifyOwner(listChangedMessage);
			setProducts(newProducts);
			return;
		}
		newProducts.stream()
		.filter(p -> !p.equals(this.products.get(newProducts.indexOf(p))))
		.forEach(p -> 
		{handler.simpleMessageAndNotifyOwner(priceChangedMessage + this.products.get(newProducts.indexOf(p)) + " -> " + p);setProducts(newProducts);});
	}

	private List<String> loadAllProducts(String text) {
		List<String> newProducts = new ArrayList<>();
		// <span class="a-offscreen">99,00&nbsp;€</span>
		Document doc = Jsoup.parse(text);
		Elements elements = doc.select("span.a-offscreen");
		elements.forEach(e -> newProducts.add(e.text().replace(" ", "")));
		return newProducts;
	}

	private void setProducts(List<String> products) {
		persistor.persist(products);
		this.products = products;
	}

	private void printProducts() {
		StringBuilder productsText = new StringBuilder("");
		this.products.forEach(p -> productsText.append("[" + p + "]"));
		Utils.log(productsText.toString());
	}
}