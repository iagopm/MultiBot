package bot.actions;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Component
public class OCRAction {
	@Value("${dataPathTess}")
	private String dataPathTess;

	@Value("${tessLang}")
	private String tessLang;

	@Value("${defaultErrorMessage}")
	private String defaultErrorMessage;

	public String getTextFromImage(String url) {
		URL link = null;
		try {
			link = new URL(url);
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
			return defaultErrorMessage;
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(link);
		} catch (IOException e1) {
			e1.printStackTrace();
			return defaultErrorMessage;
		}
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath(dataPathTess);
		tesseract.setLanguage(tessLang);
		tesseract.setPageSegMode(1);
		tesseract.setOcrEngineMode(1);
		try {
			return tesseract.doOCR(image);
		} catch (TesseractException e) {
			e.printStackTrace();
			return defaultErrorMessage;
		}
	}
}