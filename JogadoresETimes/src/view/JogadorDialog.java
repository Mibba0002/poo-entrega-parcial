package view;

import dao.ConnectionFactory;
import dao.JogadorDAO;
import dao.TimeDAO;
import model.Jogador;
import model.Time;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class JogadorDialog extends JDialog {
    private JTextField nomeField;
    private JTextField idadeField;
    private JTextField posicaoField;
    private JTextField alturaField;
    private JComboBox<String> timeComboBox; // ComboBox para selecionar o time, ta todo bugado ainda
    private JButton saveButton;
    private Connection connection;
    private Jogador jogador;


    public JogadorDialog(Frame owner, Connection connection, Jogador jogador) {
        super(owner, "Gerenciar Jogador", true);
        this.connection = connection;
        this.jogador = jogador;

        // Configurações de layout
        setLayout(new GridLayout(6, 2));

        // Campos de entrada do jogador
        add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        add(nomeField);

        add(new JLabel("Idade:"));
        idadeField = new JTextField(20);
        add(idadeField);

        add(new JLabel("Posição:"));
        posicaoField = new JTextField(20);
        add(posicaoField);

        add(new JLabel("Altura:"));
        alturaField = new JTextField(20);
        add(alturaField);

        // ComboBox para times, ainda bugado
        add(new JLabel("Time:"));
        timeComboBox = new JComboBox<>();
        add(timeComboBox);

        // Botão para salvar
        saveButton = new JButton("Salvar");
        add(saveButton);

        // Carrega os times para o ComboBox ao inicializar o diálogo, tenho que criar ainda rs
        loadTimes();

        // Preenche os campos se o jogador estiver em edição
        if (jogador != null) {
            nomeField.setText(jogador.getNome());
            idadeField.setText(String.valueOf(jogador.getIdade()));
            posicaoField.setText(jogador.getPosicao());
            alturaField.setText(String.valueOf(jogador.getAltura()));
            // Seleciona o time correspondente teoricamente 
            timeComboBox.setSelectedItem(getTimeName(jogador.getTimeId()));
        }

        // Ação do botão Salvar
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveJogador();
            }
        });

        // Configurações da janela
        pack();
        setLocationRelativeTo(owner);
    }

    // Método para carregar os times da base de dados para o ComboBox
    private void loadTimes() {
        try {
            TimeDAO timeDAO = new TimeDAO(connection);
            List<Time> times = timeDAO.listTimes();
            for (Time time : times) {
                timeComboBox.addItem(time.getNome());
            }
        } catch (SQLException e) { //Ainda não sei que erro é esse
            JOptionPane.showMessageDialog(this, "Erro ao carregar times: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obter o nome do time a partir do ID
    private String getTimeName(int timeId) {
        try {
            TimeDAO timeDAO = new TimeDAO(connection);
            Time time = timeDAO.getTimeById(timeId);
            if (time != null) {
                return time.getNome();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para salvar o jogador no banco de dados
    private void saveJogador() {
        // Ainda está em processo de criação
    }
}

