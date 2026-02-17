# 🔁 Guia de Pull Request

## O que é um Pull Request?
Pull Request é uma solicitação para que suas alterações sejam analisadas e adicionadas ao projeto.

Fluxo básico:

feature → develop

ou

develop → main (release)

---

## 🎯 Quando criar um Pull Request?

Você deve criar um PR quando:

- terminar uma feature
- corrigir um bug
- finalizar uma sprint (release)

---
## Como fazer um Pull Request

### Passo 1 — Criar Pull Request no GitHub
No repositório no GitHub:
1. Clique em **Pull Requests**
2. Clique em **New Pull Request**
3. Configure:
    Base branch:
        develop
    Compare:
        sua branch

    **Exemplo:**
        base: develop
        compare: feature/login

4. Clique em **Create Pull Request**

### Passo 2 — Preencher o template
Um template foi criado para facilitar a organização. 
[Template de Pull Request](../../.github/pull_request_template.md)

#### 🏷️ Tipo de PR

Marque uma opção:
- Feature → nova funcionalidade
- Bugfix → correção
- Documentation → documentação
- Refactor → refatoração
- Release → final de sprint

#### 📝 Descrição

Explique o que foi feito.

>Exemplo:
>*Cria sistema de login com autenticação.*

#### 🎯 Objetivo

Explique o problema resolvido.

>Exemplo:
>*Permitir que o usuário entre no sistema.*

#### 🧪 Como testar

Explique como verificar.

>Exemplo:
>1. Executar sistema
>2. Clicar em login
>3. Inserir dados

#### 📷 Evidências (opcional)

Adicionar prints se necessário.

#### ✅ Checklist

Confirme tudo antes de enviar.

### 📤 Passo 3 — Criar Pull Request

Clique em:
**Create Pull Request**

---

## ⚠️ Regras Importantes

Sempre:

✔ Criar branch própria
✔ Fazer commits organizados
✔ Testar antes

Nunca:

❌ Commit direto na develop
❌ Commit direto na main