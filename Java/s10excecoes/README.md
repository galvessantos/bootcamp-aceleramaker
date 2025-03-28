# 📌 Seção 10 – Tratamento de Erros

O **tratamento de erros** é uma prática essencial para garantir que o programa consiga lidar com situações inesperadas sem falhar abruptamente. Ele permite identificar, capturar e tratar erros de forma controlada, oferecendo ao usuário ou ao desenvolvedor informações claras sobre o que aconteceu e possibilitando a recuperação ou a correção do erro.

---

## 🔹 Tipos de Erros

### 1 – Erros de **Compilação**
Erros de compilação são problemas que impedem um código-fonte de ser convertido em um programa executável. Esses erros são detectados pelo compilador e impedem que o código seja executado. Eles são mais fáceis de corrigir, pois o código não chega nem a ser executado.

### 2 – Erros de **Execução**
Erros de execução (ou runtime errors) acontecem enquanto o programa está rodando, após a compilação. Esses erros podem fazer o programa parar inesperadamente ou gerar resultados incorretos, mesmo quando o código foi compilado com sucesso.

---

## 🔹 Hierarquia de Exceções em Java

Em Java, a classe **Throwable** é a raiz de todos os erros e exceções que podem ser lançados ou capturados. Ela possui duas subclasses principais:

- **Error**: Representa falhas graves, como falta de memória ou estouro de pilha. Erros do tipo **Error** não devem ser tratados pela aplicação.
- **Exception**: Representa situações anormais que podem ser tratadas. As exceções podem ser divididas em duas categorias:
  - **Checked Exceptions**: Exceções que o compilador exige que sejam tratadas explicitamente.
  - **Unchecked Exceptions**: Exceções que não precisam ser tratadas ou declaradas.

---

## 🔹 Pilha de Métodos

A **pilha de métodos** (Stack) é uma estrutura de dados crucial para a execução de código. Ela armazena o estado das chamadas de métodos. Cada vez que um método é chamado, um "frame" é empilhado. Quando o método termina, o frame é desempilhado. Ela segue o modelo **LIFO (Last In, First Out)** e é muito útil para depuração, como no caso do **StackOverflowError**.

---

## 🔹 Exceções Checadas vs Exceções Não Checadas

### **Exceções Checadas** (Checked Exceptions)
São aquelas que o compilador exige que sejam tratadas explicitamente no código, com o uso de `try-catch` ou pela declaração da palavra-chave `throws`. Elas representam condições que podem ser tratadas, como problemas ao acessar arquivos ou bancos de dados.

**Exemplo:**
```java
try {
    FileReader reader = new FileReader("arquivo.txt"); // Pode lançar IOException
} catch (IOException e) {
    e.printStackTrace(); // Tratamento obrigatório
}
```

### **Exceções Não Checadas** (Unchecked Exceptions)
São erros que não precisam ser tratados ou declarados pelo compilador. Elas geralmente indicam falhas lógicas no código, como acessar um índice fora dos limites de um array ou tentar usar um objeto nulo.

**Exemplo:**
```java
String texto = null;
System.out.println(texto.length()); // Lança NullPointerException
```

---

## 🔹 Exceções Personalizadas

As **exceções personalizadas** permitem que você crie novas exceções com mensagens e lógicas específicas para o seu sistema, como validar entradas de dados (por exemplo, string vazia ou número fora do intervalo).

**Exemplo de exceção personalizada**:
```java
public class ExcecaoNumeroForaDoIntervalo extends Exception {
    public ExcecaoNumeroForaDoIntervalo(String mensagem) {
        super(mensagem);
    }
}
```

---

## 🔹 Bloco **finally**

O bloco `finally` é utilizado para executar um conjunto de instruções independentemente de haver exceções ou não. Ele garante que o código dentro dele seja sempre executado, o que é útil para liberar recursos ou garantir que certas operações sejam feitas (como fechar arquivos, conexões, etc.).

**Exemplo:**
```java
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    try {
        System.out.println(7 / sc.nextInt());
    } catch (Exception e) {
        System.out.println(e.getMessage());
    } finally {
        System.out.println("Finalmente...");
        sc.close();
    }
}
```

---

## 🔹 Exceção Causadora

As **exceções causadoras** permitem encapsular uma exceção original dentro de uma nova, preservando o contexto do erro. Isso é útil quando um erro em uma camada inferior, como o acesso a um banco de dados, precisa ser propagado para uma camada superior (como um controlador), mantendo o rastreamento do erro original.

**Exemplo de exceção causadora:**
```java
public class ErroDeBancoDeDadosException extends RuntimeException {
    public ErroDeBancoDeDadosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

public class UsuarioRepository {
    public Usuario findById(Long id) {
        try {
            throw new SQLException("Erro ao acessar o banco");
        } catch (SQLException e) {
            throw new ErroDeBancoDeDadosException("Falha ao recuperar o usuário", e);
        }
    }
}
```

---

## 📂 Documentação Oficial
Para mais detalhes sobre o tratamento de exceções e erros em Java, consulte a documentação oficial:
🔗 [Java 8 Exception Handling](https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html)

---

💻 Embora nesta seção não tenhamos um desafio específico, diversos exercícios foram realizados e registrados via **commit**.

---
