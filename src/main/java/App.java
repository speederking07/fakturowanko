import javax.swing.*;
import java.awt.*;

class App extends JFrame {
    private static final String db = "";
    private static final String user = "";
    private static final String password = "";
    private final DataBaseConnection dbConn;
    private final JPanel contentPanel;

    private App() {
        dbConn = new DataBaseConnection(db, user, password);
        setTitle("Program do fakturowania");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 500));
        contentPanel = new JPanel();
        add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2));
        JButton refreshButton = new JButton("Odświerz");
        refreshButton.addActionListener((e) -> refresh());
        controlPanel.add(refreshButton);
        JButton newFactureButton = new JButton("Dodaj fakturę");
        newFactureButton.addActionListener((e) -> new NewFacture(dbConn));
        controlPanel.add(newFactureButton);
        add(controlPanel, BorderLayout.PAGE_END);
        refresh();
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }

    private void refresh() {
        contentPanel.removeAll();
        for (Facture f : dbConn.getFactures()) {
            JPanel panel = new JPanel();
            panel.setMaximumSize(new Dimension(10000, 30));
            panel.setLayout(new BorderLayout());
            panel.add(new JLabel(f.toString()), BorderLayout.CENTER);
            JButton button = new JButton("Podgląd");
            button.addActionListener((e) -> new FacturePreview(f));
            panel.add(button, BorderLayout.LINE_END);
            contentPanel.add(panel);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
