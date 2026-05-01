<h1 align="center">
    <a href="https://amplication.com/#gh-light-mode-only">
    <img src="docs/imagens/logo-lightversion.png">
    </a>
    <a href="https://amplication.com/#gh-dark-mode-only">
    <img src="docs/imagens/logo-darkversion.png">
    </a>
</h1>

<p align="center">
  <i align="center">Plataforma de cadastro de aulas feito com java 🚀</i>
</p>

<p align="center">
| <a href ="#desafio"> Desafio</a>  |  
  <a href ="#backlog">Backlog</a>  |
  <a href ="#dor">DoR</a>  |
  <a href ="#dod">DoD</a>  |
  <a href ="#sprint"> Cronograma de Sprints</a>  |
  <a href ="docs">Documentação</a>  |
  <a href ="#equipe"> Equipe</a> |
</p>

<p align="center">
    <img src="docs/imagens/L2.33.jpg" alt="dashboard"/>
</p>
  
# Desafio <a id="desafio"></a>

O desafio consiste em criar um sistema desktop para auxiliar os professores a montarem seus planos de aula.

O planejamento de aulas é um processo complexo e propenso a erros, pois exige o uso de diferentes fontes de informação para organizar horários, conteúdos e carga horária. Além disso, fatores como calendário acadêmico e feriados tornam a tarefa ainda mais difícil, levando muitos professores a utilizarem planilhas manuais, o que aumenta o risco de inconsistências e retrabalho.



# Solução <a id="solução"></a>

Centralizar o planejamento de aulas em um único ambiente, permitindo que o professor organize todo o semestre de forma visual e integrada, sendo possível visualizar as aulas, distribuir os conteúdos ao longo das datas e acompanhar o progresso do planejamento em relação à carga horária da disciplina.

# Backlog <a id="backlog"></a>

| Requisito | Assunto                             | User Story                                                                                                                                                                                                              | Prioridade | Sprint | Observação | Estimativa (Fibonacci) |
| --------- | ----------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------- | ------ | ---------- | ------ |
| RF01      | Definição de horários da disciplina | Como professor, desejo visualizar os dias da semana e horários das aulas cadastrados para ter fácil acesso a essas informações.                                                                                         | Alta       | 2      | Meta       | 5 |
| RF02      | Gestão de conteúdos                 | Como professor, desejo visualizar os tópicos cadastrados para acompanhar a distribuição do conteúdo.                                                                                                                    | Alta       | 2      | Meta       | 3 |
| RF03      | Planejamento de aulas               | Como professor, desejo associar um tópico a cada aula do semestre para organizar o conteúdo que será ministrado em cada dia.                                                                                            | Alta       | 2      | Meta       | 5 |
| RF04      | Calendário acadêmico                | Como professor, desejo que minhas aulas sejam distribuídas pelos dias letivos que constam no calendário acadêmico para que eu não precise cadastrar os dias de aula manualmente.                                        | Alta       | 2      | Meta       | 8 |
| RF05      | Calendário acadêmico                | Como professor, desejo que o calendário considere os feriados e eventos acadêmicos no planejamento das aulas para evitar marcar avaliações em datas inadequadas, como última semana de sprint e semana de planejamento. | Alta       | 2      | Meta       | 3 |
| RF06      | Gestão de disciplinas               | Como professor, desejo visualizar as disciplinas que já organizei para escolher aquela que desejo planejar.                                                                                                         | Moderada   | 2      | Meta              | 5 |
| RF07      | Controle carga horária              | Como professor, desejo visualizar quantas aulas já foram planejadas e quantas ainda faltam para garantir que a carga horária da disciplina seja cumprida.                                                           | Moderada   | 2      | Meta              | 5 |
| RF08      | Controle carga horária              | Como professor, desejo ser avisado quando a carga horária não estiver sendo cumprida.                                                                                                                               | Moderada   | 2      | Bônus da sprint 2 | 3 |
| RF09      | Calendário acadêmico                | Como professor, desejo ser avisado ao tentar planejar aulas em datas não letivas/proibidas para evitar conflitos no cronograma.                                                                                     | Moderada   | 2      | Bônus da sprint 2 | 3 |
| RF10      | Exportar planejamento               | Como professor, desejo exportar o planejamento de aulas para facilitar subir em outras plataformas.                                                                                                                 | Moderada   | 2      | Bônus da sprint 2 | 5 |
| RF11      | Gestão de disciplinas               | Como professor, desejo cadastrar uma disciplina informando nome, curso, semestre e carga horária para iniciar o planejamento das aulas do semestre.                                                                 | Baixa      | 3      | Meta              | 3 |
| RF12      | Gestão de disciplinas               | Como professor, desejo ter meu próprio cadastro para ter acesso apenas aos planejamentos de aulas das minhas disciplinas.                                                                                           | Baixa      | 3      | Meta              | 3 |
| RF13      | Gestão de disciplinas               | Como professor, desejo conseguir selecionar uma das disciplinas que eu leciono para realizar o planejamento das aulas.                                                                                              | Baixa      | 3      | Meta              | 2 |
| RF14      | Definição de horários da disciplina | Como professor, desejo informar os dias da semana em que ministro a disciplina para definir quando as aulas acontecem.                                                                                              | Baixa      | 3      | Meta              | 5 |
| RF15      | Definição de horários da disciplina | Como professor, desejo definir o horário de início e fim das aulas para organizar corretamente cada encontro.                                                                                                       | Baixa      | 3      | Meta              | 5 |
| RF16      | Definição de horários da disciplina | Como professor, desejo informar a carga horária da disciplina para garantir que essa carga será cumprida.                                                                                                           | Baixa      | 3      | Meta              | 2 |
| RF17      | Gestão de conteúdos                 | Como professor, desejo cadastrar os tópicos de cada aula para planejar o conteúdo do semestre.                                                                                                                      | Baixa      | 3      | Meta              | 3 |
| RF18      | Gestão de conteúdos                 | Como professor, desejo definir quantas aulas são necessárias para cada tópico para garantir que todo o conteúdo seja coberto.                                                                                       | Baixa      | 3      | Meta              | 5 |
| RF19      | Planejamento de aulas               | Como professor, desejo editar o conteúdo de uma aula já planejada.                                                                                                                                                  | Baixa      | 3      | Bônus da sprint 3 | 2 |
| RF20      | Calendário acadêmico                | Como professor, desejo conseguir fazer o upload do calendário acadêmico para que eu não tenha que digitar manualmente cada dia letivo.                                                                              | Baixa      | 3      | Bônus da sprint 3 | 8 |      
| RF21      | Replanejamento                      | Como professor, desejo reorganizar o conteúdo das aulas ao longo do semestre para adaptar o planejamento quando necessário.                                                                                         | Baixa      | 3      | Bônus da sprint 3 | 2 |
| RF22      | Replanejamento                      | Como professor, desejo guardar o planejamento das aulas para poder continuar de onde parei em outro momento.                                                                                                        | Baixa      | 3      | Bônus da sprint 3 | 5 |
| RF23      | Integração com celular              | Como professor, quero que meu conteúdo de aulas apareça no meu calendário do celular para eu conseguir me programar antecipadamente para as aulas.                                                                  | Baixa      | 3      | Bônus da sprint 3 | 21 |
| RF24      | Integração com celular              | Como professor, quero receber lembrete do conteúdo da disciplina no celular dois dias antes da aula para eu lembrar dos assuntos que vou passar em sala.                                                            | Baixa      | 3      | Bônus da sprint 3 | 21 |
| RF25      | Integração com IA                   | Como professor, eu desejo receber emails antes de cada aula com sugestões/dicas de assuntos personalizados sobre o conteúdo.                                                                                        | Baixa      | 3      | Bônus da sprint 3 | 21 |
| RF26      | Calendário interativo               | Como professor, eu desejo visualizar minhas aulas num calendário interativo, onde eu consiga selecionar um mês, e então visualizar as aulas dentro do mês, e ao passar o mouse no dia, aparece a descrição da aula. | Baixa      | 3      | Bônus da sprint 3 | 21 |

# Cronograma de Sprints <a id="sprint"></a>

| Sprint          |    Período    | Documentação    |
| --------------- | :-----------: | --------------- |
|  **✅ SPRINT 1** | 16/03 - 05/04 | [Sprint 1 Docs](docs/sprints/sprint-01) |
|  **✅ SPRINT 2** | 13/04 - 03/05 | [Sprint 2 Docs](docs/sprints/sprint-02) |
|  **❌ SPRINT 3** | 11/05 - 31/05 | [Sprint 3 Docs](docs/sprints/sprint-03) |


# DoR 

| DoR                                                |
|----------------------------------------------------|
|✅Histórias bem descritas e compreensíveis|
|✅Time entende o objetivo e a complexidade|
|✅Prioridades definidas|
|✅Metas definidas|

# DoD

| DoD                                               |
|----------------------------------------------------|
|✅Código final escrito e funcional|
|✅Documentação finalizada|
|✅Code review aprovado|

          

# Equipe <a id="equipe"></a>

<div align="left">
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

