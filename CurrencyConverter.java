package currencyconverterfinal;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class CurrencyConverter extends JFrame { //luo ikkunan

    private static double int1 = 0;
    //Luodaan panelit, tarvitan kolme
    private final JPanel paPohja = new JPanel(new GridLayout(2, 1));
    private final JPanel paYla = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel paAla = new JPanel(new FlowLayout(FlowLayout.LEFT));

    //Luodaan alasvetovalikko
    JComboBox currencyFrom = new JComboBox();
    JComboBox currencyTo = new JComboBox();

    //Luodaan painke
    private static final class JGradientButton extends JButton {

        private JGradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(
                    new Point(0, 0),
                    getBackground(),
                    new Point(0, getHeight() / 3),
                    Color.WHITE));
            g2.fillRect(0, 0, getWidth(), getHeight() / 3);
            g2.setPaint(new GradientPaint(
                    new Point(0, getHeight() / 3),
                    Color.WHITE,
                    new Point(0, getHeight()),
                    getBackground()));
            g2.fillRect(0, getHeight() / 3, getWidth(), getHeight());
            g2.dispose();

            super.paintComponent(g);
        }
    }
    private final JButton btCalculate = new JGradientButton("Calculate");
    private final JButton btClear = new JGradientButton("C");

    //luodaan selite-elementit
    private final JLabel lbEquals = new JLabel("=");
    private final JLabel lbAmount = new JLabel("Amount");
    private final JLabel lbTo = new JLabel("to");

    //luodaan tekstikentät
    private final JTextField tfValue1 = new JTextField(7);
    private final JTextField tfResult = new JTextField(10);

    //url parausta varten muuttujat
    private static final String CURRENCY = "currency";
    private static final String CUBE_NODE = "//Cube/Cube/Cube";
    private static final String RATE = "rate";

    //lista
    public static java.util.List<CurrencyRate> currRateList = new ArrayList<>();

    public CurrencyConverter() {

        getContentPane().setBackground(new java.awt.Color(255, 228, 225)); // muutta ikkunan värin
        setTitle("Currency Converter");//ikkunan otsikko
        setSize(425, 125);//ikkunan koko, lasketan vasemmasta yläkulmasta alkaen, luvut järjestuksessä leveys ja korkeus
        setLocation(800, 300);//ikkunan sijainti, paikka vasemmasta yläkulmasta lukien
        //luvut järjestykseesä matka vasemmasta reunasta,, matka yläreunasta
        //this.pack();//pakkaa komponentit
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Lopettaa ohjelman (jgrsap), kun ikkuna suljetaan

        //lisätään Pää valuutta listaan ja haetaan kurssit urlista ja lisätään listaan...
        currRateList.add(new CurrencyRate("EUR", 1.0));
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
                        double rateV = Double.parseDouble(attribs.getNamedItem(RATE).getNodeValue());
                        currRateList.add(new CurrencyRate(currencyTxt, rateV));
                    }
                }
            }
        } catch (SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }

        asetteleKomponentit();

    }

    private void asetteleKomponentit() {
        //muodostetaan yläpaneli
        paYla.setOpaque(false);
        paYla.add(lbAmount);
        paYla.add(tfValue1);

        for (CurrencyRate s : currRateList) {
            currencyFrom.addItem(s.getCurrency());
            currencyTo.addItem(s.getCurrency());
        }

        paYla.add(currencyFrom);
        paYla.add(lbTo);
        paYla.add(currencyTo);

        //paYla.add(tfArvo2);
        paYla.add(lbEquals);
        paYla.add(tfResult);

        //muodostetaan alapaneli
        paAla.setOpaque(false);
        btCalculate.setBackground(new java.awt.Color(205,92,92));
        paAla.add(btCalculate);
        btClear.setBackground(new java.awt.Color(205,92,92));
        paAla.add(btClear);

        //Liitetään pohjapaneeliin kaksi muta paneelia
        paPohja.setOpaque(false);
        paPohja.add(paYla);
        paPohja.add(paAla);

        //Pohja liitetän ikkunaan
        this.getContentPane().add(paPohja);

        btCalculate.addActionListener(new AlsCalculate());
        btClear.addActionListener(new AlsClear());
    }

    class AlsCalculate implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent tapahtuma) {
            double result;
            double x;
            int curTo = currencyTo.getSelectedIndex();
            int curFro = currencyFrom.getSelectedIndex();
            int1 = Double.parseDouble(tfValue1.getText());
            DecimalFormat df = new DecimalFormat("0.00000");

            if (curFro == 0) {
                result = int1 * currRateList.get(curTo).getRate();
            } else if (curTo == 0) {
                result = int1 / currRateList.get(curFro).getRate();
            } else {
                x = 1 / currRateList.get(curFro).getRate();

                result = (x * int1) * currRateList.get(curTo).getRate();

            }

            tfResult.setText("" + df.format(result));
        }
    }

    class AlsClear implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent tapahtuma) {

            tfValue1.setText("");

            tfResult.setText("");

        }
    }

    public static void main(String[] args) {
        CurrencyConverter window = new CurrencyConverter();
        window.setVisible(true);
    }
}
