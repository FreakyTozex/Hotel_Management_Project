/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ClientesDTO;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AEEstarreja
 */
public class ClienteDAO {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    
    ArrayList<ClientesDTO> lista = new ArrayList<>();
    
    
    public ArrayList<ClientesDTO> ListarCliente(){
                String sql = "select * from cliente";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()){
                ClientesDTO clienteDTO = new ClientesDTO();
                clienteDTO.setNIF(rs.getInt("NIF"));
                clienteDTO.setNome(rs.getString("nome"));
                clienteDTO.setEmail(rs.getString("email"));
                clienteDTO.setTelefone(rs.getString("telefone"));
                clienteDTO.setAtivo(rs.getString("Ativo"));
                lista.add(clienteDTO);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ClienteDAO Pesquisar: " + erro);
        }
        return lista;
    }

    
        public ArrayList<ClientesDTO> ListarClientesAtivos(){
                String sql = "select * from cliente WHERE Ativo = 'Sim'";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()){
                ClientesDTO clienteDTO = new ClientesDTO();
                clienteDTO.setNIF(rs.getInt("NIF"));
                clienteDTO.setNome(rs.getString("nome"));
                clienteDTO.setEmail(rs.getString("email"));
                clienteDTO.setTelefone(rs.getString("telefone"));
                clienteDTO.setAtivo(rs.getString("Ativo"));
                lista.add(clienteDTO);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ClienteDAO Pesquisar: " + erro);
        }
        return lista;
    }
        
        public ArrayList<ClientesDTO> ListarClientesNAtivos(){
                String sql = "select * from cliente WHERE Ativo = 'Não'";
        
        conn = new ConexaoDAO().conectaBD();
        
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while (rs.next()){
                ClientesDTO clienteDTO = new ClientesDTO();
                clienteDTO.setNIF(rs.getInt("NIF"));
                clienteDTO.setNome(rs.getString("nome"));
                clienteDTO.setEmail(rs.getString("email"));
                clienteDTO.setTelefone(rs.getString("telefone"));
                clienteDTO.setAtivo(rs.getString("Ativo"));
                lista.add(clienteDTO);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ClienteDAO Pesquisar: " + erro);
        }
        return lista;
    }
    
    public void inserirCliente(ClientesDTO clientedto){
        conn = new ConexaoDAO().conectaBD();
        
        try{
            String sql = "INSERT INTO cliente (NIF, nome, email, telefone, Ativo) VALUES (?,?,?,?,?)";
       
            pstm = conn.prepareStatement(sql);
            
            pstm.setInt(1, clientedto.getNIF());
            pstm.setString(2, clientedto.getNome());
            pstm.setString(3, clientedto.getEmail());
            pstm.setString(4, clientedto.getTelefone());
            pstm.setString(5, clientedto.getAtivo());
            
            pstm.execute();            
            pstm.close();
            
            JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
            
        } catch(SQLException erro){
            JOptionPane.showMessageDialog(null, "Inserir cliente: " + erro);
        }
    }
    
     public void alterarClientes(ClientesDTO clientedto){
        conn = new ConexaoDAO().conectaBD();
        
        try{
            String sql = "UPDATE cliente SET nome= ?, email= ?, telefone = ?, Ativo= ? WHERE NIF = ?";
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1 , clientedto.getNome());
            pstm.setString(2,clientedto.getEmail());
            pstm.setString(3 , clientedto.getTelefone());
            pstm.setString(4, clientedto.getAtivo());
            pstm.setInt(5, clientedto.getNIF());
            
            pstm.execute();
            pstm.close();
            
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
        } catch(SQLException erro){
             JOptionPane.showMessageDialog(null, "Alterar cliente: " + erro);
        }
    }
     
     
      public ArrayList<ClientesDTO> pesquisarClientesAtivos(String pesquisa) {
        ArrayList<ClientesDTO> lista = new ArrayList<>();
        String sql = "SELECT NIF, nome, email, telefone, ativo FROM cliente WHERE nome LIKE ? AND Ativo = 'Sim'";

        try {
            conn = new ConexaoDAO().conectaBD();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, "%" + pesquisa +"%" ); // Pesquisa parcial
            rs = pstm.executeQuery();

            while (rs.next()) {
                ClientesDTO cliente = new ClientesDTO();
                cliente.setNIF(rs.getInt("NIF"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setAtivo(rs.getString("ativo"));
                lista.add(cliente);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + erro.getMessage());
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
      
       public ArrayList<ClientesDTO> pesquisarClientes(String pesquisa) {
        ArrayList<ClientesDTO> lista = new ArrayList<>();
        String sql = "SELECT NIF, nome, email, telefone, ativo FROM cliente WHERE nome LIKE ?";

        try {
            conn = new ConexaoDAO().conectaBD();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, "%" + pesquisa + "%"); // Pesquisa parcial
            rs = pstm.executeQuery();

            while (rs.next()) {
                ClientesDTO cliente = new ClientesDTO();
                cliente.setNIF(rs.getInt("NIF"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setAtivo(rs.getString("ativo"));
                lista.add(cliente);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + erro.getMessage());
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
     
}
