package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.ConnectionFactory;
import java.sql.Connection;

public class MainFrame extends JFrame {
    private Connection connection;

    public MainFrame() {
        connection = ConnectionFactory.getConnection();
        initComponents();
    }

    private void initComponents() {
        setTitle("Main Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton manageTeamsButton = new JButton("Gerenciar Times");
        manageTeamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TimeFrame(connection).setVisible(true);
            }
        });

        JButton managePlayersButton = new JButton("Gerenciar Jogadores");
        managePlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JogadorFrame(connection).setVisible(true);
            }
        });

        JPanel panel = new JPanel();
        panel.add(manageTeamsButton);
        panel.add(managePlayersButton);

        getContentPane().add(panel);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
