# 📌 Seção 19 – Conceitos Web

Nesta seção explorei os **Conceitos Web** fundamentais para o desenvolvimento de aplicações web em Java. Compreender esses conceitos é essencial para qualquer desenvolvedor que deseje criar aplicações web robustas, escaláveis e seguras.

---

## 🔹 Fundamentos da Web

### 1 – Arquitetura Cliente-Servidor

A web funciona baseada no modelo cliente-servidor:

- **Cliente**: Navegador que solicita recursos (HTML, CSS, JavaScript, imagens)
- **Servidor**: Computador que hospeda a aplicação e fornece os recursos solicitados
- **Requisição-Resposta**: Padrão de comunicação onde o cliente faz uma solicitação e o servidor envia uma resposta

### 2 – Protocolos

Principais protocolos que sustentam a web:

- **HTTP (Hypertext Transfer Protocol)**: Protocolo de comunicação para transferência de dados na web
- **HTTPS**: Versão segura do HTTP, com criptografia
- **WebSocket**: Protocolo para comunicação bidirecional e em tempo real
- **FTP**: Protocolo para transferência de arquivos

### 3 – Métodos HTTP

Os métodos HTTP definem as ações a serem realizadas nos recursos:

- **GET**: Solicita um recurso
- **POST**: Envia dados para processamento
- **PUT**: Atualiza um recurso específico
- **DELETE**: Remove um recurso
- **PATCH**: Atualiza parcialmente um recurso
- **OPTIONS**: Retorna os métodos HTTP suportados
- **HEAD**: Similar ao GET, mas sem retornar o corpo da resposta

---

## 🔹 Front-end vs Back-end

### 1 – Front-end

O front-end é a parte visível da aplicação:

- **HTML**: Estrutura do conteúdo
- **CSS**: Estilização e layout
- **JavaScript**: Interatividade e comportamento
- **Frameworks**: React, Angular, Vue.js

### 2 – Back-end

O back-end lida com a lógica de negócios e armazenamento:

- **Linguagens**: Java, Python, Node.js, PHP
- **Frameworks Java**: Spring, Jakarta EE
- **Bancos de Dados**: MySQL, PostgreSQL, MongoDB
- **APIs**: Exposição de serviços para consumo pelo front-end

### 3 – Comunicação

Formas comuns de comunicação entre front-end e back-end:

- **REST (Representational State Transfer)**: Arquitetura para criação de APIs
- **GraphQL**: Linguagem de consulta para APIs
- **SOAP**: Protocolo para troca de informações estruturadas

---

## 🔹 Java Web

### 1 – Servlets

Servlets são componentes Java que processam requisições e geram respostas:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Hello, World!</h1>");
        out.println("</body></html>");
    }
}
```

### 2 – JSP (JavaServer Pages)

JSP permite criar conteúdo dinâmico combinando HTML e Java:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Exemplo JSP</title>
</head>
<body>
    <h1>Bem-vindo, <%= request.getParameter("nome") %></h1>
    
    <% 
    String mensagem = "Esta é uma página JSP dinâmica.";
    out.println(mensagem);
    %>
    
    <c:forEach var="i" begin="1" end="5">
        <p>Item ${i}</p>
    </c:forEach>
</body>
</html>
```

### 3 – JSTL (JavaServer Pages Standard Tag Library)

JSTL fornece tags para tarefas comuns em JSPs:

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Lista de Usuários</h2>
<ul>
    <c:forEach var="usuario" items="${usuarios}">
        <li>${usuario.nome} - ${usuario.email}</li>
    </c:forEach>
</ul>

<c:if test="${not empty mensagem}">
    <div class="alert">${mensagem}</div>
</c:if>
```

---

## 🔹 APIs RESTful

### 1 – Princípios REST

REST é um estilo arquitetural para sistemas distribuídos:

- **Recursos**: Identificados por URIs (ex: /usuarios/123)
- **Representações**: JSON, XML, HTML
- **Operações**: Métodos HTTP (GET, POST, PUT, DELETE)
- **Stateless**: Cada requisição contém toda informação necessária
- **Interface Uniforme**: Padrão consistente para interação com recursos

### 2 – Exemplo de API REST em Java

Implementação de uma API REST simples:

```java
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.save(usuario);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoUsuario.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoUsuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioAtualizado = usuarioService.update(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 🔹 Estrutura de uma Aplicação Web

### 1 – Arquitetura MVC (Model-View-Controller)

O padrão MVC organiza a aplicação em camadas:

- **Model**: Dados e regras de negócio
- **View**: Interface com o usuário
- **Controller**: Coordena as interações entre Model e View

```java
// Model
public class Produto {
    private Long id;
    private String nome;
    private BigDecimal preco;
    
    // Getters e Setters
}

// Controller
@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService service;
    
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", service.findAll());
        return "produtos/lista";
    }
    
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/form";
    }
    
    @PostMapping
    public String salvar(@ModelAttribute Produto produto) {
        service.save(produto);
        return "redirect:/produtos";
    }
}

// View (produtos/lista.jsp)
<html>
<body>
    <h1>Lista de Produtos</h1>
    <table>
        <c:forEach var="produto" items="${produtos}">
            <tr>
                <td>${produto.nome}</td>
                <td>${produto.preco}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
```

### 2 – Segurança Web

Implementação de segurança básica:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/css/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
    }
}
```

---

## 🔹 Desenvolvimento Web Moderno

### 1 – Single Page Applications (SPAs)

SPAs são aplicações que carregam uma única página HTML e atualizam dinamicamente:

- **Frameworks**: React, Angular, Vue.js
- **Benefícios**: Experiência de usuário mais fluida, menos tráfego de rede
- **Integração com Back-end**: APIs REST, GraphQL

### 2 – Progressive Web Apps (PWAs)

PWAs combinam o melhor da web e de aplicativos móveis:

- **Características**: Responsivos, conectividade independente, instaláveis
- **Service Workers**: Scripts que rodam em segundo plano
- **Web App Manifest**: Arquivo JSON que define aparência da aplicação

### 3 – Microsserviços

Arquitetura que divide a aplicação em serviços independentes:

- **Vantagens**: Escalabilidade, resiliência, entrega contínua
- **Desafios**: Complexidade distribuída, gerenciamento de dados
- **Tecnologias Java**: Spring Cloud, Quarkus, Micronaut

---

## 📂 Documentação e Referências
Para aprofundar os conhecimentos em desenvolvimento web com Java:
🔗 [Spring Framework](https://spring.io/projects/spring-framework)
🔗 [Jakarta EE](https://jakarta.ee/specifications/platform/)
🔗 [MDN Web Docs](https://developer.mozilla.org/pt-BR/docs/Web)
