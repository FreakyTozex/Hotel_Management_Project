/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VIEW;

import VIEW_QUARTOS.QuartosDisponiveis;
import VIEW_RESERVA.Reservas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Sistema de Reservas de Hotel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel Lateral (Menu)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 10, 10));
        menuPanel.setBackground(new Color(10, 25, 55)); // Azul escuro
        menuPanel.setPreferredSize(new Dimension(220, 400));

        // Botões do Menu
        JButton btnReservas = criarBotaoMenu("Gerir Reservas");
        JButton btnClientes = criarBotaoMenu("Gerir Clientes");
        JButton btnQuartos = criarBotaoMenu("Gerir Quartos");
        JButton btnSair = criarBotaoMenu("Sair");

        btnSair.addActionListener(e -> System.exit(0));
        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCliente telaCliente = new TelaCliente();
                telaCliente.setVisible(true);
                dispose();
            }
        });
        btnQuartos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuartosDisponiveis quartos = new QuartosDisponiveis();
                quartos.setVisible(true);
                dispose();
            }
        });
        btnReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservas reserva = new Reservas();
                reserva.setVisible(true);
                dispose();
            }
        });
        

        menuPanel.add(btnReservas);
        menuPanel.add(btnClientes);
        menuPanel.add(btnQuartos);
        menuPanel.add(btnSair); 

        // Painel Principal (Conteúdo)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(240, 240, 240));

        // Cabeçalho
        JLabel headerLabel = new JLabel("Bem-vindo ao Sistema de Reservas", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(new Color(10, 25, 55));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Carregar a imagem
       ImageIcon imageIcon = new ImageIcon(getClass().getResource("/ImagemHotel/hotel.jpg"));
       Image image = imageIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
       JLabel imageLabel = new JLabel(new ImageIcon(image));

        // Adiciona o cabeçalho e a imagem ao painel de conteúdo
        contentPanel.add(headerLabel, BorderLayout.NORTH);
        contentPanel.add(imageLabel, BorderLayout.CENTER);

        // Adiciona os painéis à janela principal
        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton criarBotaoMenu(String texto) {
     JButton botao = new JButton(texto);
     botao.setFont(new Font("Arial", Font.BOLD, 14));
     botao.setForeground(Color.WHITE);
     botao.setBackground(new Color(30, 60, 150)); // Azul escuro
     botao.setFocusPainted(false);
     botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
     botao.setOpaque(true);

     // Efeito ao passar o mouse
     botao.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            botao.setBackground(new Color(50, 100, 200)); // Efeito neon
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            botao.setBackground(new Color(30, 60, 150)); // Volta ao azul escuro
        }
     });
         return botao;
    }  


     public static void main(String[] args) {
         new TelaInicial();
     }
}
