
# Octafin - Desafio Técnico
Projeto desenvolvido para o desafio técnico de estágio da Octafin.

## Sobre o projeto

O sistema permite simular empréstimos consignados em dois bancos fictícios, cada um com suas próprias regras de aprovação e taxas.

O backend recebe os dados da solicitação, consulta as APIs dos bancos e retorna os resultados em um formato padronizado, permitindo comparar as propostas.

## Tecnologias

- Java 21
- Spring Boot
- Maven
- APIs REST
- React + TypeScript (frontend em desenvolvimento)

## Estrutura e arquitetura

O projeto conta com duas aplicações Spring Boot:

- **Backend principal:** recebe as solicitações, valida os dados e consulta os bancos.
- **Mock Banks:** simula as APIs dos bancos Aurora e Horizonte.

A organização segue o padrão Controller → Service, utilizando DTOs e interfaces para separar responsabilidades.

Também foi utilizado o padrão Adapter para converter as respostas diferentes dos bancos em um formato único.

## Bancos fictícios

| Regra | Aurora | Horizonte |
|---|---|---|
| Juros mensais | 1,8% | 2,2% |
| Máximo de parcelas | 48 | 60 |
| Limite do salário | 30% | 35% |

As regras e taxas são fictícias, e os cálculos utilizam juros simples.

## Como executar

**Pré-requisitos:** Java 21 e Maven.

1. Execute `MockBanksApplication`, no projeto `mock-banks` (porta 8081).
2. Execute `BackendApplication`, no projeto principal (porta 8080).
3. Faça uma requisição para o endpoint de simulação.

### Exemplo de requisição

**POST** `http://localhost:8080/api/simulations`

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

O sistema retorna as avaliações dos bancos, com os status `APPROVED`, `REJECTED` ou `UNAVAILABLE` e os valores das propostas aprovadas.

## Uso de Inteligência Artificial

Durante o desenvolvimento, utilizei ferramentas de IA como apoio para estudar conceitos, esclarecer dúvidas e avaliar possíveis soluções.

A IA me ajudou principalmente a compreender melhor decisões de arquitetura, integração entre APIs e boas práticas em Java e Spring Boot.

Utilizei esse suporte como parte do meu processo de aprendizado, buscando entender as implementações e os motivos por trás das escolhas feitas no projeto.

## Espaço para propor

### 1. Resiliência nas integrações

**Problema:** uma API bancária pode demorar ou ficar indisponível.

**Proposta:** configurar timeouts e melhorar o tratamento individual de falhas.

**Por quê:** evitar que problemas em um banco prejudiquem toda a simulação.

**Status:** planejado.

### 2. Consultas paralelas

**Problema:** atualmente, os bancos são consultados sequencialmente.

**Proposta:** realizar as consultas de forma concorrente.

**Por quê:** reduzir o tempo de resposta quando houver múltiplos bancos.

**Status:** planejado.

### 3. Validação e testes

**Problema:** validações básicas e testes manuais não cobrem todos os cenários.

**Proposta:** implementar validação de CPF/CNPJ e testes automatizados com JUnit e Mockito.

**Por quê:** aumentar a confiabilidade e facilitar futuras alterações.

**Status:** planejado.

## Status do projeto

**Implementado:**
- API REST de simulação.
- Integração HTTP com dois bancos fictícios.
- Regras de aprovação independentes.
- Padronização das respostas.
- Tratamento básico de falhas.

**Em desenvolvimento:**
- Interface web.
- Testes automatizados.
- Melhorias na validação e no tratamento de erros.

---

*Projeto desenvolvido exclusivamente para fins de avaliação técnica, utilizando bancos e dados fictícios.*
