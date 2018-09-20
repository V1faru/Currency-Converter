package valuuttamuunnin;

import java.util.ArrayList;

class ValuuttaLista {
    ArrayList<Valuutta01> valuutat = new ArrayList<>();


    public ValuuttaLista() {
        // kurssit haettu 17.9.2018
        valuutat.add(new Valuutta01("EUR", 1));
        valuutat.add(new Valuutta01("USD", 1.65094));
        valuutat.add(new Valuutta01("GBP", 0.889892));
        valuutat.add(new Valuutta01("RUB", 79.52860));
        valuutat.add(new Valuutta01("SEK", 10.47331));
        valuutat.add(new Valuutta01("JPY", 130.636903));
        valuutat.add(new Valuutta01("CNY", 8.004823));
        valuutat.add(new Valuutta01("THB", 38.02774));
        valuutat.add(new Valuutta01("INR", 84.46382));
        valuutat.add(new Valuutta01("btc", 0.0001794));

    }
    public Valuutta01 getKurssi(int rate){
            return valuutat.get(rate);
    }
    

}
