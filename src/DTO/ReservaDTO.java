/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;



/**
 *
 * @author anton
 */
public class ReservaDTO {
    private int id, id_cliente, numero_quarto;
    private String nome, dataIN, dataOUT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero_quarto() {
        return numero_quarto;
    }

    public void setNumero_quarto(int numero_quarto) {
        this.numero_quarto = numero_quarto;
    }

    public String getDataIN() {
        return dataIN;
    }

    public void setDataIN(String dataIN) {
        this.dataIN = dataIN;
    }

    public String getDataOUT() {
        return dataOUT;
    }

    public void setDataOUT(String dataOUT) {
        this.dataOUT = dataOUT;
    }
       
}
