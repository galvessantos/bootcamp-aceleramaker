# 📌 Seção 16 – JPA (Java Persistence API)

Nesta seção explorei a **JPA (Java Persistence API)**, uma especificação que simplifica o desenvolvimento de aplicações Java que interagem com bancos de dados relacionais, fornecendo um framework para mapeamento objeto-relacional (ORM). Com JPA, pude me concentrar na modelagem de objetos sem me preocupar com os detalhes de baixo nível do acesso ao banco de dados.

---

## 🔹 Conceitos Fundamentais

### 1 – JPA vs JDBC

Enquanto o JDBC oferece uma API de baixo nível para interagir com bancos de dados, a JPA trabalha em um nível mais alto de abstração:

```
JDBC:
- Requer SQL manual
- Mapeamento manual entre resultados e objetos
- Controle manual de transações
- Código mais verboso

JPA:
- Queries automáticas para operações CRUD
- Mapeamento automático entre tabelas e objetos
- Gerenciamento de transações simplificado
- Código mais limpo e focado no domínio
```

### 2 – Implementações da JPA

JPA é apenas uma especificação, e existem várias implementações disponíveis:

- **Hibernate**: A mais popular e completa
- **EclipseLink**: Implementação de referência da JPA
- **OpenJPA**: Implementação da Apache

Para o projeto, utilizei o Hibernate como implementação da JPA:

```xml
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.6.5.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-entitymanager</artifactId>
    <version>5.6.5.Final</version>
</dependency>
```

---

## 🔹 Configuração da JPA

### 1 – Arquivo persistence.xml

Configurei a JPA através do arquivo `persistence.xml` na pasta `META-INF/`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
             http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd"
             version="2.2">
    <persistence-unit name="exemplo-jpa" transaction-type="RESOURCE_LOCAL">
        <properties>
            <!-- Configurações da conexão com o banco de dados -->
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/exemplo_jpa?useSSL=false&amp;serverTimezone=UTC" />
            <property name="javax.persistence.jdbc.user" value="root" />
            <property name="javax.persistence.jdbc.password" value="root" />

            <!-- Configurações do Hibernate -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect" />
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
            <property name="hibernate.hbm2ddl.auto" value="update" />
        </properties>
    </persistence-unit>
</persistence>
```

### 2 – EntityManagerFactory e EntityManager

Implementei a criação de um EntityManager para interagir com o banco de dados:

```java
public class JPAUtil {
    private static final EntityManagerFactory FACTORY = 
            Persistence.createEntityManagerFactory("exemplo-jpa");
    
    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
```

---

## 🔹 Mapeamento Objeto-Relacional

### 1 – Anotações JPA

Utilizei anotações para mapear classes Java para tabelas do banco de dados:

```java
@Entity
@Table(name = "produtos")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal preco;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    // Getters e Setters
}
```

### 2 – Relacionamentos entre Entidades

Implementei diferentes tipos de relacionamentos entre entidades:

#### Um para Um (1:1)

```java
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", unique = true)
    private Endereco endereco;
    
    // Getters e Setters
}

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    
    // Getters e Setters
}
```

#### Um para Muitos (1:N)

```java
@Entity
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios = new ArrayList<>();
    
    // Getters e Setters
}

@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;
    
    // Getters e Setters
}
```

#### Muitos para Muitos (N:M)

```java
@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @ManyToMany
    @JoinTable(
        name = "curso_aluno",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos = new ArrayList<>();
    
    // Getters e Setters
}

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @ManyToMany(mappedBy = "alunos")
    private List<Curso> cursos = new ArrayList<>();
    
    // Getters e Setters
}
```

---

## 🔹 Operações CRUD com JPA

### 1 – Create (Criar)

```java
public void cadastrarProduto(Produto produto) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        em.persist(produto);
        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        throw e;
    } finally {
        em.close();
    }
}
```

### 2 – Read (Ler)

```java
public Produto buscarProdutoPorId(Long id) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        return em.find(Produto.class, id);
    } finally {
        em.close();
    }
}

public List<Produto> listarTodosProdutos() {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String jpql = "SELECT p FROM Produto p";
        return em.createQuery(jpql, Produto.class).getResultList();
    } finally {
        em.close();
    }
}
```

### 3 – Update (Atualizar)

```java
public void atualizarProduto(Produto produto) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        em.merge(produto);
        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        throw e;
    } finally {
        em.close();
    }
}
```

### 4 – Delete (Remover)

```java
public void removerProduto(Long id) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();
        Produto produto = em.find(Produto.class, id);
        if (produto != null) {
            em.remove(produto);
        }
        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        throw e;
    } finally {
        em.close();
    }
}
```

---

## 🔹 JPQL (Java Persistence Query Language)

Utilizei JPQL para criar consultas mais complexas:

```java
public List<Produto> buscarProdutosPorCategoria(Categoria categoria) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String jpql = "SELECT p FROM Produto p WHERE p.categoria = :categoria";
        return em.createQuery(jpql, Produto.class)
                .setParameter("categoria", categoria)
                .getResultList();
    } finally {
        em.close();
    }
}

public List<Produto> buscarProdutosComPrecoMaiorQue(BigDecimal preco) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String jpql = "SELECT p FROM Produto p WHERE p.preco > :preco";
        return em.createQuery(jpql, Produto.class)
                .setParameter("preco", preco)
                .getResultList();
    } finally {
        em.close();
    }
}

public List<Object[]> buscarProdutosECategorias() {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String jpql = "SELECT p.nome, p.categoria FROM Produto p";
        return em.createQuery(jpql, Object[].class).getResultList();
    } finally {
        em.close();
    }
}
```

---

## 🔹 Consultas Nativas SQL

Quando precisei de recursos específicos do banco de dados, utilizei SQL nativo:

```java
public List<Produto> buscarComSQLNativo() {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        String sql = "SELECT * FROM produtos WHERE preco > 100.0";
        return em.createNativeQuery(sql, Produto.class).getResultList();
    } finally {
        em.close();
    }
}
```

---

## 🔹 Ciclo de Vida das Entidades

Entendi e apliquei o ciclo de vida das entidades JPA:

1. **Transient**: Objeto recém criado, não associado a um EntityManager
2. **Managed**: Objeto gerenciado pelo EntityManager, alterações são sincronizadas
3. **Detached**: Objeto que já foi gerenciado, mas não está mais associado ao EntityManager
4. **Removed**: Objeto marcado para remoção

```java
// Transient
Produto produto = new Produto();
produto.setNome("Notebook");
produto.setPreco(new BigDecimal("4500.00"));

EntityManager em = JPAUtil.getEntityManager();
em.getTransaction().begin();

// Managed (após persist)
em.persist(produto);

// Alterações são rastreadas automaticamente
produto.setPreco(new BigDecimal("4300.00"));

em.getTransaction().commit();

// Detached (após fechar o EntityManager)
em.close();

// Para tornar um objeto detached em managed novamente
EntityManager em2 = JPAUtil.getEntityManager();
em2.getTransaction().begin();
produto = em2.merge(produto);
em2.getTransaction().commit();

// Removed
em2.getTransaction().begin();
em2.remove(produto);
em2.getTransaction().commit();
```

---

## 🔹 Repositório Genérico

Implementei um repositório genérico para reduzir a duplicação de código:

```java
public class GenericRepository<T> {
    private Class<T> entityClass;
    
    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public void salvar(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public T buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }
    
    public List<T> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "FROM " + entityClass.getSimpleName();
            return em.createQuery(jpql, entityClass).getResultList();
        } finally {
            em.close();
        }
    }
    
    public void atualizar(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public void remover(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
```

---

## 📂 Documentação Oficial
Para aprofundar o conhecimento em JPA:
🔗 [JPA Specification](https://javaee.github.io/javaee-spec/javadocs/javax/persistence/package-summary.html)
🔗 [Hibernate Documentation](https://hibernate.org/orm/documentation/5.6/)
