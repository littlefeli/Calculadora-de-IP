package br.dev.felipe.ipcalc.gui;

import br.dev.felipe.ipcalc.model.IPCalculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaCalculadoraIP extends JFrame {

    private JTextField campoIP;
    private JTextField campoPrefixo;
    private JTextArea campoResultado;

    public TelaCalculadoraIP() {
        setTitle("Calculadora de IP");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelEntrada = new JPanel(new GridLayout(3, 2, 10, 10));

        painelEntrada.add(new JLabel("Endereço IP:"));
        campoIP = new JTextField();
        painelEntrada.add(campoIP);

        painelEntrada.add(new JLabel("Prefixo (ex: 24):"));
        campoPrefixo = new JTextField();
        painelEntrada.add(campoPrefixo);

        JButton btnCalcular = new JButton("Calcular");
        JButton btnLimpar = new JButton("Limpar");
        painelEntrada.add(btnCalcular);
        painelEntrada.add(btnLimpar);

        add(painelEntrada, BorderLayout.NORTH);

        campoResultado = new JTextArea();
        campoResultado.setEditable(false);
        add(new JScrollPane(campoResultado), BorderLayout.CENTER);

        btnCalcular.addActionListener((ActionEvent e) -> {
            try {
                String ip = campoIP.getText();
                int prefixo = Integer.parseInt(campoPrefixo.getText());
                IPCalculadora calc = new IPCalculadora(ip, prefixo);
                campoResultado.setText(calc.getResumo());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
            }
        });

        btnLimpar.addActionListener((ActionEvent e) -> {
            campoIP.setText("");
            campoPrefixo.setText("");
            campoResultado.setText("");
        });
    }
}