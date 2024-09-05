package view;

import dao.ConnectionFactory;
import dao.JogadorDAO;
import model.Jogador;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JogadorFrame extends JFrame {
    private JTextField nomeTextField, idadeTextField, posicaoTextField, alturaTextField;
    private JComboBox<String> timeComboBox;
    private JTable jogadoresTable;
    private Connection connection;
    private JogadorDAO jogadorDAO;

    public JogadorFrame(Connection connection) {
        this.connection = connection;
        this.jogadorDAO = new JogadorDAO(connection);
        initComponents();
        refreshTable();
        loadTimes();
    }

    private void initComponents() {
        setTitle("Gerenciar Jogadores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialização dos componentes
        nomeTextField = new JTextField(20);
        idadeTextField = new JTextField(20);
        posicaoTextField = new JTextField(20);
        alturaTextField = new JTextField(20);
        timeComboBox = new JComboBox<>();
        JButton addButton = new JButton("Adicionar Jogador");
        JButton editButton = new JButton("Editar Jogador");
        JButton deleteButton = new JButton("Excluir Jogador");
        jogadoresTable = new JTable();

        // Layout
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeTextField);
        panel.add(new JLabel("Idade:"));
        panel.add(idadeTextField);
        panel.add(new JLabel("Posição:"));
        panel.add(posicaoTextField);
        panel.add(new JLabel("Altura:"));
        panel.add(alturaTextField);
        panel.add(new JLabel("Time:"));
        panel.add(timeComboBox);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(jogadoresTable), BorderLayout.CENTER);

        // Adiciona os ActionListeners aos botões
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addJogador();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editJogador();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteJogador();
            }
        });
    }

    private void loadTimes() {
        // Ainda não criei o código para carregar os times do banco de dados e adicionar ao timeComboBox, to errando ainda...
        
    }

    private int getSelectedTimeId() {
        // Ainda não criei um método para retornar o ID do time selecionado, pode ser erro no banco
        
        return 0;
        
        
    }

    private void addJogador() {
        String nome = nomeTextField.getText();
        int idade = Integer.parseInt(idadeTextField.getText());
        String posicao = posicaoTextField.getText();
        double altura = Double.parseDouble(alturaTextField.getText());
        int timeId = getSelectedTimeId();

        if (nome.isEmpty() || posicao.isEmpty() || timeId == -1) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        Jogador jogador = new Jogador(0, nome, idade, posicao, altura, timeId);

        try {
            jogadorDAO.addJogador(jogador);
            refreshTable();
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar jogador: " + ex.getMessage());
        }
    }

    private void editJogador() {
        int selectedRow = jogadoresTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para editar.");
            return;
        }

        int id = (int) jogadoresTable.getValueAt(selectedRow, 0);
        String nome = JOptionPane.showInputDialog(this, "Novo nome para o jogador:");
        int idade = Integer.parseInt(JOptionPane.showInputDialog(this, "Nova idade:"));
        String posicao = JOptionPane.showInputDialog(this, "Nova posição:");
        double altura = Double.parseDouble(JOptionPane.showInputDialog(this, "Nova altura:"));
        int timeId = getSelectedTimeId();

        if (nome == null || nome.isEmpty() || timeId == -1) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        Jogador jogador = new Jogador(id, nome, idade, posicao, altura, timeId);

        try {
            jogadorDAO.updateJogador(jogador);
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao editar jogador: " + ex.getMessage());
        }
    }

    private void deleteJogador() {
        int selectedRow = jogadoresTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para excluir.");
            return;
        }

        int id = (int) jogadoresTable.getValueAt(selectedRow, 0);

        try {
            jogadorDAO.deleteJogador(id);
            refreshTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao excluir jogador: " + ex.getMessage());
        }
    }

    private void refreshTable() {
        List<Jogador> jogadores = jogadorDAO.listJogadores();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nome", "Idade", "Posição", "Altura", "Time"}, 0);

        for (Jogador jogador : jogadores) {
            model.addRow(new Object[]{jogador.getId(), jogador.getNome(), jogador.getIdade(), jogador.getPosicao(), jogador.getAltura(), jogador.getTimeId()});
        }

        jogadoresTable.setModel(model);
    }

    private void clearFields() {
        nomeTextField.setText("");
        idadeTextField.setText("");
        posicaoTextField.setText("");
        alturaTextField.setText("");
        timeComboBox.setSelectedIndex(-1);
    }
}
