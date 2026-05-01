<h1 align="center">
    <a href="https://amplication.com/#gh-light-mode-only">
    <img src="imagens/logo-lightversion.png">
    </a>
    <a href="https://amplication.com/#gh-dark-mode-only">
    <img src="imagens/logo-darkversion.png">
    </a>
</h1>

<p align="center">
  <i align="center">Plataforma de cadastro de aulas feito com java 🚀</i>
</p>


# Estratégia de Branch <a id="branches"></a>
O processo de desenvolvimento começa na branch Develop, onde novas funcionalidades são isoladas em branches de feature para passar por ciclos de desenvolvimento e teste. Se o resultado dos testes for negativo, o desenvolvedor retorna à codificação; caso contrário, realiza-se o Merge de volta para a branch de desenvolvimento. Diante de problemas de integração, o fluxo prevê a criação de branches de fix para correções rápidas e novos testes antes de reincorporar o código. Por fim, uma vez que o sistema está totalmente integrado e sem falhas, o código é mesclado para a branch MAIN, consolidando a entrega final em produção.

# Padrão de Commit e Pull <a id="commit"></a>
 
- feat (nova funcionalidade)
- fix (correção de bug)
- docs (documentação)
- Refactor (refatoração de código)
- Style (visual / formatação)
- Chore (manutenção / tarefas internas)
- Release (merge da develop → main)

```
tipo(assunto): descrição curta
```


# DoR  <a id="DoR"></a>

| DoR                                                | 
|----------------------------------------------------|
|✅Histórias bem descritas e compreensíveis|
|✅Time entende o objetivo e a complexidade|
|✅Prioridades definidas|
|✅Metas definidas|

# DoD <a id="DoD"></a>

| DoD                                               |
|----------------------------------------------------|
|✅Código final escrito e funcional|
|✅Documentação finalizada|
|✅Code review aprovado|


## DoR e DoD Por Sprint<a id="dodedor"></a>

| Sprint | Status | Docs |
| ------ | :----: | ---- |
| 1 |  concluida | [Sprint 1 Docs](docs/sprints/sprint-01) |
| 2 |  concluida | [Sprint 2 Docs](docs/sprints/sprint-02) |
| 3 |  não iniciada | [Sprint 3 Docs](docs/sprints/sprint-03) |

