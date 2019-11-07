import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void toStringTest() {
        assertEquals("AABB (40.00 PLN)", new Product("AABB", 4000, 10).toString());
        assertEquals("Atta aa (40.00 PLN)", new Product("Atta aa", 4000, 10).toString());
        assertEquals("Opona jeden (500.01 PLN)", new Product("Opona jeden", 50001, 10).toString());
    }
}