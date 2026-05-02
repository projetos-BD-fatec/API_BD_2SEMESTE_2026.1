package org.example.service;

import org.example.model.Aula;
import org.example.model.TopicoOrdenado;

import java.util.*;

public class DistribuicaoService {
    private static final List<String> eventosBloqueados = List.of(
            "Sprint 1 | Semana 3", "Sprint 2 | Semana 3", "Sprint 3 | Semana 3",
            "Planning", "Review | Planning",
            "Sprint Review", "Feira de Soluções", "Apresentação de TGs"
    );

    public boolean isDiaBloqueado(Aula aula) {
        if (aula.getEvento() == null) return false;
        return eventosBloqueados.stream()
                .anyMatch(ev -> ev.equalsIgnoreCase(aula.getEvento()));
    }

    public void distribuir(List<Aula> aulas, List<TopicoOrdenado> topicosOrdenados) {
        aulas.forEach(a -> a.setTopicoId(null));
        Map<Long, Integer> alocacao = calcularAlocacao(aulas, topicosOrdenados);
        atribuirTopicos(aulas, topicosOrdenados, alocacao);
    }

    private Map<Long, Integer> calcularAlocacao(List<Aula> aulas, List<TopicoOrdenado> topicos) {
        int totalAulas = aulas.size();

        Map<Long, Integer> alocacao = new LinkedHashMap<>();
        for (TopicoOrdenado t : topicos) {
            alocacao.put(t.getId(), t.getMaxAulas());
        }

        int totalAlocado = alocacao.values().stream().mapToInt(i -> i).sum();

        if (totalAlocado <= totalAulas) {
            return alocacao;
        }

        int excesso = totalAlocado - totalAulas;
        excesso = reduzirExcesso(alocacao, topicos, excesso);

        if (excesso > 0) {
            throw new IllegalStateException(
                    "Não há aulas suficientes para acomodar todos os tópicos. " +
                            "Reduza os mínimos ou adicione mais datas no calendário."
            );
        }

        return alocacao;
    }

    private int reduzirExcesso(Map<Long, Integer> alocacao, List<TopicoOrdenado> topicos, int excesso) {
        List<TopicoOrdenado> ordenadosParaCorte = new ArrayList<>(topicos);
        ordenadosParaCorte.sort(
                Comparator.comparingInt(TopicoOrdenado::getPeso)
                        .thenComparingInt(t -> -t.getPosicao())
        );

        boolean reduziu = true;
        while (excesso > 0 && reduziu) {
            reduziu = false;
            for (TopicoOrdenado t : ordenadosParaCorte) {
                if (excesso == 0) break;
                int atual = alocacao.get(t.getId());
                if (atual > t.getMinAulas()) {
                    alocacao.put(t.getId(), atual - 1);
                    excesso--;
                    reduziu = true;
                }
            }
        }
        return excesso;
    }

    private void atribuirTopicos(List<Aula> aulas, List<TopicoOrdenado> topicos, Map<Long, Integer> alocacao) {
        Queue<Aula> fila = new LinkedList<>(aulas);

        for (TopicoOrdenado topico : topicos) {
            int quantidade = alocacao.get(topico.getId());
            int atribuidas = 0;

            List<Aula> bloqueadasTemporariamente = new ArrayList<>();
            while (atribuidas < quantidade && !fila.isEmpty()) {
                Aula aula = fila.poll();

                if (topico.isAvaliacao() && isDiaBloqueado(aula)) {
                    bloqueadasTemporariamente.add(aula);
                    continue;
                }

                aula.setTopicoId(topico.getId());
                atribuidas++;
            }
            fila.addAll(bloqueadasTemporariamente);
        }
    }
}
