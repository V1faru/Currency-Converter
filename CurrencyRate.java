package valuuttamuunnin;


public class CurrencyRate {

    private String currency;
    private String rate; // ?double

    public CurrencyRate(String currency, String rate) {
        super();
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return currency + ", rate: " + rate + " for 1€";
    }

    // equals, hashCode,....
}
