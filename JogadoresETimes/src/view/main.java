package view;

public class main {
    public static void main(String[] args) {
        // Foi um teste da interface gráfica, porque no início não estava abrindo a janela
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
