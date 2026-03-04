# 🎯 GoalTracker (Life Event Tracker)

Um sistema gamificado de rastreamento de metas e eventos de vida baseado em microsserviços. Os usuários podem criar missões, definir dificuldades e prazos, e ao concluí-las, recebem pontos de experiência (XP) através de uma arquitetura orientada a eventos.

## 🏗️ Arquitetura do Sistema

O projeto foi construído utilizando **Arquitetura Hexagonal (Ports and Adapters)** combinada com princípios de **Domain-Driven Design (DDD)**. Isso garante que as regras de negócio fiquem isoladas de frameworks, banco de dados e mensageria, mantendo o domínio central puro e testável.

### 🧩 Estrutura dos Microsserviços

O ecossistema é composto por 3 microsserviços principais:

1. **User Service:** Responsável pelo gerenciamento de usuários e autenticação.
2. **Mission Service:** Responsável pelo ciclo de vida das missões (CRUD). Valida regras de negócio de domínio (prazos não podem estar no passado, categorias válidas e dificuldades de 1 a 5).
3. **Gamification Service:** Serviço reativo que consome os eventos do sistema assincronamente para calcular e atribuir XP aos usuários, gerenciando níveis e sistema de recompensas.

### 🛠️ Tecnologias Utilizadas
* **Java 17+** & **Spring Boot 3**
* **PostgreSQL** (Banco de dados relacional)
* **Flyway** (Migrations de banco de dados)
* **Apache Kafka & Zookeeper** (Mensageria assíncrona / Event-Driven)
* **Docker & Docker Compose** (Infraestrutura local)

---

## 🚀 Como Executar o Projeto Localmente

Siga os passos abaixo para rodar a infraestrutura e os microsserviços na sua máquina.

### 1. Pré-requisitos
* [Docker Desktop](https://www.docker.com/) ou Rancher Desktop rodando.
* Java Development Kit (JDK) 17 ou superior.
* Maven instalado (ou utilize o `.mvnw` embutido).

### 2. Configurar Variáveis de Ambiente
Na raiz do projeto (onde está o `docker-compose.yml`), você encontrará um arquivo `.env.example`.
Crie um arquivo chamado **apenas `.env`** e configure suas senhas locais:

```env
DB_USER=postgres
DB_PASSWORD=postgres
DB_NAME=goaltracker_mission
```

### 3. Subir a Infraestrutura (Banco e Kafka)
Abra o terminal na pasta onde está o `docker-compose.yml` e execute:

```bash
docker-compose up -d
```
*Isso fará o download das imagens (confluentinc/cp-kafka e cp-zookeeper) e iniciará o PostgreSQL e o Kafka em segundo plano.*

### 4. Rodar os Microsserviços
Você pode iniciar os microsserviços de duas formas:
* **Via IDE (IntelliJ/Eclipse):** Abra o projeto, aguarde o Maven carregar as dependências e rode a classe principal `Application.java` de cada serviço.
* **Via Terminal:**
```bash
./mvnw spring-boot:run
```

*O Flyway criará as tabelas do banco de dados automaticamente durante a inicialização.*

---

## 📡 Endpoints Principais (Mission Service)

O microsserviço de missões roda por padrão na porta `8080`. Abaixo estão as rotas principais:

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/missoes` | Cria uma nova missão |
| `GET` | `/api/missoes` | Lista todas as missões (Retorna página do Spring) |
| `GET` | `/api/missoes/usuario/{userId}` | Busca missões filtrando pelo ID do usuário |
| `PUT` | `/api/missoes/{id}` | Atualiza dados da missão |
| `DELETE` | `/api/missoes/{id}` | Realiza o soft delete da missão (`deletedAt`) |
| `PATCH` | `/api/missoes/{id}/concluir` | **Conclui a missão e dispara o evento no Kafka** |

### Exemplo de Payload - Criar Missão (`POST`)
```json
{
  "title": "Aprender Clean Architecture e Kafka",
  "category": "ESTUDOS",
  "difficulty": 4,
  "deadline": "2026-12-31T23:59:00"
}
```

---

## 📨 Fluxo Assíncrono e Gamificação (Kafka)

Para garantir que o serviço de Missões não fique acoplado ao serviço de Usuários/Gamificação, utilizamos comunicação assíncrona.

Quando o endpoint `PATCH /api/missoes/{id}/concluir` é chamado com sucesso, o *Mission Service* publica um evento no tópico Kafka chamado `mission-completed-topic`.

**Formato do Evento Trafegado:**
```json
{
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "category": "ESTUDOS",
  "difficulty": 4
}
```

O *Gamification Service* atua como um **Consumer** deste tópico, lendo os dados de dificuldade e categoria para processar a quantidade exata de XP a ser creditada ao perfil do usuário.