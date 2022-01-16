# MultiBot

Functionalities ->

    Commands:

        Ocr image recognition via ocr command.

        Usage: !ocr https://webpage.com/image.whateverextension

        Shutdown command (can only be performed by the discord server admin): !exit 

        Help command: Displays all the commands available for the user.

    Scheduled tasks:

        Rss feed read.

        Amazon wishlist price tracker.

        Countdown to any date.

Requirements ->

        JAVA 11 + 

        Maven building the project

How to install ->

        1.- Import project into your favourite IDE

        2.- Create your own application.properties using the sample file provided in src/main/resources.

        3.- Install tesseract see: 
        https://www.baeldung.com/java-ocr-tesseract 
        https://digi.bib.uni-mannheim.de/tesseract/
        or this particular version for windows https://digi.bib.uni-mannheim.de/tesseract/tesseract-ocr-w64-setup-v5.0.0-rc1.20211030.exe

        4.- Put tessdata folder of tesseract into src/main/resources project folder

        5.- Run maven goal "clean package"

        6.- Run java project from target folder.

Technologies Used ->

        SpringBoot

        Tesseract4j

        JavaCord

        jsoup

