package view;

import dao.ConnectionFactory;
import dao.TimeDAO;
import model.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TimeFrame extends JFrame {
    private JTable timesTable;
    private Connection connection;

        public TimeFrame(Connection connection) {
        this.connection = connection;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gerenciar Times");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Criando a tabela
        String[] columnNames = {"ID", "Nome"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        timesTable = new JTable(model);

        // Ainda pendente
        loadTimes();

        JScrollPane scrollPane = new JScrollPane(timesTable);

        // Botões 
        JButton addButton = new JButton("Adicionar Time");
        JButton editButton = new JButton("Editar Time");
        JButton deleteButton = new JButton("Remover Time");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TimeDialog(connection, null).setVisible(true);
                loadTimes();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = timesTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) timesTable.getValueAt(selectedRow, 0);
                    String nome = (String) timesTable.getValueAt(selectedRow, 1);
                    new TimeDialog(connection, new Time(id, nome)).setVisible(true);
                    loadTimes();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um time para editar.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = timesTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = (int) timesTable.getValueAt(selectedRow, 0);
                    try {
                        new TimeDAO(connection).deleteTime(id);
                        loadTimes();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um time para remover.");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setLocationRelativeTo(null);
    }

    private void loadTimes() {
        try {
            TimeDAO timeDAO = new TimeDAO(connection);
            List<Time> times = timeDAO.listTimes();
            DefaultTableModel model = (DefaultTableModel) timesTable.getModel();
            model.setRowCount(0);

            for (Time time : times) {
                model.addRow(new Object[]{time.getId(), time.getNome()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

