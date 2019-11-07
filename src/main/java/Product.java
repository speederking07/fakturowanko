public class Product {
    private final int priceNetto;
    private final int vat;
    private final String name;

    public Product(String n, int p, int v){
        this.name = n;
        this.priceNetto = p;
        this.vat = v;
    }

    public int getPriceNetto() {
        return priceNetto;
    }

    public int getVat() {
        return vat;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name+" ("+Facture.priceFormat(priceNetto)+")";
    }
}
