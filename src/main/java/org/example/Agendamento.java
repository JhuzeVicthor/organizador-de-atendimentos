package org.example;

class Agendamento {
    private Atendimentos atendimento;
    private int horarioInicio; // em minutos desde meia-noite

    public Agendamento(Atendimentos atendimento, int horarioInicio) {
        this.atendimento = atendimento;
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFormatado() {
        int horas = horarioInicio / 60;
        int minutos = horarioInicio % 60;
        return String.format("%02d:%02d", horas, minutos);
    }

    public int getDuracaoReal() {
        return atendimento.isRapido() ? 10 : atendimento.getMinutos();
    }

    public Atendimentos getAtendimento() {
        return atendimento;
    }
    public void setAtendimento(Atendimentos atendimento) {
        this.atendimento = atendimento;
    }

    public int getHorarioInicio() {
        return horarioInicio;
    }
    public void setHorarioInicio(int horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
}