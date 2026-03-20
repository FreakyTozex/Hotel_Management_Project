/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.QuartoDTO;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author anton
 */
public class QuartoDAO {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    
    ArrayList<QuartoDTO> lista = new ArrayList<>();
    
    
    public ArrayList<QuartoDTO> ListarQuartos(){
        String sql = "SELECT numero, tipo_quarto, telefone, reservado, preco FROM quartos INNER JOIN tipos_quartos ON quartos.tipo=tipos_quartos.id";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()){
                QuartoDTO quartoDTO = new QuartoDTO();
                quartoDTO.setNumero(rs.getInt("numero"));
                quartoDTO.setTipo_quarto(rs.getString("tipo_quarto"));
                quartoDTO.setTelefone(rs.getString("telefone"));
                quartoDTO.setReservado(rs.getString("reservado"));
                quartoDTO.setPreco(rs.getDouble("preco"));
                lista.add(quartoDTO);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "QuartoDAO Pesquisar: " + erro);
        }
        return lista;
    }
    
    public ArrayList<QuartoDTO> ListarQuartosFiltro(String filtro) {
        ArrayList<QuartoDTO> lista = new ArrayList<>();
        
        String sql = "SELECT numero, tipo_quarto, telefone, reservado, preco FROM quartos INNER JOIN tipos_quartos ON quartos.tipo=tipos_quartos.id";

        // Adicionando filtro
        if ("precoBaixo".equals(filtro)) {
            sql += " ORDER BY preco ASC";
        } else if ("precoAlto".equals(filtro)) {
            sql += " ORDER BY preco DESC";
        } else if ("naoReservados".equals(filtro)) {
            sql += " WHERE reservado = 'Não'";
        }

        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                QuartoDTO quartoDTO = new QuartoDTO();
                quartoDTO.setNumero(rs.getInt("numero"));
                quartoDTO.setTipo_quarto(rs.getString("tipo_quarto"));
                quartoDTO.setTelefone(rs.getString("telefone"));
                quartoDTO.setReservado(rs.getString("reservado"));
                quartoDTO.setPreco(rs.getDouble("preco"));
                lista.add(quartoDTO);
            }
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar quartos: " + erro.getMessage());
        }

        return lista;
    }
    
    public ArrayList<QuartoDTO> pesquisarQuarto(int pesquisa) {
        ArrayList<QuartoDTO> lista = new ArrayList<>();
        String sql = "SELECT numero, tipo_quarto, telefone, reservado, preco FROM quartos INNER JOIN tipos_quartos ON quartos.tipo=tipos_quartos.id WHERE numero = ?";

        try {
            conn = new ConexaoDAO().conectaBD();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, pesquisa); // Pesquisa parcial
            rs = pstm.executeQuery();

            while (rs.next()) {
                QuartoDTO quarto = new QuartoDTO();
                quarto.setNumero(rs.getInt("numero"));
                quarto.setTipo_quarto(rs.getString("tipo_quarto"));
                quarto.setTelefone(rs.getString("telefone"));
                quarto.setReservado(rs.getString("reservado"));               
                quarto.setPreco(rs.getDouble("preco"));
                lista.add(quarto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar quarto: " + erro.getMessage());
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return lista;
    }
    
    
   public void alterarQuartos(QuartoDTO quartoDTO) {
        conn = new ConexaoDAO().conectaBD();

        try {
            // 1. Buscar o ID do tipo de quarto pelo nome
            String sqlTipo = "SELECT ID FROM tipos_quartos WHERE tipo_quarto = ?";
            pstm = conn.prepareStatement(sqlTipo);
            pstm.setString(1, quartoDTO.getTipo_quarto()); // aqui passas o nome
            rs = pstm.executeQuery();

            int idTipo = -1;
            if (rs.next()) {
                idTipo = rs.getInt("ID");
            } else {
                JOptionPane.showMessageDialog(null, "Tipo de quarto não encontrado!");
                return; // se não existir, sai
            }

            rs.close();
            pstm.close();

            // 2. Atualizar o quarto com o ID obtido
            String sql = "UPDATE quartos SET tipo= ?, telefone = ?WHERE numero = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idTipo); // agora sim, passas o ID obtido
            pstm.setString(2, quartoDTO.getTelefone());
            pstm.setInt(3, quartoDTO.getNumero());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "Quarto alterado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Alterar quarto: " + erro);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar conexão: " + ex.getMessage());
            }
        }
    }
}
