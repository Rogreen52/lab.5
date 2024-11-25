package com.example.lab5;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<String> parseXML(InputStream inputStream) {
        List<String> currencyList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            NodeList items = document.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element element = (Element) items.item(i);
                String currency = element.getElementsByTagName("targetCurrency").item(0).getTextContent();
                String rate = element.getElementsByTagName("exchangeRate").item(0).getTextContent();
                currencyList.add(currency + " - " + rate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencyList;
    }
}
