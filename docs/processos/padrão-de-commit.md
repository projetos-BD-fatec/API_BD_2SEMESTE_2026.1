# :pushpin: Padrão de commits

## Objetivo
Garantir organização, estabilidade e rastreabilidade do código.

## Estrutura
```
tipo(assunto): descrição curta
```
>Obs.:
>O assunto é opcional, porém bem útil para entendimento!

## Tipos de commits:

- [feat](#-feat) 

- [fix](#-fix)

- [refactor](#-refactor)

- [docs](#-docs)

- [chore](#-chore)

- [test](#-test)

---

## 🟢 feat (feature)

👉 Quando você adiciona uma nova funcionalidade.

### Exemplos:

```bash
feat: adiciona sistema de login

feat(user): cria endpoint de cadastro
```

### Use quando:

- Criou algo novo
- Implementou uma nova regra de negócio
- Adicionou nova tela
- Criou novo endpoint

---

## 🔴 fix

👉 Quando você corrige um bug.

### Exemplos:
```bash
fix: corrige validação de senha

fix(auth): resolve erro de autenticação
```

### Use quando:

- Algo estava quebrado
- Comportamento errado foi ajustado

---

### 🟡 refactor

👉 Quando você melhora o código SEM mudar comportamento.

### Exemplos:
```bash
refactor: melhora organização do service

refactor(user): simplifica método de busca
```

### Use quando:

- Remove duplicação
- Melhora legibilidade
- Muda estrutura interna
- NÃO muda regra de negócio

---

## 🔵 docs

👉 Mudança só em documentação.

#### Exemplos:
```bash
docs: atualiza README

docs(branch): adiciona estratégia de branch
```

### Use quando:

- Atualizou README
- Alterou documentação de API
- Criou documentação de processo

---

## 🟣 chore

👉 Mudanças técnicas que não impactam regra de negócio.

### Exemplos:
```bash
chore: adiciona .gitignore

chore: configura estrutura do projeto

chore: atualiza dependências
```

### Use quando:

- Configuração de projeto
- Ajuste em build
- Atualização de dependências
- Organização de arquivos

---

## 🟠 test

👉 Quando você adiciona ou altera testes.

### Exemplos
```bash
test: adiciona teste para login

test(user): cria testes unitários do servisse
```

---

## 🧠 Regra de ouro

commit deve responder:

**O que foi feito?**

Não:

*O que eu estava fazendo*

