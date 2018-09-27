package currencyconverterfinal;




public class CurrencyRate {

    private String currency;
    private double rate; 

    public CurrencyRate(String currency, Double rate) {
        super();
        this.currency = currency;
        this.rate = rate;
    }
    
    public CurrencyRate (String currency){
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return currency + ", rate: " + rate + " for 1€";
    }

   
}
