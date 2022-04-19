package k1.simulaciones.simulacionestp3.InterfazGrafica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGUI extends JFrame {
    private JPanel mainPanel;
    private JComboBox randomComboBox;
    private JButton btnGenerar;
    private JComboBox distribucionComboBox;

    public mainGUI() {
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(btnGenerar, randomComboBox.getItemCount()+ " ,Seleccionaste este generador: ");
            }
        });

    }

    public static void main(String[] args) {
        mainGUI m = new mainGUI();
        m.setContentPane(m.mainPanel);
        m.setTitle("TRABAJO PRACTICO 3");
        m.setSize(300,400);
        m.setVisible(true);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
