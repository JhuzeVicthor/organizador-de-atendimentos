package org.example;

public class Atendimentos {
    private String nome;
    private int minutos;
    private boolean rapido;

    public Atendimentos(String nome, int minutos, boolean rapido) {
        this.nome = nome;
        this.minutos = minutos;
        this.rapido = rapido;
    }

    @Override
    public String toString(){
        return nome + " " + (rapido ? "rapido" : minutos + "minutos");
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMinutos() {
        return minutos;
    }
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public boolean isRapido() {
        return rapido;
    }
    public void setRapido(boolean rapido) {
        this.rapido = rapido;
    }
}