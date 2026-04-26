# 📁 Estrutura do Projeto

## Objetivo
Este arquivo tem como objetivo estabelecer uma estrutura padrão para as pastas e arquivos do projeto, com a intenção de garantir escalabilidade sem perder organização.

```text
├── README.md
├── .github/
│    └── pull_request_template.md
├── projeto/
│    ├── .idea/
│    ├── src/
│    │    ├── main/
│    │    │    ├── java/
│    │    │    │    └── org/example/
│    │    │    │         ├── Main.java
│    │    │    │         |
│    │    │    │         ├── model/           # EXEMPLO: Representação das entidades do sistema
│    │    │    │         │    ├── Disciplina.java
│    │    │    │         │    ├── Aula.java
│    │    │    │         │    ├── Topico.java
│    │    │    │         │    └── Horario.java
│    │    │    │         |
│    │    │    │         ├── dao/             #EXEMPLO: Acesso ao banco (CRUD)
│    │    │    │         │    ├── DisciplinaDAO.java
│    │    │    │         │    ├── AulaDAO.java
│    │    │    │         │    ├── TopicoDAO.java
│    │    │    │         │    └── HorarioDAO.java
│    │    │    │         |
│    │    │    │         ├── db/              # Configuração e conexão com banco
│    │    │    │         │    ├── Conexao.java
│    │    │    │         │    └── ConfigBD.java
│    │    │    │         |
│    │    │    │         ├── controller/      # Controllers do JavaFX
│    │    │    │         │    ├── TelaDisciplinasController.java
│    │    │    │         │    └── TelaPlanejamentoController.java
│    │    │    │         |
│    │    │    │         └── service/         # Regras de negócio (opcional, mas recomendado)
│    │    │    │              └── PlanejamentoService.java
│    │    │    │
│    │    │    ├── resources/
│    │    │    │    ├── db.properties        # Configuração do banco
│    │    │    │    |
│    │    │    │    ├── org/example/
│    │    │    │    │    ├── TelaDisciplinas.fxml
│    │    │    │    │    └── TelaPlanejamento.fxml
│    │    │    │    |
│    │    │    │    ├── static/
│    │    │    │    │    └── css/
│    │    │    │    │         └── style.css
│    │    │    │    |
│    │    │    │    └── imagens/
│    │    │    │         └── (logos, ícones, etc)
│    │    │    │
│    │    │    └── module-info.java
│    │    │
│    │    └── test/                         # Testes
│    │
│    ├── target/
│    ├── pom.xml
│    └── .gitignore
│
└── docs/
     ├── processos/
     │   ├── estratégia-de-branch.md
     │   ├── padrão-de-commit.md
     │   ├── guia-estrutura-projeto.md
     │   ├── guia-de-pull-requests.md
     │   └── aceitação-e-permanência.md
     │
     ├── imagens/
     │   ├── fluxograma-branches.png
     │   └── logo-bughunters.png
     │
     ├── sprints/
     │   ├── sprint-01/
     │   │   └── README.md
     │   ├── sprint-02/
     │   │   └── README.md
     │   └── sprint-03/
     │         └── README.md
     │
     └── documentação.md
```
