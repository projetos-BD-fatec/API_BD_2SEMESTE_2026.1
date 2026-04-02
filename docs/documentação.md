<a id="readme-top"></a>


# 👾 API 2º Semestre BD

<p align="center">
      <img src="imagens\logo-bughunters.png" alt="logo da BugHunters" width="200">
      <h2 align="center">BugHunters</h2>
</p>

# Resumo da documentação

## 1. <a href="docs/processos/aceitação-e-permanência.md">Critérios de aceitação e permanência</a>
- Critérios definidos para aceitação ou não de novos integrantes no grupo e critérios que todos os integrantes devem seguir para permanecer na equipe.

## 2. <a href="docs/processos/estratégia-de-branch.md">Estratégia de branches</a>
- Um guia para organização das branches, para que o código principal esteja sempre livre de bugs.
- Diagrama do fluxo de branches:
![Fluxo de Branch](imagens/fluxograma-branches.png)

## 3. <a href=".md">Guia de estrutura do projeto</a>
- Um guia com objetivo de planejar a organização das pastas e arquivos do projeto.
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
     └── documentação.md
```
## 4. <a href="docs/processos/guia-pull-requests.md">Guia de Pull Request</a>
- Guia de Pull Request para garantir que features novas sejam integradas apenas após testar corretamente.
- [Template de Pull Request](../.github/pull_request_template.md)

## 5. <a href="docs/processos/padrão-de-commit.md">Padrão de commits</a>
- Um guia para padronizar os commits, melhorando a legibidade do repositório.
```
tipo(assunto): descrição curta
```
>Obs.:
>O assunto é opcional, porém bem útil para entendimento!

