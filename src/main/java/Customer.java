public class Customer {
    private final String name;
    private final String address;

    Customer (String n, String a){
        this.name = n;
        this.address = a;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return name;
    }
}
