# 📌 Seção 12 – Generics

Nesta seção explorei o poderoso recurso de **Generics** do Java, que permite criar classes, interfaces e métodos que operam com tipos parametrizados. Generics fornecem type safety em tempo de compilação e eliminam a necessidade de casting explícito, resultando em código mais seguro e reutilizável.

---

## 🔹 Conceitos Fundamentais

### 1 – Definição de Classes Genéricas

Classes genéricas permitem especificar o tipo dos dados com os quais a classe vai trabalhar no momento da instanciação:

```java
// Definição da classe
public class Caixa<T> {
    private T coisa;
    
    public void guardar(T coisa) {
        this.coisa = coisa;
    }
    
    public T abrir() {
        return coisa;
    }
}

// Uso da classe
Caixa<String> caixaDeTexto = new Caixa<>();
caixaDeTexto.guardar("Segredo");
String segredo = caixaDeTexto.abrir();
```

### 2 – Métodos Genéricos

Métodos genéricos permitem usar tipos parametrizados sem precisar definir a classe inteira como genérica:

```java
public static <T> T getUltimo(List<T> lista) {
    if (lista.isEmpty()) return null;
    return lista.get(lista.size() - 1);
}

List<String> nomes = Arrays.asList("Ana", "Carlos", "Pedro");
String ultimo = getUltimo(nomes); // "Pedro"
```

### 3 – Wildcards (?)

Wildcards são úteis quando queremos flexibilidade na definição dos tipos aceitos:

```java
// Wildcard unbounded (aceita qualquer tipo)
public void imprimirLista(List<?> lista) {
    lista.forEach(System.out::println);
}

// Wildcard com limite superior (upper bounded)
public double somarNumeros(List<? extends Number> lista) {
    double soma = 0;
    for (Number n : lista) {
        soma += n.doubleValue();
    }
    return soma;
}

// Wildcard com limite inferior (lower bounded)
public void adicionarNumeros(List<? super Integer> lista) {
    lista.add(1);
    lista.add(2);
    lista.add(3);
}
```

---

## 🔹 Estruturas de Dados Genéricas

Implementei algumas estruturas de dados genéricas como exercício:

### Par Genérico

```java
public class Par<C, V> {
    private C chave;
    private V valor;
    
    public Par(C chave, V valor) {
        this.chave = chave;
        this.valor = valor;
    }
    
    // Getters e setters...
}
```

### Lista Genérica

```java
public class Lista<T> {
    private List<T> itens = new ArrayList<>();
    
    public void adicionar(T item) {
        itens.add(item);
    }
    
    public T obter(int indice) {
        return itens.get(indice);
    }
}
```

---

## 🔹 Restrições e Limitações

Durante a implementação, aprendi algumas limitações dos Generics em Java:

1. **Type Erasure**: Os tipos genéricos são "apagados" em tempo de execução.
2. **Não é possível criar instâncias de tipos genéricos**: `new T()` não é permitido.
3. **Não é possível usar tipos primitivos diretamente**: Deve-se usar as classes wrapper (Integer, Double, etc).
4. **Não é possível criar arrays de tipos genéricos**: `new T[10]` não é válido.

---

## 🔹 Benefícios dos Generics

A utilização de Generics trás diversas vantagens:

- **Type Safety**: Erros de tipo são capturados em tempo de compilação.
- **Eliminação de casts**: Não é necessário fazer casting explícito.
- **Algoritmos reutilizáveis**: Implementações que funcionam com qualquer tipo de dados.
- **Melhor legibilidade**: O código fica mais claro e expressivo.
- **API mais clara**: As interfaces ficam mais fáceis de entender e usar.

---

## 🔹 Aplicações Práticas

Os conceitos de Generics foram aplicados em diversos contextos:

- **Implementação de estruturas de dados**: Listas, filas, pilhas genéricas.
- **Criação de caches**: Armazenamento temporário de objetos de diferentes tipos.
- **Construção de métodos utilitários**: Métodos que funcionam com diferentes tipos.
- **Integração com APIs**: Uso de APIs genéricas como Collections Framework.

---

## 📂 Documentação Oficial
Para aprofundar o conhecimento em Generics, consulte:
🔗 [Java Generics Tutorial](https://docs.oracle.com/javase/tutorial/java/generics/index.html)
🔗 [Java Generics API](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/TypeVariable.html)
