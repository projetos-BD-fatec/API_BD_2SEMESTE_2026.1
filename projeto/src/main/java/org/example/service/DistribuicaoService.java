package org.example.service;

import org.example.model.Aula;
import org.example.model.Topico;

import java.util.*;
import java.util.stream.Collectors;

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

    public void distribuir(List<Aula> aulas, List<Topico> topicos) {

        Map<Long, Integer> cotasRestantes = new LinkedHashMap<>();
        for (Topico t : topicos) {
            cotasRestantes.put(t.getId(), t.getMaxAulas());
        }
        for (Aula a : aulas) {
            if (a.isAncorada() && a.getTopicoId() != null) {
                cotasRestantes.computeIfPresent(a.getTopicoId(), (id, v) -> v - 1);
            }
        }

        aulas.stream()
                .filter(a -> !a.isAncorada())
                .forEach(a -> a.setTopicoId(null));

        List<Topico> topicosParaDistribuir = topicos.stream()
                .filter(t -> cotasRestantes.getOrDefault(t.getId(), 0) > 0)
                .collect(Collectors.toList());

        long totalLivres = aulas.stream().filter(a -> !a.isAncorada()).count();

        Map<Long, Integer> alocacao = calcularAlocacao(topicosParaDistribuir, cotasRestantes, (int) totalLivres);
        atribuirTopicos(aulas, topicos, alocacao);
    }

    private Map<Long, Integer> calcularAlocacao(List<Topico> topicos, Map<Long, Integer> cotasRestantes, int totalLivres) {
        Map<Long, Integer> alocacao = new LinkedHashMap<>();
        for (Topico t : topicos) {
            alocacao.put(t.getId(), cotasRestantes.get(t.getId()));
        }

        int totalAlocado = alocacao.values().stream().mapToInt(i -> i).sum();

        if (totalAlocado <= totalLivres) {
            return alocacao;
        }

        int excesso = totalAlocado - totalLivres;
        excesso = reduzirExcesso(alocacao, topicos, excesso);

        if (excesso > 0) {
            throw new IllegalStateException(
                    "Não há aulas suficientes para acomodar todos os tópicos. " +
                            "Reduza os mínimos ou adicione mais datas no calendário."
            );
        }

        return alocacao;
    }

    private int reduzirExcesso(Map<Long, Integer> alocacao, List<Topico> topicos, int excesso) {
        List<Topico> ordenadosParaCorte = new ArrayList<>(topicos);
        ordenadosParaCorte.sort(
                Comparator.comparingInt(Topico::getPeso)
                        .thenComparingInt(t -> -topicos.indexOf(t))
        );

        boolean reduziu = true;
        while (excesso > 0 && reduziu) {
            reduziu = false;
            for (Topico t : ordenadosParaCorte) {
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

    private void atribuirTopicos(List<Aula> aulas, List<Topico> topicos, Map<Long, Integer> alocacao) {
        Queue<Aula> filaNormal = new LinkedList<>(
                aulas.stream().filter(a -> !a.isAncorada()).collect(Collectors.toList())
        );

        for (Topico topico : topicos) {
            if (topico.isAvaliacao()) continue;
            Integer quantidade = alocacao.get(topico.getId());
            if (quantidade == null || quantidade <= 0) continue;
            int atribuidas = 0;

            while (atribuidas < quantidade && !filaNormal.isEmpty()) {
                Aula aula = filaNormal.poll();
                aula.setTopicoId(topico.getId());
                atribuidas++;
            }
        }

        Queue<Aula> filaAvaliacao = new LinkedList<>(
                aulas.stream().filter(a -> !a.isAncorada() && a.getTopicoId() == null).collect(Collectors.toList())
        );

        for (Topico topico : topicos) {
            if (!topico.isAvaliacao()) continue;
            Integer quantidade = alocacao.get(topico.getId());
            if (quantidade == null || quantidade <= 0) continue;
            int atribuidas = 0;

            List<Aula> bloqueadasTemporariamente = new ArrayList<>();
            while (atribuidas < quantidade && !filaAvaliacao.isEmpty()) {
                Aula aula = filaAvaliacao.poll();
                if (isDiaBloqueado(aula)) {
                    bloqueadasTemporariamente.add(aula);
                    continue;
                }
                aula.setTopicoId(topico.getId());
                atribuidas++;
            }
            filaAvaliacao.addAll(bloqueadasTemporariamente);
        }
    }
}
