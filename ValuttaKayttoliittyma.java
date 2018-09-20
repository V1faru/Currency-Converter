package valuuttamuunnin;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

class Laskin4 extends JFrame//luo ikkunan
{

    private static double luku1 = 0;
    //Luodaan panelit, tarvitan kolme
    private final JPanel paPohja = new JPanel(new GridLayout(2, 1));
    private final JPanel paYla = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel paAla = new JPanel(new FlowLayout(FlowLayout.LEFT));

    //Luodaan alasvetovalikko
    JComboBox kmistaValinta = new JComboBox();
    JComboBox kmihinValinta = new JComboBox();

    //Luodaan painke
    private final JButton btLaske = new JButton("Laske");
    private final JButton btTyhjaa = new JButton("C");

    //luodaan selite-elementit
    private final JLabel lbYhtasuuri = new JLabel("=");
    private final JLabel lbMaara = new JLabel("Määrä");
    private final JLabel lbMihin = new JLabel("Mihin");

    //luodaan tekstikentät
    private final JTextField tfArvo1 = new JTextField(3);
    private final JTextField tfTulos = new JTextField(7);

    public Laskin4(/*ArrayList<Valuutta01> valuutat*/) {
        setTitle("Valuutta Muunnin");//ikkunan otsikko
        setSize(400, 150);//ikkunan koko, lasketan vasemmasta yläkulmasta alkaen, luvut järjestuksessä leveys ja korkeus
        setLocation(800, 300);//ikkunan sijainti, paikka vasemmasta yläkulmasta lukien
        //luvut järjestykseesä matka vasemmasta reunasta,, matka yläreunasta
        asetteleKomponentit();
        //this.pack();//pakkaa komponentit
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Lopettaa ohjelman (jgrsap), kun ikkuna suljetaan
    }

    private void asetteleKomponentit() {
        //muodostetaan yläpaneli
        paYla.add(lbMaara);
        paYla.add(tfArvo1);

        kmistaValinta.addItem("EUR");

        kmihinValinta.addItem("USD");
        kmihinValinta.addItem("GBP");
        kmihinValinta.addItem("RUB");
        kmihinValinta.addItem("SEK");
        kmihinValinta.addItem("JPY");
        kmihinValinta.addItem("CNY");
        kmihinValinta.addItem("THB");
        kmihinValinta.addItem("INR");
        kmihinValinta.addItem("BTC");

        paYla.add(kmistaValinta);
        paYla.add(lbMihin);
        paYla.add(kmihinValinta);

        //paYla.add(tfArvo2);
        paYla.add(lbYhtasuuri);
        paYla.add(tfTulos);

        //muodostetaan alapaneli
        paAla.add(btLaske);
        paAla.add(btTyhjaa);

        //Liitetään pohjapaneeliin kaksi muta paneelia
        paPohja.add(paYla);
        paPohja.add(paAla);

        //Pohja liitetän ikkunaan
        this.getContentPane().add(paPohja);

        btLaske.addActionListener(new AlsLaske());
        btTyhjaa.addActionListener(new AlsTyhjaa());
    }

    class AlsLaske implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent tapahtuma) {
            double tulos;
            int merkki = kmihinValinta.getSelectedIndex();
            luku1 = Double.parseDouble(tfArvo1.getText());
            DecimalFormat df = new DecimalFormat("0.00");

            switch (merkki) {
                case 0:
                    tulos = luku1 * 1.65094; // USD
                    break;
                case 1:
                    tulos = luku1 * 0.889892; //GBP
                    break;
                case 2:
                    tulos = luku1 * 79.52860; //RUB
                    break;
                case 3:
                    tulos = luku1 * 10.47331; //SEK
                    break;
                case 4:
                    tulos = luku1 * 130.636903; //JPY
                    break;
                case 5:
                    tulos = luku1 * 8.004823; //CNY
                    break;
                case 6:
                    tulos = luku1 * 38.02774; //THB
                    break;
                case 7:
                    tulos = luku1 * 84.46382; //INR
                    break;
                default:
                    tulos = luku1 * 0.0001794; //BTC
                    break;
            }

            tfTulos.setText("" + df.format(tulos));
        }
    }

    class AlsTyhjaa implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent tapahtuma) {

            tfArvo1.setText("");

            tfTulos.setText("");

        }
    }

    public static void main(String[] args) {
        Laskin4 ikkuna = new Laskin4();
        ikkuna.setVisible(true);
    }
}


