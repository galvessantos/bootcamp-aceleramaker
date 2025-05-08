# 📌 Seção 14 – Bancos de Dados Relacionais

Nesta seção explorei os **Bancos de Dados Relacionais**, aprendendo conceitos fundamentais de modelagem, SQL e manipulação de dados estruturados. Os bancos de dados relacionais são essenciais para o armazenamento e gerenciamento de dados em aplicações empresariais, e compreender seu funcionamento é uma habilidade crítica para qualquer desenvolvedor.

---

## 🔹 Conceitos Fundamentais

### 1 – Modelo Relacional
O modelo relacional organiza dados em tabelas (relações) compostas por linhas (tuplas) e colunas (atributos):

- **Tabela**: Estrutura que armazena dados de entidades similares (ex: Clientes, Produtos)
- **Coluna**: Representa um atributo específico da entidade (ex: nome, preço)
- **Linha**: Representa uma instância única da entidade, com valores para cada atributo
- **Chave Primária**: Identificador único para cada registro na tabela
- **Chave Estrangeira**: Referência à chave primária de outra tabela, estabelecendo relacionamentos

### 2 – Relacionamentos
Os dados podem se relacionar de diferentes formas:

- **Um para Um (1:1)**: Cada registro na tabela A corresponde a exatamente um registro na tabela B
- **Um para Muitos (1:N)**: Um registro na tabela A pode corresponder a vários registros na tabela B
- **Muitos para Muitos (N:M)**: Vários registros na tabela A podem corresponder a vários registros na tabela B (implementado com tabela de junção)

### 3 – Normalização
Processo de organizar dados para reduzir redundância e melhorar a integridade:

- **1ª Forma Normal (1FN)**: Eliminar grupos repetitivos, garantir valores atômicos
- **2ª Forma Normal (2FN)**: Satisfazer 1FN e remover dependências parciais
- **3ª Forma Normal (3FN)**: Satisfazer 2FN e remover dependências transitivas

---

## 🔹 SQL - Structured Query Language

Aprendi e pratiquei os principais comandos SQL divididos em categorias:

### DDL (Data Definition Language)
Comandos para definir a estrutura do banco de dados:

```sql
-- Criar tabela
CREATE TABLE clientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    data_nascimento DATE,
    ativo BOOLEAN DEFAULT TRUE
);

-- Alterar tabela
ALTER TABLE clientes ADD COLUMN telefone VARCHAR(20);

-- Remover tabela
DROP TABLE clientes;
```

### DML (Data Manipulation Language)
Comandos para manipular dados nas tabelas:

```sql
-- Inserir dados
INSERT INTO clientes (nome, email, data_nascimento)
VALUES ('João Silva', 'joao@email.com', '1990-05-15');

-- Atualizar dados
UPDATE clientes
SET telefone = '(11) 98765-4321'
WHERE id = 1;

-- Remover dados
DELETE FROM clientes WHERE id = 3;
```

### DQL (Data Query Language)
Comandos para consultar dados:

```sql
-- Consulta simples
SELECT * FROM clientes;

-- Consulta com condições
SELECT nome, email FROM clientes WHERE ativo = TRUE;

-- Consulta com junção de tabelas
SELECT c.nome, p.descricao, p.valor
FROM clientes c
INNER JOIN pedidos pd ON c.id = pd.cliente_id
INNER JOIN pedido_itens pi ON pd.id = pi.pedido_id
INNER JOIN produtos p ON pi.produto_id = p.id;

-- Consulta com agregação
SELECT COUNT(*) as total_clientes FROM clientes;
```

### DCL (Data Control Language) e TCL (Transaction Control Language)
Comandos para controle de acesso e transações:

```sql
-- Conceder privilégios
GRANT SELECT, INSERT ON clientes TO usuario;

-- Revogar privilégios
REVOKE INSERT ON clientes FROM usuario;

-- Iniciar transação
BEGIN TRANSACTION;

-- Confirmar transação
COMMIT;

-- Desfazer transação
ROLLBACK;
```

---

## 🔹 Modelagem de Dados

Pratiquei a modelagem de dados utilizando:

### Modelo Entidade-Relacionamento (ER)
Diagrama conceitual que representa entidades e suas relações:

- **Entidades**: Representadas por retângulos
- **Atributos**: Propriedades das entidades
- **Relacionamentos**: Conexões entre entidades

### Exemplo de Modelo para uma Loja Virtual
```
CLIENTE (id, nome, email, telefone, endereco)
    |
    | 1:N
    v
PEDIDO (id, data, valor_total, status, cliente_id)
    |
    | 1:N
    v
ITEM_PEDIDO (pedido_id, produto_id, quantidade, valor_unitario)
    |
    | N:1
    v
PRODUTO (id, nome, descricao, preco, estoque, categoria_id)
    |
    | N:1
    v
CATEGORIA (id, nome, descricao)
```

---

## 🔹 Projeto Prático

Desenvolvi um sistema de gerenciamento de uma biblioteca como projeto prático:

```sql
-- Criação das tabelas principais
CREATE TABLE autores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(50),
    data_nascimento DATE
);

CREATE TABLE editoras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200),
    telefone VARCHAR(20)
);

CREATE TABLE livros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    ano_publicacao INT,
    isbn VARCHAR(20) UNIQUE,
    quantidade_copias INT DEFAULT 0,
    editora_id INT,
    FOREIGN KEY (editora_id) REFERENCES editoras(id)
);

CREATE TABLE livro_autor (
    livro_id INT,
    autor_id INT,
    PRIMARY KEY (livro_id, autor_id),
    FOREIGN KEY (livro_id) REFERENCES livros(id),
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);

CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telefone VARCHAR(20),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE emprestimos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT,
    livro_id INT,
    data_emprestimo DATE NOT NULL,
    data_devolucao_prevista DATE NOT NULL,
    data_devolucao_efetiva DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (livro_id) REFERENCES livros(id)
);
```

---

## 📂 Documentação e Referências
Para aprofundar o conhecimento em Bancos de Dados Relacionais:
🔗 [MySQL Documentation](https://dev.mysql.com/doc/)
🔗 [PostgreSQL Documentation](https://www.postgresql.org/docs/)
🔗 [SQL Tutorial - W3Schools](https://www.w3schools.com/sql/)
