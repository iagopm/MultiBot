package bot.tasks;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bot.controller.DiscordController;

@Component
public class VandalTask implements DiscordTask{
	
	@Autowired
	DiscordController handler;
	
	@Value("${vandalURL}")
	private String vandalURL;

	public void perform() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(new URL(vandalURL).openStream());
			NodeList portnodeList = doc.getElementsByTagName("title");
			for(int i = 0 ; i < portnodeList.getLength() ; i++) {
				//Send Message
				String message = portnodeList.item(i).getTextContent();
				if(!message.equals("Vandal")) {
					handler.simpleMessage(message);
				}
			}
			System.out.println();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}