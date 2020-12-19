package com.example.stmobile;

public class ModeloExpedicao {
    private int cod_pedido;
    private String nome_cliente;
    private String status;
    private String entregador;

    public ModeloExpedicao() {
        this.cod_pedido = cod_pedido;
        this.nome_cliente = nome_cliente;
        this.status = status;
        this.entregador = entregador;
    }

    public int getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(int cod_pedido) {this.cod_pedido = cod_pedido; }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntregador() {
        return entregador;
    }

    public void setEntregador(String entregador) {
        this.entregador = entregador;
    }
}
