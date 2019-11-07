import org.javatuples.Pair;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;

class DataBaseConnection {
    private final AbstractList<Customer> customers;
    private final AbstractList<Product> products;
    private final AbstractList<Facture> factures;
    @SuppressWarnings("FieldCanBeLocal")
    private final String db;
    @SuppressWarnings("FieldCanBeLocal")
    private final String user;
    @SuppressWarnings("FieldCanBeLocal")
    private final String password;

    @SuppressWarnings("deprecation")
    public DataBaseConnection(String db, String user, String password){
        this.db = db;
        this.user = user;
        this.password = password;
        customers = new ArrayList<>();
        products = new ArrayList<>();
        factures = new ArrayList<>();
        customers.add(new Customer("Jan Kowalski", "Wyspy Bergabuta"));
        customers.add(new Customer("Stefan Ziemba", "Nowy Jork 32"));
        products.add(new Product("Opona", 40000, 23));
        products.add(new Product("Felga", 90000, 8));
        ArrayList<Pair<Product, Integer>> temp = new ArrayList<>();
        temp.add(new Pair<>(products.get(0), 4) );
        factures.add(new Facture(customers.get(0), temp , new Date(118,11,1)));
    }

    public AbstractList<Customer> getCustomers() {
        return customers;
    }

    public AbstractList<Product> getProducts() {
        return products;
    }

    public AbstractList<Facture> getFactures() {
        return factures;
    }

    public void addProduct(Product p){
        products.add(p);
    }

    public void addCustomer(Customer c){
        customers.add(c);
    }

    public void addFacture(Facture f){
        factures.add(f);
    }
}
