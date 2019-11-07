import org.javatuples.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class FactureTest {
    @org.junit.Test
    public void productsToTable() {
        ArrayList<Pair<Product, Integer>> temp = new ArrayList<>();
        temp.add(new Pair<>(new Product("A", 300, 10), 1));
        Facture f = new Facture(new Customer("A", "B"), temp , new Date(118,11,1));
        String[][] array = f.productsToTable();
        assertEquals("A", array[0][0]);
        assertEquals("3.00 PLN", array[0][1]);
        assertEquals("1", array[0][2]);
        assertEquals("3.00 PLN", array[0][3]);
        assertEquals("10%", array[0][4]);
        assertEquals("0.30 PLN", array[0][5]);
        assertEquals("3.30 PLN", array[0][6]);
    }

    @org.junit.Test
    public void priceFormat() {
        assertEquals("14.41 PLN", Facture.priceFormat(1441));
        assertEquals("3121.00 PLN", Facture.priceFormat(312100));
        assertEquals("1.00 PLN", Facture.priceFormat(100));
        assertEquals("71.99 PLN", Facture.priceFormat(7199));
    }

    @Test
    public void dateFormat() {
        assertEquals("2000-01-03", Facture.dateFormat(new Date(100, 0, 3)));
        assertEquals("2012-04-12", Facture.dateFormat(new Date(112, 3, 12)));
        assertEquals("2000-12-09", Facture.dateFormat(new Date(100, 11, 9)));
    }
}