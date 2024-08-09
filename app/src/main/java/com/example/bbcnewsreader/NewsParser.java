package com.example.bbcnewsreader;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class NewsParser extends DefaultHandler {
    private final List<NewsItem> newsItems = new ArrayList<>();
    private NewsItem currentItem;
    private StringBuilder currentText;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("item")) {
            currentItem = new NewsItem("", "", "", "");
        }
        currentText = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (currentItem != null) {
            switch (qName) {
                case "title":
                    currentItem.setTitle(currentText.toString());
                    break;
                case "description":
                    currentItem.setDescription(currentText.toString());
                    break;
                case "link":
                    currentItem.setUrl(currentText.toString());
                    break;
                case "pubDate":
                    currentItem.setDate(currentText.toString());
                    break;
                case "item":
                    newsItems.add(currentItem);
                    currentItem = null;
                    break;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentText != null) {
            currentText.append(ch, start, length);
        }
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }
}
