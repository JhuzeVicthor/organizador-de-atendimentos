package org.example;

import java.util.ArrayList;
import java.util.List;

public class Clinica {
    private static final int INICIO_MANHA = 8*60;
    private static final int FIM_MANHA = (11 * 60) + 30;
    private static final int INICIO_TARDE = (13 * 60) + 30;
    private static final int DURACAO = 210;

    private List<Atendimentos> atendimentos = new ArrayList<>();
    private List<Consultorios> consultorios = new ArrayList<>();

    public static void main(String[] args){
        Clinica organizarClinica = new Clinica();
        organizarClinica.carregarAtendimentos();
        organizarClinica.organizarConsultorios();
        organizarClinica.resultados();
    }

    public void carregarAtendimentos(){
        String[] dados = {
            "Castração de gato adulto 90min",
            "Aplicação de vacina antirrábica expresso",
            "Limpeza dentária em cão de pequeno porte 45min",
            "Consulta de rotina em filhote de gato 30min",
            "Exame de sangue completo 30min",
            "Cirurgia ortopédica em cão atropelado 120min",
            "Avaliação dermatológica em cão com sarna 45min",
            "Microchipagem expresso",
            "Retirada de pontos pós-cirúrgicos 30min",
            "Atendimento de emergência respiratória 60min",
            "Consulta com nutricionista veterinária 45min",
            "Ultrassonografia abdominal 60min",
            "Castração de cadela em fase reprodutiva 90min",
            "Vermifugação em ninhada de filhotes 30min",
            "Avaliação cardiológica em cão idoso 60min",
            "Curativo de ferida exposta 30min",
            "Aplicação de vacina V10 expresso",
            "Consulta comportamental para gato resgatado 45min",
            "Raio-X de pata traseira 30min",
            "Tratamento de otite em cão 30min",
            "Cirurgia de remoção de tumor cutâneo 90min",
            "Resgate emocional: socialização de gato feral 60min",
            "Avaliação ortopédica em cão com displasia 45min"
        };

        for (String linha : dados) {
            Atendimentos atendimento = analisarAtendimento(linha);
            if (atendimento != null){
                atendimentos.add(atendimento);
            }
        }
    }

    private Atendimentos analisarAtendimento(String linha) {

        if (linha.toLowerCase().contains("expresso")) {
            String nome = linha.replace("expresso", "").trim();
            return new Atendimentos(nome, 10, true);
        }

        int lastSpace = linha.lastIndexOf(" ");
        String duracaoStr = linha.substring(lastSpace + 1);

        int duracao = Integer.parseInt(duracaoStr.replace("min", "").trim());

        String nome = linha.substring(0, lastSpace).trim();

        return new Atendimentos(nome, duracao, false);
    }

    public void organizarConsultorios() {
        // Ordena por duração (maior primeiro) - estratégia First Fit Decreasing
        atendimentos.sort((a1, a2) -> Integer.compare(a2.getMinutos(),
                a1.getMinutos()));

        for (Atendimentos atendimento : atendimentos) {
            boolean alocado = false;

            // Tenta alocar em consultório existente
            for (Consultorios consultorio : consultorios) {
                if (consultorio.cabeManha(atendimento)) {
                    int horarioInicio = INICIO_MANHA + consultorio.getTempoTotalManha();
                    consultorio.adicionarManha(atendimento, horarioInicio);
                    alocado = true;
                    break;
                } else if (consultorio.cabeTarde(atendimento)) {
                    int horarioInicio = INICIO_TARDE + consultorio.getTempoTotalTarde();
                    consultorio.adicionarTarde(atendimento, horarioInicio);
                    alocado = true;
                    break;
                }
            }

            // Se não conseguiu alocar, cria novo consultório
            if (!alocado) {
                Consultorios novoConsultorio = new Consultorios(consultorios.size() + 1);
                int horarioInicio = INICIO_MANHA;
                novoConsultorio.adicionarManha(atendimento, horarioInicio);
                consultorios.add(novoConsultorio);
            }
        }
    }

    public void resultados() {
        for (Consultorios consultorio : consultorios) {
            System.out.println("Consultório " + consultorio.getNumero() + ":");

            // Sessão da manhã
            for (Agendamento agendamento : consultorio.getHorariosManhas()) {
                System.out.println(agendamento.getHorarioFormatado() + " " +
                        agendamento.getAtendimento());
            }
            System.out.println("11:30 Higienização");

            // Sessão da tarde
            for (Agendamento agendamento : consultorio.getHorarioTarde()) {
                System.out.println(agendamento.getHorarioFormatado() + " " +
                        agendamento.getAtendimento());
            }

            // Calcula horário da reunião
            int tempoTotalTarde = consultorio.getTempoTotalTarde();
            int horarioReuniao = INICIO_TARDE + tempoTotalTarde;

            if (consultorio.getHorarioTarde().isEmpty()) {
                horarioReuniao = Math.max(17 * 60, INICIO_TARDE); // mínimo 17:00
            }

            // Garante que a reunião é entre 17:00 e 18:00
            if (horarioReuniao < 17 * 60) {
                horarioReuniao = 17 * 60;
            } else if (horarioReuniao > 17 * 60 + getRandomMinutes()) {
                horarioReuniao = 17 * 60 + getRandomMinutes();
            }

            String horarioReuniaoFormatado = String.format("%02d:%02d",
                    horarioReuniao / 60, horarioReuniao % 60);
            System.out.println(horarioReuniaoFormatado + " Reunião de encerramento");
            System.out.println();
        }
    }

    private int getRandomMinutes() {
        // Gera um horário aleatório entre 17:00 e 17:59
        return (int) (Math.random() * 60);
    }


}
