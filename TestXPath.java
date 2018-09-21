package valuuttamuunnin;


import valuuttamuunnin.CurrencyRate;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestXPath {

    private static final String CURRENCY = "currency";
    private static final String CUBE_NODE = "//Cube/Cube/Cube";
    private static final String RATE = "rate";

    public static void main(String[] args) {
        List<CurrencyRate> currRateList = new ArrayList<>();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        String spec = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        try {
            URL url = new URL(spec);
            InputStream is = url.openStream();
            document = builder.parse(is);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            String xPathString = CUBE_NODE;
            XPathExpression expr = xpath.compile(xPathString);
            NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                NamedNodeMap attribs = node.getAttributes();
                if (attribs.getLength() > 0) {
                    Node currencyAttrib = attribs.getNamedItem(CURRENCY);
                    if (currencyAttrib != null) {
                        String currencyTxt = currencyAttrib.getNodeValue();
                        String rateTxt = attribs.getNamedItem(RATE).getNodeValue();
                        currRateList.add(new CurrencyRate(currencyTxt, rateTxt));
                    }
                }
            }
        } catch (SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }

        for (CurrencyRate currencyRate : currRateList) {
            System.out.println(currencyRate);
        }
    }
}


/*
21,09,2018
CurrencyRate [currency=USD, rate=1.1759]
CurrencyRate [currency=JPY, rate=132.44]
CurrencyRate [currency=BGN, rate=1.9558]
CurrencyRate [currency=CZK, rate=25.585]
CurrencyRate [currency=DKK, rate=7.4597]
CurrencyRate [currency=GBP, rate=0.89400]
CurrencyRate [currency=HUF, rate=324.05]
CurrencyRate [currency=PLN, rate=4.2946]
CurrencyRate [currency=RON, rate=4.6581]
CurrencyRate [currency=SEK, rate=10.3315]
CurrencyRate [currency=CHF, rate=1.1228]
CurrencyRate [currency=ISK, rate=129.40]
CurrencyRate [currency=NOK, rate=9.5793]
CurrencyRate [currency=HRK, rate=7.4278]
CurrencyRate [currency=RUB, rate=78.5108]
CurrencyRate [currency=TRY, rate=7.3935]
CurrencyRate [currency=AUD, rate=1.6154]
CurrencyRate [currency=BRL, rate=4.7920]
CurrencyRate [currency=CAD, rate=1.5197]
CurrencyRate [currency=CNY, rate=8.0503]
CurrencyRate [currency=HKD, rate=9.1840]
CurrencyRate [currency=IDR, rate=17424.92]
CurrencyRate [currency=ILS, rate=4.2043]
CurrencyRate [currency=INR, rate=84.8905]
CurrencyRate [currency=KRW, rate=1312.42]
CurrencyRate [currency=MXN, rate=22.2132]
CurrencyRate [currency=MYR, rate=4.8565]
CurrencyRate [currency=NZD, rate=1.7606]
CurrencyRate [currency=PHP, rate=63.657]
CurrencyRate [currency=SGD, rate=1.6042]
CurrencyRate [currency=THB, rate=38.140]
CurrencyRate [currency=ZAR, rate=16.8918]
*/