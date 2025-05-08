# 📌 Seção 21 – Bancos de Dados NoSQL

Nesta seção explorei os **Bancos de Dados NoSQL** (Not Only SQL), que oferecem abordagens alternativas ao modelo relacional tradicional para armazenamento e recuperação de dados. Os bancos NoSQL são especialmente valiosos para casos de uso que envolvem grandes volumes de dados, escalabilidade horizontal e esquemas flexíveis.

---

## 🔹 Conceitos Fundamentais

### 1 – O que são Bancos NoSQL?

Bancos NoSQL são sistemas de gerenciamento de dados que se afastam do modelo relacional:

- **Flexibilidade de Esquema**: Permitem armazenar dados sem um esquema rígido predefinido
- **Escalabilidade Horizontal**: Facilidade para distribuir dados em múltiplos servidores
- **Alta Disponibilidade**: Projetados para operação contínua, mesmo em falhas de hardware
- **Otimização para Casos Específicos**: Diferentes tipos de bancos NoSQL para diferentes necessidades

### 2 – Classificação dos Bancos NoSQL

Os bancos NoSQL são tipicamente classificados em quatro categorias principais:

- **Bancos de Documentos**: Armazenam dados em documentos semelhantes a JSON (MongoDB, CouchDB)
- **Bancos de Colunas**: Armazenam dados em colunas em vez de linhas (Cassandra, HBase)
- **Bancos de Chave-Valor**: Simples pares de chave-valor, ótimos para cache (Redis, DynamoDB)
- **Bancos de Grafos**: Otimizados para dados interconectados (Neo4j, JanusGraph)

### 3 – SQL vs NoSQL

Comparação entre os paradigmas:

```
SQL (Relacional):
- Esquema rígido e predefinido
- Relacionamentos explícitos (chaves estrangeiras)
- Garantias ACID (Atomicidade, Consistência, Isolamento, Durabilidade)
- Escalabilidade vertical (upgrade de hardware)
- Consultas poderosas com SQL

NoSQL:
- Esquema flexível ou sem esquema
- Relacionamentos implícitos (documentos aninhados, referências)
- Garantias BASE (Basically Available, Soft state, Eventually consistent)
- Escalabilidade horizontal (adicionar mais servidores)
- APIs específicas para consultas
```

---

## 🔹 MongoDB - Banco de Documentos

### 1 – Conceitos Básicos do MongoDB

Trabalhei com os conceitos fundamentais do MongoDB:

- **Documento**: Unidade básica de dados (similar a JSON/BSON)
- **Coleção**: Grupo de documentos (equivalente a uma tabela em SQL)
- **Banco de Dados**: Contêiner para coleções
- **_id**: Identificador único gerado automaticamente para cada documento

### 2 – CRUD em MongoDB

Implementei operações básicas de CRUD:

```javascript
// Create (Inserir)
db.produtos.insertOne({
  nome: "Smartphone XYZ",
  preco: 999.99,
  especificacoes: {
    memoria: "6GB",
    armazenamento: "128GB",
    processador: "Octa-core"
  },
  cores: ["Preto", "Branco", "Azul"],
  disponivel: true
})

// Read (Consultar)
db.produtos.find({ preco: { $lt: 1000 } })
db.produtos.findOne({ nome: "Smartphone XYZ" })

// Update (Atualizar)
db.produtos.updateOne(
  { nome: "Smartphone XYZ" },
  { $set: { preco: 899.99, promocao: true } }
)

// Delete (Remover)
db.produtos.deleteOne({ nome: "Smartphone XYZ" })
```

### 3 – MongoDB com Java

Integrei o MongoDB em aplicações Java:

```java
// Dependência Maven
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

// Modelo
@Document(collection = "produtos")
public class Produto {
    
    @Id
    private String id;
    
    private String nome;
    private BigDecimal preco;
    private Map<String, String> especificacoes;
    private List<String> cores;
    private boolean disponivel;
    
    // Getters e Setters
}

// Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    List<Produto> findByPrecoLessThan(BigDecimal preco);
    List<Produto> findByCoresContaining(String cor);
    Optional<Produto> findByNome(String nome);
}

// Service
@Service
public class ProdutoServiceImpl implements ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Override
    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }
    
    @Override
    public Optional<Produto> buscarPorId(String id) {
        return produtoRepository.findById(id);
    }
    
    @Override
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    @Override
    public void remover(String id) {
        produtoRepository.deleteById(id);
    }
}

// Controller
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
    public ResponseEntity<Produto> buscarPorId(@PathVariable String id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable String id) {
        produtoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
```

## 🔹 Uso Híbrido: SQL + NoSQL

### 1 – Estratégias de Uso Combinado

Implementei estratégias para usar SQL e NoSQL de forma complementar:

- **Persistência Poliglota**: Usar diferentes bancos para diferentes tipos de dados
- **Padrão CQRS**: Separar operações de leitura e escrita em bancos diferentes
- **Caching com NoSQL**: Usar Redis para cache de dados relacionais
- **Dados Estruturados vs Não Estruturados**: SQL para dados estruturados, NoSQL para semi-estruturados

### 2 – Exemplo de Arquitetura Híbrida

```java
@Service
public class ProdutoServiceImpl implements ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository; // JPA/SQL
    
    @Autowired
    private ProdutoDocumentRepository produtoDocumentRepository; // MongoDB
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // Redis
    
    @Override
    public Produto salvar(Produto produto) {
        // Salvar no banco relacional
        Produto produtoSalvo = produtoRepository.save(produto);
        
        // Salvar versão documento no MongoDB para buscas mais complexas
        ProdutoDocument documento = converterParaDocument(produtoSalvo);
        produtoDocumentRepository.save(documento);
        
        // Invalidar cache
        redisTemplate.delete("produto:" + produto.getId());
        
        return produtoSalvo;
    }
    
    @Override
    public Optional<Produto> buscarPorId(Long id) {
        // Verificar cache primeiro
        String chaveCache = "produto:" + id;
        Produto produtoCache = (Produto) redisTemplate.opsForValue().get(chaveCache);
        
        if (produtoCache != null) {
            return Optional.of(produtoCache);
        }
        
        // Buscar no banco relacional
        Optional<Produto> resultado = produtoRepository.findById(id);
        
        // Atualizar cache se encontrado
        resultado.ifPresent(p -> redisTemplate.opsForValue().set(chaveCache, p, 1, TimeUnit.HOURS));
        
        return resultado;
    }
    
    @Override
    public List<ProdutoDTO> buscarPorPropriedadesAvancadas(Map<String, Object> filtros) {
        // Usar MongoDB para consultas complexas em dados não estruturados
        List<ProdutoDocument> documentos = produtoDocumentRepository.findByPropriedades(filtros);
        return documentos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
}
```

---

## 🔹 Considerações sobre NoSQL

### 1 – Quando Usar NoSQL

Identifiquei cenários onde NoSQL é mais adequado:

- **Big Data**: Grandes volumes de dados não estruturados
- **Alta Escala**: Necessidade de escalabilidade horizontal
- **Esquema Evolutivo**: Estrutura de dados em constante mudança
- **Desenvolvimento Ágil**: Necessidade de iterar rapidamente sem alterações de esquema
- **Disponibilidade**: Sistemas que não podem ficar offline
- **Dados Geográficos**: Necessidade de consultas geoespaciais
- **Dados em Tempo Real**: Dados que mudam com alta frequência

### 2 – Desafios NoSQL

Identifiquei os principais desafios ao trabalhar com NoSQL:

- **Consistência**: A maioria dos NoSQL sacrifica consistência por disponibilidade
- **Transações**: Suporte limitado para transações ACID
- **Maturidade**: Algumas soluções ainda estão em desenvolvimento
- **Conhecimento**: Curva de aprendizado para diferentes bancos
- **Ferramentas**: Ecossistema menos maduro de ferramentas de administração
- **Consultas Complexas**: Algumas consultas são mais difíceis sem SQL completo

### 3 – Teorema CAP

Compreendi o Teorema CAP (Consistency, Availability, Partition Tolerance):

- **Consistência**: Todos os nós veem os mesmos dados ao mesmo tempo
- **Disponibilidade**: Todo nó sempre responde às solicitações
- **Tolerância a Partição**: O sistema continua funcionando mesmo com falhas de comunicação

Segundo o teorema, em um sistema distribuído, é possível garantir apenas duas das três propriedades simultaneamente:

- **CA**: Consistência e Disponibilidade (MySQL, PostgreSQL)
- **CP**: Consistência e Tolerância a Partição (MongoDB, HBase)
- **AP**: Disponibilidade e Tolerância a Partição (Cassandra, CouchDB)

---

## 📂 Documentação e Referências
Para aprofundar os conhecimentos em bancos NoSQL:
🔗 [MongoDB Documentation](https://docs.mongodb.com/)
🔗 [Redis Documentation](https://redis.io/documentation)
🔗 [Apache Cassandra Documentation](https://cassandra.apache.org/doc/latest/)
🔗 [Neo4j Documentation](https://neo4j.com/docs/)
