# Justificativa do raciocínio

## Modelagem do problema

### 1. Como você classificou esse problema? (Ex.: empacotamento, escalonamento, busca, otimização combinatória, etc.) Justifique a escolha citando características do enunciado que apoiam essa classificação.

Classifiquei este problema como um problema de **empacotamento** combinado com **escalonamento de tarefas**.
Temos múltiplos "consultórios" com capacidade limitada 3h30 por sessão,
cada "atendimento" tem uma "duração", o objetivo é minimizar o número de consultórios utilizado,
os horários fixos das sessões adiciona o componente de escalonamento.

### 2. Esse problema tem alguma semelhança com problemas clássicos da computação? Cite pelo menos um e explique a analogia em suas próprias palavras.

Consultórios são caixas, atendimentos são itens, e a duração das sessões é a capacidade de cada caixas.
Temos duas caixas por consultório (manhã e tarde), as caixas têm restrições temporais específicas,
os atendimentos não podem ser divididos, o objetivo é minimizar o número total de caixas

### 3. Quais foram as estruturas de dados que você escolheu para representar consultórios, sessões e atendimentos? Por que cada uma delas? O que mudaria se você tivesse usado outra?

**Atendimento**: Objeto com nome, duração em minutos e ‘flag’ rápido, 
porque encapsula todas as propriedades relevantes de um atendimento

**Consultorio**: Lista de agendamentos para manhã e tarde porque, 
separa claramente as duas sessões e facilita verificação de capacidade

**Agendamento**: Atendimento + horário de início calculado porque, 
permite manter o vínculo entre o atendimento e o seu horário específico

**Alternativas consideradas**: Se eu usasse arrays simples, perderia a clareza do código,
e se eu usasse apenas uma lista plana, seria mais difícil pensar sobre as duas sessões

## Parte 2 — Estratégia algorítmica

### 4. Descreva, em linguagem natural (sem código), o algoritmo que você implementou — passo a passo, como se estivesse explicando para um colega que nunca viu o problema.

O programa lista todos os atendimentos do arquivo de entrada, com isso ele tenta encaixar no horario da manhã a consulta disponivel, se não couber por causa do tempo,
ele tenta na sessão da tarde e se não couber de novo, ele cria um novo consultório. Ele faz isso em um loop até todos
atendimentos ficarem alocados. Além de calcular os horários automático conforme a regra do negócio.

### 5. Sua solução é gulosa, exata, heurística ou usa alguma outra abordagem? Como você chegou a essa decisão?

Cheguei a esta decisão porquê o problema é difícil, então soluções exatas são inviáveis para muitas entradas

### 6. Existe alguma entrada para a qual seu algoritmo não encontraria a melhor solução possível? Dê um exemplo concreto (pode inventar uma lista pequena de atendimentos) e explique o que aconteceria.

Sim, existe entrada onde o algoritmo não encontra a solução ótima

Consultório 1 manhã: A (120min) + D (90min) = 210min 
Consultório 2 manhã: B (120min) + C (90min) = 210min 
E fica para tarde do Consultório 1 (não ideal)

O algoritmo "gasta" D prematuramente quando poderia usar E para preencher melhor. Usando o consultório 1 com o E (10min)

### 7. Qual é a complexidade de tempo aproximada da sua solução em função do número n de atendimentos? Mostre seu raciocínio para chegar nessa estimativa.

Complexidade: O(n log n + n²), onde n é o número de atendimentos então o raciocínio, na prática,
como m tende a ser pequeno (poucos consultórios), o comportamento é próximo de O(n log n).

## Parte 3 — Decisões de implementação

### 8. Como seu programa decide quantos consultórios abrir? Explique o critério.

O programa começa com nenhum consultorio e cria novos consultorios sobre a demanda. 
Se um atendimento não cabe em nenhum consultório existente, cria-se um novo consultório e 
isso garante o número mínimo necessário segundo a heurística FFD

### 9. Como você tratou os atendimentos expressos? Por que essa abordagem?

Atendimentos expressos são tratados como tendo duração fixa de 10 minutos,
são identificados pela palavra "rápido" no final da descrição e
recebem o mesmo tratamento que outros atendimentos, apenas com duração menor.
isso simplifica a implementação sem perder generalidade

### 10. Aponte um trecho do seu código que você considera a parte mais inteligente da solução, e outro que você acha que poderia ser melhorado. Justifique ambos.

**Parte mais inteligente**:
Ordenação decrescente + alocação em primeiro consultório disponível
atendimentos.sort((a1, a2) -> Integer.compare(a2.getDuracaoMinutos(), 
                                              a1.getDuracaoMinutos()));

**Trecho que poderia ser melhorado**:
Cálculo do horário da reunião - poderia ser mais determinístico
if (consultorio.getSessoesTarde().isEmpty()) {
horarioReuniao = Math.max(17 * 60, INICIO_TARDE);
}