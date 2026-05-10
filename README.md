<h1 align="center">
    <a>
    <img src= https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/imagens/Plan_guide_bughunters.png>
    </a>
</h1>

<p align="center">
| <a href ="#desafio"> Desafio</a>  |  
  <a href ="#solução"> Solução</a>  |
  <a href ="#backlog">Backlog</a>  |
  <a href ="#instalação">Instalação</a>  |
  <a href ="#estrutura">Estrutura</a>  |
  <a href ="#modelo">Modelo</a>  |
  <a href ="#equipe"> Equipe</a> |
</p>

# Desafio <a id="desafio"></a>
O desafio consiste em criar um sistema desktop para auxiliar os professores a montarem seus planos de aula.


<table width="100%">
  <tr>
    <!-- Coluna 1 -->
    <td width="33%" valign="top">
      <div align="center">
        <h3>
          <img src="https://img.icons8.com/?size=100&id=lrcr6RgJFygK&format=png&color=000000" width="20" height="20" style="vertical-align: middle;">
          <span>Fragmentação de Dados</span>
        </h3>
        <p>As informações necessárias para o planejamento de aulas estão dispersas.</p>
      </div>
    </td>
    <!-- Coluna 2 -->
    <td width="33%" valign="top">
      <div align="center">
        <h3>
          <img src="https://img.icons8.com/?size=100&id=nkGDoqzPxYM3&format=png&color=000000" width="20" height="20" style="vertical-align: middle;">
          <span>Logística</span>
        </h3>
        <p>Fatores externos e calendários variáveis tornam o processo propenso a erros.</p>
      </div>
    </td>
    <!-- Coluna 3 -->
    <td width="33%" valign="top">
      <div align="center">
        <h3>
          <img src="https://img.icons8.com/?size=100&id=102707&format=png&color=000000" width="20" height="20" style="vertical-align: middle;">
          <span>Automatização</span>
        </h3>
        <p>A dependência de planilhas manuais gera alto risco de inconsistências.</p>
      </div>
    </td>
  </tr>
</table>

# Solução <a id="solução"></a>
Centralizar o planejamento de aulas em um único ambiente, permitindo que o professor organize todo o semestre de forma visual e integrada, sendo possível visualizar as aulas, distribuir os conteúdos ao longo das datas e acompanhar o progresso do planejamento em relação à carga horária da disciplina.

# Backlog <a id="backlog"></a>
<details closed>
<summary>
 Features
</summary> <br />
    
| Requisito | Assunto                             | User Story                                                                                                                                                                                                              | Prioridade | Sprint | Observação | Estimativa (Fibonacci) | Status |
| --------- | ----------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------- | ------ | ---------- | ------ | ----- |
| [US01](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US01.md)     | Definição de horários da disciplina | Como professor, desejo visualizar os dias da semana e horários das aulas cadastrados para ter fácil acesso a essas informações.                                                                                         | Alta       | 2      | Meta       | 5 | ✅ |
| [US02](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US02.md)      | Gestão de conteúdos                 | Como professor, desejo visualizar os tópicos cadastrados para acompanhar a distribuição do conteúdo.                                                                                                                    | Alta       | 2      | Meta       | 3 | ✅ |
| [US03](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US03.md)      | Calendário acadêmico               | Como professor, desejo que minhas aulas sejam distribuídas pelos dias letivos que constam no calendário acadêmico para que eu não precise cadastrar os dias de aula manualmente.                                                                                               | Alta       | 2      | Meta       | 8 | ✅ |
| [US04](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US04.md)     | Calendário acadêmico                | Como professor, desejo que o calendário considere os feriados e eventos acadêmicos no planejamento das aulas para evitar marcar avaliações em datas inadequadas, como última semana de sprint e semana de planejamento.                                        | Alta       | 2      | Meta       | 3 | ✅ |
| [US05](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US05.md)      | Planejamento de aulas                | Como professor, desejo associar um tópico a cada aula do semestre para organizar o conteúdo que será ministrado em cada dia.    | Alta       | 2      | Meta       | 5 | ✅ |
| [US06](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US06.md)     | Gestão de disciplinas               | Como professor, desejo visualizar as disciplinas que já organizei para escolher aquela que desejo planejar.                                                                                                         | Moderada   | 2      | Meta              | 5 | ✅ |
| [US07](http://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US07.md)      | Controle carga horária              | Como professor, desejo visualizar quantas aulas já foram planejadas e quantas ainda faltam para garantir que a carga horária da disciplina seja cumprida.                                                           | Moderada   | 2      | Meta              | 5 | ✅ |
|  [US08](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US08.md)     | Controle carga horária              | Como professor, desejo ser avisado quando a carga horária não estiver sendo cumprida.                                                                                                                               | Moderada   | 2      | Bônus da sprint 2 | 3 | ✅ |
| [US09](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US09.md)      | Calendário acadêmico                | Como professor, desejo ser avisado ao tentar planejar aulas em datas não letivas/proibidas para evitar conflitos no cronograma.                                                                                     | Moderada   | 2      | Bônus da sprint 2 | 3 | ✅ |
| [US10](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US10.md)      | Exportar planejamento               | Como professor, desejo exportar o planejamento de aulas para facilitar subir em outras plataformas.                                                                                                                 | Moderada   | 3      | Meta | 5 | |
| [US11](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US11.md)    | Gestão de disciplinas               | Como professor, desejo cadastrar uma disciplina informando nome, curso, semestre e carga horária para iniciar o planejamento das aulas do semestre.                                                                 | Baixa      | 3      | Meta              | 3 |
| [US12](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US12.md)      | Gestão de disciplinas               | Como professor, desejo ter meu próprio cadastro para ter acesso apenas aos planejamentos de aulas das minhas disciplinas.                                                                                           | Baixa      | 3      | Meta              | 3 |
| [US13](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US13.md)      | Gestão de disciplinas               | Como professor, desejo conseguir selecionar uma das disciplinas que eu leciono para realizar o planejamento das aulas.                                                                                              | Baixa      | 3      | Meta              | 2 |
| [US14](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US14.md)      | Definição de horários da disciplina | Como professor, desejo informar os dias da semana em que ministro a disciplina para definir quando as aulas acontecem.                                                                                              | Baixa      | 3      | Meta              | 5 |
| [US15](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US15.md)      | Definição de horários da disciplina | Como professor, desejo informar a carga horária da disciplina para garantir que essa carga será cumprida.                                                                                                           | Baixa      | 3      | Meta              | 2 |
| [US16](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US16.md)      | Celendário acadêmico                 | Como professor, desejo que o calendário acadêmico esteja sempre atualizado mesmo nos próximos semestres.                                                                                                                      | Baixa      | 3      | Meta              | 8 |
| [US17](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US17.md)      | Planejamento de aulas               | Como professor, desejo editar o conteúdo de uma aula já planejada.                                                                                                                                                  | Baixa      | 3      | Bônus da sprint 3 | 2 |    
| [US18](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US18.md)      | Integração com celular           | Como professor, quero que meu conteúdo de aulas apareça no meu calendário do celular para eu conseguir me programar antecipadamente para as aulas.                                                                                          | Baixa      | 3      | Bônus da sprint 3 | 21 |
| [US19](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US19.md)      | Integração com celular                      | Como professor, quero receber lembrete do conteúdo da disciplina no celular dois dias antes da aula para eu lembrar dos assuntos que vou passar em sala.                                                                                                        | Baixa      | 3      | Bônus da sprint 3 | 21 |
| [US20](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US20.md)      | Integração com IA             | Como professor, eu desejo receber emails antes de cada aula com sugestões/dicas de assuntos personalizados sobre o conteúdo.                                                                | Baixa      | 3      | Bônus da sprint 3 | 21 |
| [US21](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/User_Stories/US21.md)      | Calendário interativo              | Como professor, eu desejo visualizar minhas aulas num calendário interativo, onde eu consiga selecionar um mês, e então visualizar as aulas dentro do mês, e ao passar o mouse no dia, aparece a descrição da aula.                | Baixa      | 3      | Bônus da sprint 3 | 21 |

</details>

<div align="center">
    <h1 id="sprint">Sprint</h1>
</div>
<div align="center">

| Sprint | Período | Docs |
| :----: | :-----: | :---: |
| 1 | 16/03 - 05/04 | [Sprint 1 Docs](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/tree/main/docs/sprints/sprint-01) |
| 2 | 13/04 - 03/05 | [Sprint 2 Docs](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/tree/main/docs/sprints/sprint-02) |
| 3 | 11/05 - 31/05 | [Sprint 3 Docs](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/tree/main/docs/sprints/sprint-03) |

</div>

# Instalação <a id="instalação"></a>
<details closed>
<summary>
 Manual de instalação
</summary> <br />
Este guia fornece as instruções necessárias para configurar e executar o projeto da API localmente para desenvolvimento ou avaliação.  
Para garantir o funcionamento correto da API em seu ambiente, certifique-se de ter os seguintes pré-requisitos instalados:  

```
→ Java JDK 17 ou superior.  
→ Maven 3.8+ para gestão de dependências.  
→ Git (utilize o Git Bash para execução de comandos).  
→ IDE Recomendada: IntelliJ IDEA ou VS Code.  
```

> **Nota**
> Caso prefira não utilizar o terminal,você também pode baixar o projeto diretamente pelo GitHub navegando até o botão `< > Code`, select `donwload` 

1. Abra o git bash e cole:
```shell
git clone: https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/tree/main
```
2. Abra sua IDE :
```shell
Abra a sua IDE, em seguida abra onde clonou o repositório e selecione a pasta projeto
```

3. Para que a API se conecte ao banco de dados externo (Supabase), siga estes passos: Navegue até a pasta: `src/main/resources/`, Crie um novo arquivo de texto e nomeie-o como `db.properties`, Abra o arquivo e cole exatamente a linha abaixo:  <br>
```shell
db.url=jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.ogvzlsaluygvhidfalut&password=Bughunters@2026&sslmode=require&prepareThreshold=0
```
4. Rodar o Aplicativo 
> **Nota**
> Caso prefira não utilizar o terminal,você também pode rodar o projeto navegando ate a classe app e selecionando o botao  `run` da sun IDE

```shell
mvn clean javafx:run 
```
</details>

# Estrutura <a id="estrutura"></a>
<details closed>
<summary>
 Estrutura
</summary> <br />
    
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

</details>

# Modelo Entidade Relacionamento <a id="modelo"></a>
<details closed>
<summary>
 Modelo Entidade Relacionamento
</summary> <br />

<p align="center">
    <img src= https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/main/docs/imagens/DER_1.jpeg >
</p>

</details>

<div align="center">
  <h1 id="equipe">Equipe</h1>
</div>

<div align="center">
  <table>
    <tr>
      <th>Membro</th>
      <th>Função</th>
      <th>Github</th>
      <th>Linkedin</th>
    </tr>
    <tr>
      <td>Lucas Monteiro</td>
      <td>Scrum Master</td>
      <td><a href="https://github.com/lhmontech"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a href="https://www.linkedin.com/in/lucas-henrique-monteiro-55101a365"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
    <tr>
      <td>Melina Ito</td>
      <td>Product Owner</td>
      <td><a href="https://github.com/melinaito1"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a href="https://www.linkedin.com/in/melinaito/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
    <tr>
    <td>Abraão Prado</td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/abraaops25"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a href="https://br.linkedin.com/in/abra%C3%A3o-prado-santana-830a06123"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
      </tr>
    <td>André Junqueira</td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/andre-sjunqueira"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a href="https://br.linkedin.com/in/andr%C3%A9-soares-junqueira-54668a26b"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
    <tr>
      <td>Isabelle Cristine</td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/bellecristines"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a href="https://www.linkedin.com/in/isabelle-leite-597a66362/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
      <td>Ramon Nascimento</td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/Ramon-1221"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a href="https://www.linkedin.com/in/ramon-nascimento-3bbb68249/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
    <tr>
      <td>Luis Gustavo</td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/gracianoluis"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a href="https://www.linkedin.com/in/luisgustavograciano/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
    <tr>
      <td>João Victor</td>
      <td>Desenvolvedor</td>
      <td><a href="https://github.com/blom28"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white"></a></td>
      <td><a "><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a></td>
    </tr>
    
  </table>
</div>

<p align="center">
  <sub>feito com 💚 na fatec sjc </sub>
</p>

