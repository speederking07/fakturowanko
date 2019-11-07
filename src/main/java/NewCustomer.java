import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NewCustomer extends JDialog {
    private final JTextPane name;
    private final JTextPane address;
    private boolean added;

    public NewCustomer() {
        super();
        setModal(true);
        setName("Nowy klient");
        this.setLayout(new GridLayout(3, 1));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        namePanel.add(new JLabel("Nazwa: "));
        name = new JTextPane();
        namePanel.add(name);
        this.add(namePanel);
        JPanel addressPanel = new JPanel();
        addressPanel.add(new JLabel("Adress: "));
        address = new JTextPane();
        addressPanel.add(address);
        this.add(addressPanel);
        JButton button = new JButton("Dodaj");
        button.addActionListener(e -> {
            if(name.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(new JFrame(), "Brak nazwy", "Blad",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(address.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(new JFrame(), "Brak adressu", "Blad",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
            added = true;
        });
        added = false;
        add(button);
        pack();
    }

    public boolean isAdded(){
        return added;
    }

    public String getName() {
        return name.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public Customer getCustomer(){
        if (!added){
            return null;
        }
        return new Customer(name.getText(), address.getText());
    }
}
