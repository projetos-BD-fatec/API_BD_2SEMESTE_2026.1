<a id="readme-top"></a>

# 👾 PlanGuide

# Sprint 2
Na Sprint 2, o foco é entregar o MVP do produto, garantindo que o professor já consiga realizar o planejamento das aulas de forma completa e funcional.<br>
Priorizamos as funcionalidades essenciais para resolver o problema principal, enquanto melhorias de usabilidade e recursos adicionais serão desenvolvidos nas próximas sprints, evoluindo a experiência do usuário.<br>
**Metas da sprint:** US01, US02, US03, US04, US05, US06, US07.

## 📄 Backlog da Sprint <a id="backlog"></a>

| ID | Assunto                             | User Story                                                                                                                                                                                                              | Prioridade | Sprint | Observação | Estimativa (Fibonacci) | Status |
| --------- | ----------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------- | ------ | ---------- | ------ | ---- |
| [US01](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US01.md)     | Definição de horários da disciplina | Como professor, desejo visualizar os dias da semana e horários das aulas cadastrados para ter fácil acesso a essas informações.                                                                                         | Alta       | 2      | Meta       | 5 | ✅ |
| [US02](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US02.md)      | Gestão de conteúdos                 | Como professor, desejo visualizar os tópicos cadastrados para acompanhar a distribuição do conteúdo.                                                                                                                    | Alta       | 2      | Meta       | 3 | ✅ |
| [US03](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US03.md)      | Calendário acadêmico               | Como professor, desejo que minhas aulas sejam distribuídas pelos dias letivos que constam no calendário acadêmico para que eu não precise cadastrar os dias de aula manualmente.                                                                                               | Alta       | 2      | Meta       | 8 | ✅ |
| [US04](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US04.md)     | Calendário acadêmico                | Como professor, desejo que o calendário considere os feriados e eventos acadêmicos no planejamento das aulas para evitar marcar avaliações em datas inadequadas, como última semana de sprint e semana de planejamento.                                        | Alta       | 2      | Meta       | 3 | ✅ |
| [US05](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US05.md)      | Planejamento de aulas                | Como professor, desejo associar um tópico a cada aula do semestre para organizar o conteúdo que será ministrado em cada dia.    | Alta       | 2      | Meta       | 5 | ✅ |
| [US06](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US06.md)     | Gestão de disciplinas               | Como professor, desejo visualizar as disciplinas que já organizei para escolher aquela que desejo planejar.                                                                                                         | Moderada   | 2      | Meta              | 5 | ✅ |
| [US07](http://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US07.md)      | Controle carga horária              | Como professor, desejo visualizar quantas aulas já foram planejadas e quantas ainda faltam para garantir que a carga horária da disciplina seja cumprida.                                                           | Moderada   | 2      | Meta              | 5 | ✅ |
|  [US08](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US08.md)     | Controle carga horária              | Como professor, desejo ser avisado quando a carga horária não estiver sendo cumprida.                                                                                                                               | Moderada   | 2      | Bônus da sprint 2 | 3 | Transbordo sprint 3 |
| [US09](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US09.md)      | Calendário acadêmico                | Como professor, desejo ser avisado ao tentar planejar aulas em datas não letivas/proibidas para evitar conflitos no cronograma.                                                                                     | Moderada   | 2      | Bônus da sprint 2 | 3 | ✅ |
| [US10](https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/Docs/Backlog/docs/User_Stories/US10.md)      | Exportar planejamento               | Como professor, desejo exportar o planejamento de aulas para facilitar subir em outras plataformas.                                                                                                                 | Moderada   | 2      | Bônus da sprint 2 | 5 |Transbordo sprint 3|

---

<p align="right">(<a href="#readme-top">voltar ao topo</a>)</p>


## 🏃‍ DoR - Definition of Ready <a id="dor"></a>

- [x] Histórias bem descritas e compreensíveis
- [x] Time entende o objetivo e a complexidade
- [x] Prioridades definidas
- [x] Metas definidas

## 🏆 DoD - Definition of Done <a id="dod"></a>
- [x] Código final escrito e funcional
- [x] Documentação finalizada
- [ ] Code review aprovado

---

## 📄 Manual do usuário - PlanGuide

<details closed>
<summary>
 Manual do usuário
</summary> <br />
  
### 1. Seleção da Disciplina
Na tela inicial de disciplinas, o professor deve selecionar a matéria que deseja planejar.

<h1 align="center">
    <a>
    <img src= https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/docs/readme/docs/imagens/Tela_disciplina1.jpeg>
    </a>
</h1>

#### ⚠️ Observação: <br>
O cadastro de novas disciplinas ainda não está disponível nesta sprint.<br>
Nesta etapa, o sistema utiliza disciplinas previamente definidas.<br>
Após selecionar a disciplina (por exemplo, Modelagem de Banco de Dados), o usuário é automaticamente direcionado para a tela de planejamento.<br>
<br>
### 2. Tela de Planejamento
Na tela de planejamento, o professor organiza o conteúdo da disciplina ao longo do calendário de aulas.<br>
<br>

<h1 align="center">
    <a>
    <img src= https://github.com/projetos-BD-fatec/API_BD_2SEMESTE_2026.1/blob/docs/readme/docs/imagens/Tela_planejamento1.jpeg>
    </a>
</h1>

O professor pode cadastrar os tópicos que serão lecionados, informando:<br><br>
•	Nome do tópico (ex: Introdução, DER, Avaliação 1) <br>
•	Quantidade mínima de aulas <br>
•	Quantidade máxima de aulas <br>
•	Peso do conteúdo <br>
•	Indicação se é avaliação <br><br>
Após preencher os campos, deve clicar no botão “Incluir”.<br>

## 3. Regras de Distribuição de Conteúdo
Ao clicar em “Incluir”, o sistema automaticamente distribui o tópico no calendário de aulas.<br>
A distribuição segue as seguintes regras:<br><br>
#### Prioridade por Peso<br><br>
O peso define a prioridade do conteúdo na distribuição:<br><br>
•	Conteúdos com peso 3 têm maior prioridade <br>
•	Conteúdos com peso 2 têm prioridade intermediária <br>
•	Conteúdos com peso 1 têm menor prioridade <br><br>
Isso significa que, caso haja necessidade de preencher mais aulas além do mínimo, conteúdos com maior peso serão distribuídos primeiro.<br><br>

#### Quantidade de Aulas<br><br>
•	O sistema garante que o conteúdo seja distribuído até o máximo de aulas definido <br>
•	A distribuição ocorre automaticamente ao longo das aulas disponíveis <br><br>

#### Avaliações<br><br>
Ao marcar um tópico como avaliação, o sistema aplica restrições automáticas:<br><br>
As avaliações não serão alocadas em:<br><br>
•	Terceira semana de sprint <br>
•	Eventos especiais, como: kickoff, Review/Planning, Feira de Soluções <br><br>
Isso garante conformidade com as regras definidas pelo cliente.<br><br>

## 4. Calendário de Aulas
O calendário é preenchido automaticamente com base nos tópicos cadastrados.<br><br>
Cada aula contém:<br><br>
•	Data <br>
•	Dia da semana <br>
•	Evento (ex: Letivo, Kickoff) <br>
•	Horário <br>
•	Conteúdo da aula <br><br>
O professor pode visualizar facilmente como o conteúdo foi distribuído ao longo do semestre, além disso, se desejar, ele pode alterar manualmente os tópicos das aulas.<br><br>


## 5. Indicadores
A área de indicadores apresenta um resumo do planejamento: <br><br>
•	Carga horária planejada / total <br>
•	Quantidade total de aulas <br>
•	Aulas planejadas <br>
•	Aulas restantes <br>
•	Quantidade de tópicos cadastrados <br><br>
Essas informações ajudam o professor a acompanhar o progresso do planejamento em tempo real.<br><br>

## Resumo do Fluxo de Uso
1.	Selecionar a disciplina <br>
2.	Inserir os tópicos com suas configurações <br>
3.	Clicar em Incluir <br>
4.	Visualizar a distribuição automática no calendário <br>
5.	Acompanhar os indicadores<br><br>


</details>

