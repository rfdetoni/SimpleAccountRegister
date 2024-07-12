# Techincal Challenge


Crie um sistema de gestão bancária por meio de uma API, compostapor dois endpoints:
"/conta" e "/transacao". O endpoint "/conta" deve criar e fornecer informações sobre o
número da conta. O endpoint "/transacao" será responsável por realizar diversas operações
financeiras.

Os endpoints devem ter o seguintes padrões de entrada e saídanoformato json:
Use as seguintes siglas para as formas de pagamento:
P => Pix
C => Cartão de Crédito
D => Cartão de Débito

POST /conta
input => JSON { "numero_conta": 234, "saldo":180.37}
output => HTTP STATUS 201 / JSON {"numero_conta": 234, “saldo”: 180.37}
POST /transacao
input => JSON {"forma_pagamento":"D", "numero_conta": 234, "valor":10}
output => HTTP STATUS 201 / JSON {“numero_conta”: 234, “saldo”: 170.07}
HTTP STATUS 404 (Caso não tenha saldo disponível)
GET /conta?numero_conta=234
output => Caso não exista a conta deve retornar HTTP STATUS 404.
Caso exista a conta retorna HTTP STATUS 200 e umJSON:
{“numero_conta”: 234, “saldo”: 170.07}

Aqui estão as etapas do desafio:
Crie um endpoint na API que permita a criação de uma novacontacom um saldo inicial de
valor float.
Há três formas de transação disponíveis: débito, crédito e Pix, cada uma com taxas
diferentes. As taxas são acrecidas nas transações, ouseja, é descontado o valor + taxa. Por
exemplo, uma transação de R$10 no débito, deverá ser descontado R$ 10,30.
Taxa de débito: 3% sobre a operação
Taxa de crédito: 5% sobre a operação
Taxa do Pix: Sem custo
Implementar a validação caso a conta já existe (POST);
Importante lembrar que todas as contas não possuemlimitedecheque especial, o que
significa que não é permitido ter saldonegativo. Portanto, implementar as validações
necessárias paragarantir que as transações não excedam o saldo disponível.
As chamadas devem interferir no saldo da conta para as próximas operações. Fica facultativo
a persistência dos dados.
Após realizar o teste, disponibilize o código no github, deixe orepositório privado e adicione
como colaborador os usuários informados pelo recrutamento.
Adicione um readme de como executar sua aplicação

Diferenciais:
● Realize commits bem escritos;
● Realize os testes necessários;
● Mantenha um código limpo;
● Utilização de design patterns;
● Persistência de dados.


# Applied technologies:
   - Java 17
   - Spring boot 3.3.1
   - Lombok
   - Database H2
   - OpenApi Swagger 3
   - JUnit
   - Mockito

# Access:
   - Documentation: Access the complete API documentation through Swagger - http://localhost:8082/swagger-ui/index.html
   - H2 Console: Manage the in-memory H2 database through the console - http://localhost:8082/h2-console
      - db Name: test
      - User: sa
      - Password: 1234

# Prerequisites:
   - Java 17 installed on the machine
   - Maven installed

# How to Run
   - Through an IDE: Use your favorite IDE to run the application as a Spring Boot project.
   - Maven command: Open a terminal in the project root folder and run the command " mvn spring-boot:run ".
  
# Usage and Tests
 - The API endpoints are all documented in Swagger and can be accessed directly through the interface without authentication.
 - The H2 database is used for development purposes only. In a real-world scenario, a persistent database should be used.
