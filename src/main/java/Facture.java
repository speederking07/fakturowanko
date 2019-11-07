import org.javatuples.Pair;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.Date;

class Facture {
    public Customer getCustomer() {
        return customer;
    }

    public Date getDate() {
        return date;
    }

    private final Customer customer;
    private final AbstractList<Pair<Product, Integer>> productList;
    private final Date date;

    public Facture(Customer cust, AbstractList<Pair<Product, Integer>> products, Date d) {
        this.customer = cust;
        this.productList = products;
        this.date = d;
    }

    public static String priceFormat(final int p) {
        return Integer.toString(p / 100) + "." + extraZero(p % 100) + " PLN";
    }

    //Fix it
    public static String dateFormat(final Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(d);
    }

    private static String extraZero(int a){
        if(a < 10){
            return "0"+Integer.toString(a);
        }
        else{
            return Integer.toString(a);
        }
    }

    public String[][] productsToTable() {
        String[][] res = new String[productList.size()][7];
        int i = 0;
        for (Pair<Product, Integer> p : productList) {
            res[i][0] = p.getValue0().getName();
            res[i][1] = priceFormat(p.getValue0().getPriceNetto());
            res[i][2] = p.getValue1().toString();
            res[i][3] = priceFormat(p.getValue0().getPriceNetto() * p.getValue1());
            res[i][4] = Integer.toString(p.getValue0().getVat()) + "%";
            res[i][5] = priceFormat(p.getValue0().getPriceNetto() * p.getValue1() * p.getValue0().getVat() / 100);
            res[i][6] =   priceFormat(p.getValue0().getPriceNetto() * p.getValue1() * (p.getValue0().getVat() + 100) / 100);
            i++;
        }
        return res;
    }

    public String sumToString(){
        int sum = 0;
        for (Pair<Product, Integer> p : productList) {
            sum += (p.getValue0().getPriceNetto()*(p.getValue0().getVat()+100)*p.getValue1())/100;
        }
        return priceFormat(sum);
    }

    @Override
    public String toString(){
        return customer.toString()+" "+dateFormat(date)+" "+sumToString();
    }
}
