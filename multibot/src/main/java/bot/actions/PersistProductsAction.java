package bot.actions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PersistProductsAction {

	@Value("${persistFile}")
	private String persistFile;

	public void persist(List<String> products) {
		String text = "";
		for (String p : products) {
			text += p + "\n";
		}
		try {
			FileUtils.writeStringToFile(new File(persistFile), text, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> retrievePersisted() {
		List<String> products = null;
		try {
			products = FileUtils.readLines(new File(persistFile), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return products;
	}

	public boolean fileExists() {
		return new File(persistFile).exists();
	}
}
