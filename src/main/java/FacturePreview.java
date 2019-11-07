import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class FacturePreview extends JFrame {
    public FacturePreview(Facture facture) {
        super();
        setTitle("Podgląd faktury - "+facture.toString());
        setLayout(new BorderLayout());
        JPanel ad = new JPanel();
        ad.setLayout(new GridLayout(5, 1));
        ad.setPreferredSize( new Dimension( 200, 100 ) );
        ad.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel date = new JLabel(Facture.dateFormat(facture.getDate()), SwingConstants.RIGHT);
        JLabel customerName = new JLabel(facture.getCustomer().getName(), SwingConstants.LEFT);
        JLabel customerAddress = new JLabel(facture.getCustomer().getAddress(), SwingConstants.LEFT);
        JLabel companyName = new JLabel("Firma Marek&Partners", SwingConstants.RIGHT);
        JLabel companyAddress = new JLabel("Jakis addres", SwingConstants.RIGHT);
        ad.add(date);
        ad.add(customerName);
        ad.add(customerAddress);
        ad.add(companyName);
        ad.add(companyAddress);
        add(ad, BorderLayout.PAGE_START);
        String[] columnsName = {"Nazwa", "Cena jednostkowa", "Ilosc", "Netto", "VAT", "Podatek", "Brutto"};
        JTable products = new JTable(facture.productsToTable(), columnsName);
        products.setBounds(30, 40, 200, 300);
        products.setEnabled(false);
        JScrollPane sp = new JScrollPane(products);
        add(sp, BorderLayout.CENTER);
        JLabel sum = new JLabel("Do zapłaty "+facture.sumToString(), SwingConstants.RIGHT);
        add(sum, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
    }
}
