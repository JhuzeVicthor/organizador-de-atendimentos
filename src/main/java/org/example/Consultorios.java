package org.example;

import java.util.ArrayList;
import java.util.List;

public class Consultorios {
    private int numero;
    private List<Agendamento> horariosManhas;
    private List<Agendamento> horarioTarde;

    public Consultorios(int numero){
        this.numero = numero;
        this.horariosManhas = new ArrayList<>();
        this.horarioTarde = new ArrayList<>();
    }

    public int getTempoTotalManha() {
        return horariosManhas.stream().mapToInt(Agendamento::getDuracaoReal).sum();
    }

    public int getTempoTotalTarde() {
        return horarioTarde.stream().mapToInt(Agendamento::getDuracaoReal).sum();
    }

    public boolean cabeManha(Atendimentos atendimento) {
        return getTempoTotalManha() + atendimento.getMinutos() <= 210; // 3.5h
    }

    public boolean cabeTarde(Atendimentos atendimento) {
        return getTempoTotalTarde() + atendimento.getMinutos() <= 210; // 3.5h
    }

    public void adicionarManha(Atendimentos atendimento, int horarioInicio) {
        horariosManhas.add(new Agendamento(atendimento, horarioInicio));
    }

    public void adicionarTarde(Atendimentos atendimento, int horarioInicio) {
        horarioTarde.add(new Agendamento(atendimento, horarioInicio));
    }

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Agendamento> getHorariosManhas() {
        return horariosManhas;
    }
    public void setHorariosManhas(List<Agendamento> horariosManhas) {
        this.horariosManhas = horariosManhas;
    }

    public List<Agendamento> getHorarioTarde() {
        return horarioTarde;
    }
    public void setHorarioTarde(List<Agendamento> horarioTarde) {
        this.horarioTarde = horarioTarde;
    }
}
