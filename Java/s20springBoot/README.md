# 📌 Seção 20 – Spring Boot

Nesta seção explorei o **Spring Boot**, um framework que revolucionou o desenvolvimento de aplicações Java, simplificando a configuração e acelerando a criação de aplicações prontas para produção. O Spring Boot elimina grande parte do trabalho de configuração manual através de convenções inteligentes e autoconfiguração.

---

## 🔹 Introdução ao Spring Boot

### 1 – O que é Spring Boot?

O Spring Boot é uma extensão do ecossistema Spring que facilita a criação de aplicações autônomas baseadas em Spring:

- **Convention over Configuration**: Adota convenções sensíveis para reduzir decisões do desenvolvedor
- **Starter Dependencies**: Pacotes de dependências pré-configurados para casos de uso comuns
- **Auto-configuration**: Configura automaticamente beans conforme as dependências presentes no classpath
- **Embedded Servers**: Servidores embutidos (Tomcat, Jetty, Undertow) eliminando a necessidade de deploy externo
- **Production-ready**: Recursos prontos para produção como métricas, health checks e configuração externalizada

### 2 – Criando uma Aplicação Spring Boot

Iniciei um projeto Spring Boot usando o Spring Initializr:

1. Acessei [start.spring.io](https://start.spring.io)
2. Selecionei as opções básicas:
   - Tipo de projeto: Maven
   - Linguagem: Java
   - Versão do Spring Boot: 2.7.x
   - Metadados do projeto: Grupo, Artefato, Nome, Descrição
3. Adicionei as dependências iniciais:
   - Spring Web
   - Spring Data JPA
   - MySQL Driver
   - Lombok
4. Baixei o projeto e importei no meu IDE

### 3 – Estrutura Básica

O projeto gerado seguiu a estrutura padrão Maven com alguns elementos específicos do Spring Boot:

```
minha-aplicacao/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/meudominio/aplicacao/
│   │   │       └── MinhaAplicacaoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/meudominio/aplicacao/
│               └── MinhaAplicacaoApplicationTests.java
├── pom.xml
└── README.md
```

A classe principal contém a anotação `@SpringBootApplication` e o método `main`:

```java
@SpringBootApplication
public class MinhaAplicacaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinhaAplicacaoApplication.class, args);
    }
}
```

---

## 🔹 Recursos Essenciais

### 1 – Spring Web e MVC

Criei controladores REST usando as anotações do Spring MVC:

```java
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.buscarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody @Valid Produto produto) {
        Produto produtoSalvo = produtoService.salvar(produto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).body(produtoSalvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, 
                                             @RequestBody @Valid Produto produto) {
        if (!produtoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        produto.setId(id);
        Produto produtoAtualizado = produtoService.salvar(produto);
        return ResponseEntity.ok(produtoAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!produtoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoService.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 2 – Spring Data JPA

Para persistência de dados, utilizei o Spring Data JPA:

```java
// Entidade JPA
@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String nome;
    
    @NotBlank
    @Size(max = 500)
    private String descricao;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;
    
    @Min(0)
    private Integer estoque;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}

// Repository
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContaining(String nome);
    List<Produto> findByPrecoLessThanEqual(BigDecimal precoMaximo);
    List<Produto> findByCategoriaId(Long categoriaId);
    
    @Query("SELECT p FROM Produto p WHERE p.estoque > 0 ORDER BY p.nome")
    List<Produto> findProdutosEmEstoque();
}
```

### 3 – Configuração via application.properties/yml

Configurei a aplicação através do arquivo `application.properties`:

```properties
# Configuração do Servidor
server.port=8080
server.servlet.context-path=/minha-aplicacao

# Configuração do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/minha_base?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuração JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Configuração de Logging
logging.level.org.springframework=INFO
logging.level.com.meudominio.aplicacao=DEBUG

# Configuração de Internacionalização
spring.messages.basename=messages
spring.messages.encoding=UTF-8
```

Alternativamente, usei o formato YAML para maior legibilidade:

```yaml
server:
  port: 8080
  servlet:
    context-path: /minha-aplicacao

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/minha_base?useSSL=false&serverTimezone=UTC
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
  
  messages:
    basename: messages
    encoding: UTF-8

logging:
  level:
    org.springframework: INFO
    com.meudominio.aplicacao: DEBUG
```

---

## 🔹 Arquitetura em Camadas

Implementei a aplicação seguindo uma arquitetura em camadas bem definida:

### 1 – Camada de Apresentação (Controllers)

Responsável por receber e responder às requisições:

```java
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;
    
    // Endpoints REST
}
```

### 2 – Camada de Serviço

Implementa a lógica de negócios:

```java
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Override
    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }
    
    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }
    
    @Override
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    @Override
    public void removerPorId(Long id) {
        produtoRepository.deleteById(id);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return produtoRepository.existsById(id);
    }
    
    @Override
    public List<Produto> buscarPorPrecoMaximo(BigDecimal precoMaximo) {
        return produtoRepository.findByPrecoLessThanEqual(precoMaximo);
    }
    
    @Override
    public List<Produto> buscarProdutosEmEstoque() {
        return produtoRepository.findProdutosEmEstoque();
    }
}
```

### 3 – Camada de Persistência (Repositories)

Interação com o banco de dados:

```java
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNome(String nome);
}
```

### 4 – DTOs (Data Transfer Objects)

Utilizei DTOs para transferência de dados entre camadas:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    
    @NotBlank
    private String nome;
    
    @NotBlank
    private String descricao;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;
    
    private Integer estoque;
    
    @NotNull
    private Long categoriaId;
}
```

---

## 🔹 Recursos Avançados

### 1 – Tratamento de Exceções

Implementei um manipulador global de exceções:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                LocalDateTime.now(),
                errors);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor",
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### 2 – Segurança com Spring Security

Adicionei segurança à aplicação:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .httpBasic();
    }
}
```

### 3 – Testes Automatizados

Criei testes unitários e de integração:

```java
// Teste Unitário
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    
    @Mock
    private ProdutoRepository produtoRepository;
    
    @InjectMocks
    private ProdutoServiceImpl produtoService;
    
    @Test
    void deveBuscarTodosProdutos() {
        // Dado
        List<Produto> produtos = Arrays.asList(
            new Produto(1L, "Produto 1", "Descrição 1", new BigDecimal("10.00"), 5, null),
            new Produto(2L, "Produto 2", "Descrição 2", new BigDecimal("20.00"), 10, null)
        );
        when(produtoRepository.findAll()).thenReturn(produtos);
        
        // Quando
        List<Produto> resultado = produtoService.buscarTodos();
        
        // Então
        assertEquals(2, resultado.size());
        verify(produtoRepository).findAll();
    }
}

// Teste de Integração
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void deveListarTodosProdutos() throws Exception {
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }
    
    @Test
    void deveCriarProduto() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(
                null, "Novo Produto", "Descrição do Novo Produto", 
                new BigDecimal("15.99"), 20, 1L);
        
        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Novo Produto"));
    }
}
```

---

## 🔹 Spring Boot Actuator

Utilizei o Spring Boot Actuator para monitoramento e gerenciamento:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```properties
# Configuração do Actuator
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=when_authorized
management.info.env.enabled=true

# Informações personalizadas
info.app.name=@project.name@
info.app.description=@project.description@
info.app.version=@project.version@
```

---

## 🔹 Profiles

Implementei configurações específicas para diferentes ambientes:

```java
@Configuration
@Profile("dev")
public class DevConfig {
    
    @Bean
    public CommandLineRunner dataLoader(ProdutoRepository produtoRepository,
                                       CategoriaRepository categoriaRepository) {
        return args -> {
            // Carregar dados de exemplo para desenvolvimento
            Categoria categoria = new Categoria(null, "Eletrônicos");
            categoriaRepository.save(categoria);
            
            Produto produto = new Produto(null, "Smartphone", "Smartphone de última geração",
                    new BigDecimal("999.99"), 10, categoria);
            produtoRepository.save(produto);
        };
    }
}
```

```properties
# application-dev.properties
spring.jpa.hibernate.ddl-auto=create-drop
```

```properties
# application-prod.properties
spring.jpa.hibernate.ddl-auto=validate
```

Para ativar um profile específico:

```bash
java -jar minha-aplicacao.jar --spring.profiles.active=prod
```

---

## 🔹 Spring Boot DevTools

Utilizei o Spring Boot DevTools para aumentar a produtividade:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

Benefícios do DevTools:

- Reinicialização automática da aplicação quando os arquivos são alterados
- LiveReload para atualização automática do navegador
- Cache de templates desabilitado durante o desenvolvimento
- Configurações de depuração aprimoradas

---

## 🔹 Spring Boot Starter Web

O starter web inclui tudo necessário para desenvolvimento web:

- Spring MVC para controladores REST
- Embedded Tomcat como servidor padrão
- Jackson para serialização/desserialização JSON

```java
@GetMapping("/hello")
@ResponseBody
public String hello() {
    return "Hello, Spring Boot!";
}
```

---

## 📂 Documentação Oficial
Para aprofundar o conhecimento em Spring Boot:
🔗 [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
🔗 [Spring Boot Guides](https://spring.io/guides)
🔗 [Spring Boot API](https://docs.spring.io/spring-boot/docs/current/api/)
