package view;

import dao.ConnectionFactory;
import dao.TimeDAO;
import model.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class TimeDialog extends JDialog {
    private JTextField nomeField;
    private Time time;
    private Connection connection;

    public TimeDialog(Connection connection, Time time) {
        this.connection = connection;
        this.time = time;
        initComponents();
    }

    private void initComponents() {
        setTitle(time == null ? "Adicionar Time" : "Editar Time");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));
        setModal(true);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        if (time != null) {
            nomeField.setText(time.getNome());
        }

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nome não pode ser vazio.");
                    return;
                }

                try {
                    TimeDAO timeDAO = new TimeDAO(connection);
                    if (time == null) {
                        timeDAO.addTime(new Time(0, nome));
                    } else {
                        time.setNome(nome);
                        timeDAO.updateTime(time);
                    }
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(nomeLabel);
        add(nomeField);
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(null);
    }
}
