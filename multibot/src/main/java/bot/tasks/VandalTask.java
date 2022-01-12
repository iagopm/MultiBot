package bot.tasks;

import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bot.controller.DiscordController;

@Component
public class VandalTask implements DiscordTask {

	@Autowired
	private DiscordController handler;

	@Value("${vandalURL}")
	private String vandalURL;

	@Async
	public void perform() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(new URL(vandalURL).openStream());
			NodeList portnodeList = doc.getElementsByTagName("title");
			for (int i = 0; i < portnodeList.getLength(); i++) {
				if (!portnodeList.item(i).getTextContent().equals("Vandal")) {
					handler.simpleMessage(portnodeList.item(i).getTextContent());
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}