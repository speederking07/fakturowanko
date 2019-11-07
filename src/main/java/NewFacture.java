import org.javatuples.Pair;
import org.javatuples.Triplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

class NewFacture extends JFrame {
    private final DataBaseConnection dbConn;
    private final JComboBox<Customer> customerJComboBox;
    private final JPanel productsPanelInScrollPane;
    private final JComboBox<Product> newProductJComboBox;
    private final AbstractList<Triplet<Product, AtomicInteger, JPanel>> listOfProducts;

    NewFacture(DataBaseConnection db){
        super();
        dbConn = db;
        setTitle("Nowa faktura");
        setLayout(new BorderLayout());
        listOfProducts = new ArrayList<>();
        add(new Label("Nowa faktura"), BorderLayout.PAGE_START);
        customerJComboBox = new JComboBox<>();
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BorderLayout());
        customerPanel.add(customerJComboBox, BorderLayout.CENTER);
        JButton newCustomerButton = new JButton("Nowy klient");
        newCustomerButton.addActionListener((ActionEvent e) ->{
            NewCustomer nc = new NewCustomer();
            nc.setVisible(true);
            if(nc.isAdded()){
                dbConn.addCustomer(nc.getCustomer());
                Object old = customerJComboBox.getSelectedItem();
                updateCustomers();
                customerJComboBox.setSelectedItem(old);
            }
        });
        customerPanel.add(newCustomerButton, BorderLayout.EAST);
        contentPanel.add(customerPanel, BorderLayout.PAGE_START);
        add(contentPanel,BorderLayout.CENTER);
        updateCustomers();
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());
        productsPanelInScrollPane = new JPanel();
        productsPanelInScrollPane.setLayout(new BoxLayout(productsPanelInScrollPane, BoxLayout.Y_AXIS));
        productsPanelInScrollPane.setPreferredSize(new Dimension(500, 400));
        JScrollPane scrollPane = new JScrollPane(productsPanelInScrollPane);
        productPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel productManagementPanel = new JPanel();
        productManagementPanel.setLayout(new BorderLayout());
        JButton newProductButton = new JButton("Nowy produkt");
        newProductButton.addActionListener((ActionEvent e) ->{
            NewProduct np = new NewProduct();
            np.setVisible(true);
            if(np.isAdded()){
                dbConn.addProduct(np.getProduct());
                updateProducts();
            }
        });
        productManagementPanel.add(newProductButton, BorderLayout.WEST);
        newProductJComboBox = new JComboBox<>();
        updateProducts();
        productManagementPanel.add(newProductJComboBox, BorderLayout.CENTER);
        JButton addProductButton = new JButton("Dodaj");
        addProductButton.addActionListener((ActionEvent e) ->{
            Product p = (Product)newProductJComboBox.getSelectedItem();
            for (Triplet<Product, AtomicInteger, JPanel> triplet: listOfProducts){
                if (triplet.getValue0().equals(p)){
                    JOptionPane.showMessageDialog(new JFrame(), "Produkt juz na liscie", "Blad",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            Triplet<Product, AtomicInteger, JPanel> newProductTriplet = generateProductPosition(p);
            productsPanelInScrollPane.add(newProductTriplet.getValue2());
            listOfProducts.add(newProductTriplet);
            productsPanelInScrollPane.revalidate();
            productsPanelInScrollPane.repaint();
        });
        productManagementPanel.add(addProductButton, BorderLayout.EAST);
        productPanel.add(productManagementPanel, BorderLayout.PAGE_END);
        contentPanel.add(productPanel, BorderLayout.CENTER);
        JButton createFacture = new JButton("Nowa foktura");
        createFacture.addActionListener((ActionEvent e) ->{
            ArrayList<Pair<Product, Integer>> resProducts = new ArrayList<>();
            for (Triplet<Product, AtomicInteger, JPanel> triplet: listOfProducts){
                if (triplet.getValue1().intValue() == 0){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Produkt "+triplet.getValue0()+" na liscie ma ilosc sztuk zero", "Blad",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                resProducts.add(new Pair<>(triplet.getValue0(), triplet.getValue1().intValue()));
            }
            if(resProducts.isEmpty()){
                JOptionPane.showMessageDialog(new JFrame(),
                        "Brak produktow", "Blad",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dbConn.addFacture(new Facture((Customer) customerJComboBox.getSelectedItem(), resProducts, new Date()));
            dispose();
        });
        contentPanel.add(createFacture, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
    }

    private void updateCustomers() {
        customerJComboBox.removeAllItems();
        for (Customer c : dbConn.getCustomers()) {
            customerJComboBox.addItem(c);
        }
    }

    /**
     * Generating Triplet for specific product
     */
    private Triplet<Product, AtomicInteger, JPanel> generateProductPosition(Product product){
        AtomicInteger quantityInt = new AtomicInteger(1);
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(10000, 24));
        Triplet<Product, AtomicInteger, JPanel> res = new Triplet<>(product, quantityInt, panel);
        panel.setLayout(new GridLayout(1,3));
        panel.add(new JLabel(product.toString()));
        JTextPane quantity = new JTextPane();
        quantity.setText("1");
        quantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (quantity.getText().trim().isEmpty() || quantity.getText().trim().equals("0") ){
                    quantityInt.set(0);
                }
                else {
                    quantityInt.set(Integer.parseInt(quantity.getText()));
                }
            }
        });
        panel.add(quantity);
        JButton deleteButton = new JButton("Usuń");
        deleteButton.addActionListener((ActionEvent e) -> {
            productsPanelInScrollPane.remove(panel);
            listOfProducts.remove(res);
            productsPanelInScrollPane.revalidate();
            productsPanelInScrollPane.repaint();
        });
        panel.add(deleteButton);
        return res;
    }

    private void updateProducts() {
        newProductJComboBox.removeAllItems();
        for (Product p : dbConn.getProducts()) {
            newProductJComboBox.addItem(p);
        }
    }
}
