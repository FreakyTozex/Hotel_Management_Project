/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.UserDTO;
import VIEW.TelaInicial;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.Objects;
import javax.swing.JFrame;
/**
 *
 * @author anton
 */
public class UserDAO {
    PreparedStatement pstm;
    ResultSet rs;
    Connection conn;

    public boolean Login(UserDTO userDTO, JFrame loginFrame) {
        conn = new ConexaoDAO().conectaBD();
        
        if ((Objects.isNull(userDTO.getUser()) || userDTO.getUser().isBlank())) {
            JOptionPane.showMessageDialog(null, "Username está vazio, por favor insira o seu Username", "Erro", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if ((Objects.isNull(userDTO.getPassword()) || userDTO.getPassword().isBlank())) {
            JOptionPane.showMessageDialog(null, "Password está vazia, por favor insira a sua Password", "Erro", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            String sql = "SELECT * FROM users WHERE Username = ? AND Password = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, userDTO.getUser());
            pstm.setString(2, userDTO.getPassword());  

            rs = pstm.executeQuery(); 

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Bem-vindo, " + userDTO.getUser() + "!");

                TelaInicial telaInicial = new TelaInicial();
                telaInicial.setVisible(true);

                if (loginFrame != null) {
                    loginFrame.dispose();
                }
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Username ou Password incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no login: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
