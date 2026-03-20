/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ClientesDTO;
import DTO.QuartoDTO;
import DTO.ReservaDTO;
import MailSender.EmailSender;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author anton
 */
public class ReservaDAO {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    
    ArrayList<ReservaDTO> lista = new ArrayList<>();
    
    
    public ArrayList<ReservaDTO> ListarReservas(){
        String sql = "SELECT ID, id_cliente, nome, numero, check_in, check_out FROM reservas INNER JOIN cliente ON reservas.id_cliente = cliente.NIF INNER JOIN quartos ON reservas.nr_quarto = quartos.numero ORDER BY ID DESC;";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()){
                ReservaDTO reservaDTO = new ReservaDTO();
                reservaDTO.setId(rs.getInt("ID"));
                reservaDTO.setId_cliente(rs.getInt("id_cliente"));
                reservaDTO.setNome(rs.getString("nome"));
                reservaDTO.setNumero_quarto(rs.getInt("numero"));
                reservaDTO.setDataIN(rs.getString("check_in"));
                reservaDTO.setDataOUT(rs.getString("check_out"));
                lista.add(reservaDTO);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ReservaDAO Pesquisar: " + erro);
        }
        return lista;
    }
    
    public void inserirReserva(ReservaDTO reservadto) {
        conn = new ConexaoDAO().conectaBD();
        QuartoDTO quarto = new QuartoDTO();

        try {
            
            String sqlCheck = "SELECT reservado FROM quartos WHERE numero = ?";
            pstm = conn.prepareStatement(sqlCheck);
            pstm.setInt(1, reservadto.getNumero_quarto());
            rs = pstm.executeQuery();

            if (rs.next() && rs.getString("reservado").equalsIgnoreCase("Sim")) {
                JOptionPane.showMessageDialog(null, "Erro: O quarto já está reservado!", "Quarto Ocupado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            rs.close();
            pstm.close();
            
            
            String sql = "INSERT INTO reservas (id_cliente, nr_quarto, check_in, check_out) VALUES (?, ?, ?, ?)";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, reservadto.getId_cliente());
            pstm.setInt(2, reservadto.getNumero_quarto());
            pstm.setString(3, reservadto.getDataIN());
            pstm.setString(4, reservadto.getDataOUT());
            pstm.execute();
            pstm.close(); 

            ClientesDTO cliente = new ClientesDTO();
            String sqlEmailCliente = "SELECT email, telefone, nome FROM cliente WHERE NIF = ?";
            pstm = conn.prepareStatement(sqlEmailCliente);
            pstm.setInt(1, reservadto.getId_cliente());
            rs = pstm.executeQuery();

            if (rs.next()) {
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setNome(rs.getString("nome"));
            }
            rs.close();
            pstm.close();

            String sqlNrQuarto = "SELECT preco FROM quartos INNER JOIN tipos_quartos ON quartos.tipo = tipos_quartos.id WHERE numero = ?";
            pstm = conn.prepareStatement(sqlNrQuarto);
            pstm.setInt(1, reservadto.getNumero_quarto());
            rs = pstm.executeQuery();

            if (rs.next()) {
                quarto.setPreco(rs.getDouble("preco"));
            }
            rs.close();
            pstm.close();


            String sqlUpdateQuarto = "UPDATE quartos SET reservado = 'Sim' WHERE numero = ?";
            pstm = conn.prepareStatement(sqlUpdateQuarto);
            pstm.setInt(1, reservadto.getNumero_quarto());
            pstm.executeUpdate();
            pstm.close();
            
            DecimalFormat preco = new DecimalFormat("#0.00");
            String precoformatado = preco.format(quarto.getPreco());

            String assunto = "Confirmação de Reserva";
            String mensagem = "Olá, a sua reserva foi confirmada com sucesso!\n"
                              + "Nome: " + cliente.getNome() + "\n"
                              + "NIF: " + reservadto.getId_cliente() + "\n"
                              + "Telefone: " + cliente.getTelefone() + "\n"
                              + "Número do Quarto: " + reservadto.getNumero_quarto() + "\n"
                              + "Check-in: " + reservadto.getDataIN() + "\n"
                              + "Check-out: " + reservadto.getDataOUT() + "\n\n"
                              + "Preço p/noite: " + precoformatado + "€\n\n"
                              + "Obrigado por escolher nosso serviço!";

            EmailSender.enviarEmail(cliente.getEmail(), assunto, mensagem);

            JOptionPane.showMessageDialog(null, "Reserva inserida com sucesso!");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir reserva: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    
    public void eliminarReserva(ReservaDTO reservadto) {
        conn = new ConexaoDAO().conectaBD();

        try {
            //Eliminar a reserva
            String sqlDeleteReserva = "DELETE FROM reservas WHERE id = ?";
            pstm = conn.prepareStatement(sqlDeleteReserva);
            pstm.setInt(1, reservadto.getId());
            int linhasAfetadas = pstm.executeUpdate();
            pstm.close();

            if (linhasAfetadas > 0) {
                // Atualizar o estado do quarto para 'Não reservado'
                String sqlUpdateQuarto = "UPDATE quartos SET reservado = 'Não' WHERE numero = ?";
                pstm = conn.prepareStatement(sqlUpdateQuarto);
                pstm.setInt(1, reservadto.getNumero_quarto());
                int updateQuarto = pstm.executeUpdate();
                pstm.close();

                if (updateQuarto > 0) {
                    JOptionPane.showMessageDialog(null, "Reserva eliminada e quarto atualizado!");
                } else {
                    JOptionPane.showMessageDialog(null, "Reserva eliminada, mas o quarto NÃO foi atualizado!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }

                // Verificar se o cliente ainda tem reservas
                String sqlVerificaReservas = "SELECT COUNT(*) AS total FROM reservas WHERE id_cliente = ?";
                pstm = conn.prepareStatement(sqlVerificaReservas);
                pstm.setInt(1, reservadto.getId_cliente());
                rs = pstm.executeQuery();

                boolean clienteSemReservas = false;
                if (rs.next()) {
                    clienteSemReservas = rs.getInt("total") == 0;
                }
                rs.close();
                pstm.close();

                // 4. Se o cliente não tiver mais reservas, definir como inativo
                if (clienteSemReservas) {
                    String sqlUpdateCliente = "UPDATE cliente SET ativo = 'Não' WHERE NIF = ?";
                    pstm = conn.prepareStatement(sqlUpdateCliente);
                    pstm.setInt(1, reservadto.getId_cliente());

                    System.out.println("Atualizar cliente com NIF: " + reservadto.getId_cliente());

                    int updateCliente = pstm.executeUpdate();
                    pstm.close();

                    if (updateCliente > 0) {
                        JOptionPane.showMessageDialog(null, "Cliente atualizado para inativo.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar o cliente. Verifique se o NIF existe.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma reserva encontrada para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro ao eliminar reserva: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (pstm != null) pstm.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
    }



}
