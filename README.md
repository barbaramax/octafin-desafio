
# Octafin - Simulador de Empréstimo Consignado

Projeto desenvolvido para o desafio técnico de estágio da Octafin.

## Sobre o projeto

O sistema permite simular empréstimos consignados em dois bancos fictícios, cada um com suas próprias regras de aprovação.

O usuário informa seus dados e as condições desejadas. O backend consulta os bancos e retorna os resultados em um formato padronizado, permitindo comparar as propostas.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Maven
- JUnit 5
- React + TypeScript
- Vite
- CSS

## Arquitetura

O projeto possui três partes:

- **Backend:** recebe as solicitações, valida os dados e consulta os bancos.
- **Mock Banks:** simula as APIs dos bancos Aurora e Horizonte.
- **Frontend:** formulário para realizar simulações e visualizar os resultados.

No backend, utilizei a separação entre Controller, Service e Adapter.

A interface `BankProvider` define um contrato comum para os bancos. Cada adapter realiza a comunicação HTTP e converte a resposta para o formato `BankResult`.

## Bancos fictícios

| Regra | Aurora | Horizonte |
|---|---|---|
| Taxa mensal | 1,8% | 2,2% |
| Máximo de parcelas | 48 | 60 |
| Limite do salário | 30% | 35% |

As regras são fictícias e utilizam juros simples para facilitar a simulação.

## Como executar

### Pré-requisitos

- Java 21
- Maven
- Node.js e npm

### 1. Mock Banks

Execute `MockBanksApplication` no projeto `mock-banks`.

Porta: `8081`.

### 2. Backend

Execute `BackendApplication` no projeto principal.

Porta: `8080`.

### 3. Frontend

Na pasta `frontend`, execute:

```bash
npm install
npm run dev
```

Acesse `http://localhost:5173`.

As três aplicações devem estar executando para realizar as simulações.

## Endpoint principal

**POST** `/api/simulations`

Exemplo de requisição:

```json
{
  "name": "Maria Silva",
  "email": "maria@email.com",
  "cpf": "12345678909",
  "salary": 4500,
  "employerCnpj": "11222333000181",
  "loanAmount": 12000,
  "installments": 24
}
```

O sistema retorna as avaliações dos bancos com os status:

- `APPROVED`: empréstimo aprovado.
- `REJECTED`: solicitação recusada.
- `UNAVAILABLE`: banco indisponível ou falha na comunicação.

## Validações e tratamento de erros

O backend utiliza Bean Validation para validar os dados recebidos, incluindo CPF, CNPJ e valores obrigatórios.

As integrações bancárias possuem timeouts configurados:

- **Conexão:** 2 segundos.
- **Leitura:** 5 segundos.

Em caso de falha na comunicação, o banco é identificado como `UNAVAILABLE`, sem confundir uma indisponibilidade com uma rejeição.

O frontend também apresenta uma mensagem de erro quando a simulação não pode ser realizada.

## Testes

Foram implementados testes unitários com JUnit 5 para os services dos bancos Aurora e Horizonte.

Os testes verificam cenários de aprovação, rejeição e o cálculo dos valores das parcelas.

## Uso de Inteligência Artificial

Durante o desenvolvimento, utilizei IA como ferramenta de apoio para estudar conceitos, tirar dúvidas e entender melhor algumas decisões técnicas.

No frontend, utilizei principalmente para auxiliar na construção da interface com React e TypeScript e na integração com o backend.

Procurei entender as soluções sugeridas e adaptar o código ao que fazia sentido para o projeto, mantendo uma implementação simples e organizada.

## Espaço para propor

### 1. Consultas paralelas

**Problema:** atualmente, os bancos são consultados sequencialmente.

**Proposta:** executar as consultas em paralelo.

**Por quê:** reduzir o tempo de espera quando houver vários bancos.

**Status:** planejado.

### 2. Melhorar a validação e os testes

**Problema:** os testes atuais cobrem apenas os cenários principais, e as mensagens de validação no frontend são genéricas.

**Proposta:** adicionar testes para entradas inválidas, timeouts e falhas de integração, além de apresentar mensagens mais específicas para o usuário.

**Por quê:** aumentar a confiabilidade e facilitar a identificação de problemas.

**Status:** planejado.

## Status do projeto

- [x] Backend com Spring Boot
- [x] Dois bancos fictícios
- [x] Integração HTTP entre aplicações
- [x] Padronização das respostas
- [x] Frontend com React e TypeScript
- [x] Validação de CPF e CNPJ
- [x] Tratamento básico de erros
- [x] Timeouts nas integrações
- [x] Testes unitários com JUnit 5

---

