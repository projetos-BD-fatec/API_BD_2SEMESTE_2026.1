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

# Estratégia  <a id="branches"></a>
O processo de desenvolvimento estruturado no fluxograma começa na branch Develop, onde novas funcionalidades são isoladas em branches de feature para passar por ciclos de desenvolvimento e teste. Se o resultado dos testes for negativo, o desenvolvedor retorna à codificação; caso contrário, realiza-se o Merge de volta para a branch de desenvolvimento. Diante de problemas de integração, o fluxo prevê a criação de branches de fix para correções rápidas e novos testes antes de reincorporar o código. Por fim, uma vez que o sistema está totalmente integrado e sem falhas, o código é mesclado para a branch MAIN, consolidando a entrega final em produção.

# Estrutura <a id="estrutura"></a>
```
├── README.md
├── .github/
│    └── pull_request_template.md
├── projeto/
│    ├── .idea/
│    ├── src/
│    ├── target/
│    ├── pom.xml
│    └── .gitignore
└── docs/
     ├── processos/
     │   ├── estratégia-de-branch.md
     │   ├── padrão-de-commit.md
     │   ├── guia-estrutura-projeto.md
     │   ├── guia-de-pull-requests.md
     │   └── aceitação-e-permanência.md
     ├── imagens/
     │   ├── fluxograma-branches.png
     │   └── logo-bughunters.png
     ├── sprints/
     │   ├── sprint-01/
     │   │   └── README.md
     │   ├── sprint-02/
     │   │   └── README.md
     │   └── sprint-03/
     │         └── README.md
     └── README.md
```
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

# Cronograma de Sprints <a id="calendariosprint"></a>

| Sprint          |    Período    | Documentação    |
| --------------- | :-----------: | --------------- |
|  **✅ SPRINT 1** | 16/03 - 05/04 | [Sprint 1 Docs](docs/sprints/sprint-01) |
|  **✅ SPRINT 2** | 13/04 - 03/05 | [Sprint 2 Docs](docs/sprints/sprint-02) |
|  **❌ SPRINT 3** | 11/05 - 31/05 | [Sprint 3 Docs](docs/sprints/sprint-03) |


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

