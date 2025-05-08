# 📌 Seção 15 – JDBC (Java Database Connectivity)

Nesta seção explorei o **JDBC (Java Database Connectivity)**, a API padrão do Java para interação com bancos de dados relacionais. O JDBC fornece uma interface uniforme para conectar, consultar e manipular diferentes sistemas de banco de dados, permitindo que o código Java interaja com MySQL, PostgreSQL, Oracle, SQLite e outros SGBDs.

---

## 🔹 Conceitos Fundamentais do JDBC

### 1 – Arquitetura JDBC
O JDBC é composto por diferentes componentes que trabalham juntos:

- **Aplicação Java**: O código que escrevi para interagir com o banco de dados
- **API JDBC**: Interfaces e classes definidas no pacote `java.sql`
- **Driver Manager**: Gerencia os drivers JDBC disponíveis
- **Driver JDBC**: Implementação específica para cada banco de dados
- **Banco de Dados**: O SGBD que armazena os dados

### 2 – Principais Classes e Interfaces
Aprendi sobre as principais classes do JDBC:

- **`DriverManager`**: Gerencia drivers e estabelece conexões
- **`Connection`**: Representa uma conexão com o banco de dados
- **`Statement`**: Executa consultas SQL simples
- **`PreparedStatement`**: Executa consultas SQL parametrizadas
- **`CallableStatement`**: Executa procedimentos armazenados
- **`ResultSet`**: Contém os resultados de uma consulta

---

## 🔹 Conectando ao Banco de Dados

### Configuração do Driver
Adicionar dependência do driver JDBC ao projeto:

```xml
<!-- Para MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>
```

### Estabelecendo a Conexão
Implementar a conexão com o banco de dados usando o DriverManager:

```java
public Connection getConnection() throws SQLException {
    final String url = "jdbc:mysql://localhost:3306/minha_base";
    final String usuario = "root";
    final String senha = "senha123";
    
    return DriverManager.getConnection(url, usuario, senha);
}
```

### Criando uma Classe de Conexão
Desenvolver uma classe utilitária para gerenciar conexões:

```java
public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/minha_base";
    private static final String USUARIO = "root";
    private static final String SENHA = "senha123";
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeConnection(Connection conn, Statement stmt) {
        closeConnection(conn);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        closeConnection(conn, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 🔹 Operações CRUD com JDBC

### Create (Inserir)
Implementar a inserção de dados usando PreparedStatement:

```java
public void inserirProduto(Produto p) {
    String sql = "INSERT INTO produtos (nome, descricao, preco, quantidade) VALUES (?, ?, ?, ?)";
    
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = ConnectionFactory.getConnection();
        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getDescricao());
        stmt.setDouble(3, p.getPreco());
        stmt.setInt(4, p.getQuantidade());
        
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao inserir produto", e);
    } finally {
        ConnectionFactory.closeConnection(conn, stmt);
    }
}
```

### Read (Consultar)
Implementar a consulta de dados:

```java
public List<Produto> listarTodos() {
    String sql = "SELECT * FROM produtos";
    
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    List<Produto> produtos = new ArrayList<>();
    
    try {
        conn = ConnectionFactory.getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        
        while (rs.next()) {
            Produto p = new Produto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setDescricao(rs.getString("descricao"));
            p.setPreco(rs.getDouble("preco"));
            p.setQuantidade(rs.getInt("quantidade"));
            
            produtos.add(p);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar produtos", e);
    } finally {
        ConnectionFactory.closeConnection(conn, stmt, rs);
    }
    
    return produtos;
}

public Produto buscarPorId(int id) {
    String sql = "SELECT * FROM produtos WHERE id = ?";
    
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Produto p = null;
    
    try {
        conn = ConnectionFactory.getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            p = new Produto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setDescricao(rs.getString("descricao"));
            p.setPreco(rs.getDouble("preco"));
            p.setQuantidade(rs.getInt("quantidade"));
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar produto", e);
    } finally {
        ConnectionFactory.closeConnection(conn, stmt, rs);
    }
    
    return p;
}
```

### Update (Atualizar)
Implementar a atualização de dados:

```java
public void atualizar(Produto p) {
    String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, quantidade = ? WHERE id = ?";
    
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = ConnectionFactory.getConnection();
        stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getDescricao());
        stmt.setDouble(3, p.getPreco());
        stmt.setInt(4, p.getQuantidade());
        stmt.setInt(5, p.getId());
        
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao atualizar produto", e);
    } finally {
        ConnectionFactory.closeConnection(conn, stmt);
    }
}
```

### Delete (Remover)
Implementar a remoção de dados:

```java
public void excluir(int id) {
    String sql = "DELETE FROM produtos WHERE id = ?";
    
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = ConnectionFactory.getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao excluir produto", e);
    } finally {
        ConnectionFactory.closeConnection(conn, stmt);
    }
}
```

---

## 🔹 Transações com JDBC

Implementar o controle de transações para garantir a consistência dos dados:

```java
public void transferir(int contaOrigem, int contaDestino, double valor) {
    String sqlDebito = "UPDATE contas SET saldo = saldo - ? WHERE id = ?";
    String sqlCredito = "UPDATE contas SET saldo = saldo + ? WHERE id = ?";
    
    Connection conn = null;
    PreparedStatement stmtDebito = null;
    PreparedStatement stmtCredito = null;
    
    try {
        conn = ConnectionFactory.getConnection();
        
        // Desabilita o autocommit
        conn.setAutoCommit(false);
        
        // Debita valor da conta origem
        stmtDebito = conn.prepareStatement(sqlDebito);
        stmtDebito.setDouble(1, valor);
        stmtDebito.setInt(2, contaOrigem);
        stmtDebito.executeUpdate();
        
        // Simula erro para testar o rollback
        if (valor > 1000) {
            throw new SQLException("Valor muito alto para transferência!");
        }
        
        // Credita valor na conta destino
        stmtCredito = conn.prepareStatement(sqlCredito);
        stmtCredito.setDouble(1, valor);
        stmtCredito.setInt(2, contaDestino);
        stmtCredito.executeUpdate();
        
        // Confirma a transação
        conn.commit();
    } catch (SQLException e) {
        try {
            // Desfaz todas as operações em caso de erro
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException("Erro ao transferir: " + e.getMessage(), e);
    } finally {
        try {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.closeConnection(conn, stmtDebito);
        if (stmtCredito != null) {
            try {
                stmtCredito.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

---

## 🔹 Padrão DAO (Data Access Object)

Implementar o padrão DAO para separar a lógica de acesso a dados do restante da aplicação:

```java
// Interface DAO
public interface ProdutoDAO {
    void inserir(Produto produto);
    void atualizar(Produto produto);
    void excluir(int id);
    Produto buscarPorId(int id);
    List<Produto> listarTodos();
}

// Implementação do DAO
public class ProdutoDAOImpl implements ProdutoDAO {
    // Implementação dos métodos CRUD usando JDBC...
}

// Classe de serviço que utiliza o DAO
public class ProdutoService {
    private ProdutoDAO dao;
    
    public ProdutoService() {
        this.dao = new ProdutoDAOImpl();
    }
    
    public void cadastrarProduto(Produto p) {
        // Validações e regras de negócio...
        dao.inserir(p);
    }
    
    // Outros métodos...
}
```

---

## 🔹 Boas Práticas e Desafios

### Boas Práticas
Durante o desenvolvimento com JDBC, aprendi algumas boas práticas:

1. **Sempre fechar recursos**: Utilizar blocos try-with-resources ou fechar manualmente conexões, statements e resultsets
2. **Usar PreparedStatement**: Previne SQL Injection e melhora o desempenho
3. **Controlar transações**: Garantir a consistência dos dados em operações relacionadas
4. **Utilizar padrão DAO**: Separar a lógica de acesso a dados do restante da aplicação
5. **Centralizar a criação de conexões**: Utilizar uma factory para gerenciar conexões

---

## 📂 Documentação Oficial
Para aprofundar o conhecimento em JDBC:
🔗 [Java JDBC API](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)
🔗 [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
