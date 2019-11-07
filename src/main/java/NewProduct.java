import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NewProduct extends JDialog {
    private final JTextPane price;
    private final JTextPane name;
    private final JTextPane vat;
    private boolean added;

    public NewProduct() {
        super();
        setModal(true);
        setName("Nowy produkt");
        this.setLayout(new GridLayout(4, 1));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(new JLabel("Nazwa: "));
        name = new JTextPane();
        namePanel.add(name);
        this.add(namePanel);
        JPanel pricePanel = new JPanel();
        pricePanel.add(new JLabel("Cena netto: "));
        price = new JTextPane();
        pricePanel.add(price);
        pricePanel.add(new JLabel(" PLN"));
        this.add(pricePanel);
        JPanel vatPanel = new JPanel();
        vatPanel.add(new JLabel("Vat: "));
        vat = new JTextPane();
        vatPanel.add(vat);
        vatPanel.add(new JLabel(" %"));
        this.add(vatPanel);
        JButton button = new JButton("Dodaj");
        button.addActionListener(e -> {
                if(name.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(), "Brak nazwy", "Blad",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Float.parseFloat(price.getText());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), "Zla cena", "Blad",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Integer.parseInt(vat.getText());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(new JFrame(), "Zly vat", "Blad",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            added = true;
            dispose();
        });
        add(button);
        pack();
        added = false;
    }

    public boolean isAdded(){
        return added;
    }

    public Product getProduct(){
        if(!added){
            return null;
        }
        int p = (int)(Float.parseFloat(price.getText())*100);
        int v = Integer.parseInt(vat.getText());
        return new Product(name.getText(), p, v);
    }
}
