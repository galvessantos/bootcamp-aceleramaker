# 📌 Seção 9 – Stream API

A **Stream API** do Java permite processar coleções de dados de maneira eficiente e funcional. Suas operações são divididas em **três categorias principais**:

---

## 🔹 1. Built-in Operations (Built ops)
São operações prontas que manipulam ou coletam dados de um Stream. Elas **não retornam um novo Stream**, e incluem:

- **`forEach()`** → Itera sobre os elementos do Stream.
- **`collect()`** → Converte o Stream de volta para uma coleção (como `List`, `Set`, etc.).
- **`count()`** → Conta o número de elementos no Stream.

Exemplo:
```java
List<String> aprovados = Arrays.asList("Lu", "Gui", "Luca", "Ana");

aprovados.stream()
         .forEach(System.out::println);
```

---

## 🔹 2. Intermediate Operations (Int ops)
São operações que **transformam um Stream em outro Stream**, permitindo o **encadeamento de chamadas**. Essas operações são **lazy**, ou seja, só são executadas quando uma operação terminal é chamada.

### Métodos importantes:
- **`filter()`** → Filtra elementos de acordo com uma condição.
- **`map()`** → Transforma os elementos do Stream.
- **`sorted()`** → Ordena os elementos.
- **`distinct()`** → Remove elementos duplicados.
- **`skip(n)`** → Pula os primeiros `n` elementos do Stream.

Exemplo `map()`:
```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> dobrados = numeros.stream()
                                .map(n -> n * 2)
                                .collect(Collectors.toList());

System.out.println(dobrados);  // Saída: [2, 4, 6, 8, 10]
```

Exemplo `filter()`:
```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> pares = numeros.stream()
                             .filter(n -> n % 2 == 0)
                             .collect(Collectors.toList());

System.out.println(pares);  // Saída: [2, 4, 6, 8, 10]
```

---

## 🔹 3. Terminal Operations (Terminal ops)
São as operações que **disparam o processamento** do Stream e produzem um resultado. Uma vez chamadas, o Stream é consumido e **não pode ser reutilizado**.

### Métodos importantes:
- **`collect()`** → Coleta os elementos em uma coleção.
- **`reduce()`** → Combina os elementos do Stream em um único valor.
- **`forEach()`** → Executa uma ação para cada elemento do Stream.
- **`count()`** → Conta o número de elementos.
- **`min()` e `max()`** → Obtêm o menor ou maior valor de um Stream.
- **`allMatch()`, `anyMatch()`, `noneMatch()`** → Testam se os elementos do Stream correspondem a uma condição.

Exemplo `reduce()`:
```java
List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

int soma = numeros.stream()
                  .reduce(0, (acumulador, n) -> acumulador + n);

System.out.println(soma); // Saída: 15
```

Exemplo `allMatch()`:
```java
List<Integer> numeros = Arrays.asList(2, 4, 6, 8, 10);
boolean todosPares = numeros.stream()
                            .allMatch(n -> n % 2 == 0);

System.out.println(todosPares); // Saída: true
```

---

## 📌 Desafios

### 1️⃣ **Desafio `map()`**
**Objetivo**: Converter um número para binário, inverter a string binária e depois converter de volta para inteiro.

```java
List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

UnaryOperator<String> inverter = s -> new StringBuilder(s).reverse().toString();
Function<String, Integer> binarioParaInt = s -> Integer.parseInt(s, 2);

nums.stream()
    .map(Integer::toBinaryString)
    .map(inverter)
    .map(binarioParaInt)
    .forEach(System.out::println);
```

### 2️⃣ **Desafio `filter()`**
**Objetivo**: Criar um sistema de filtragem de casas para aluguel, classificando em "Excelente negócio", "Bom negócio" e "Mal negócio".

```java
Casa c1 = new Casa("Rua Maria Eugenia, 45", 750.00, 2, false);
Casa c2 = new Casa("Rua Joao Augusto, 69", 1250.00, 3, true);
Casa c3 = new Casa("Rua Gabriel Alves, 10", 2700.00, 5, false);
Casa c4 = new Casa("Rua Rildo Aparecido, 23", 1500.00, 3, false);
Casa c5 = new Casa("Rua Dalete Cristina, 50", 1100.00, 2, true);
Casa c6 = new Casa("Rua Anna Julia, 100", 1800.00, 3, false);
Casa c7 = new Casa("Rua Jonas Lima, 92", 3000.00, 2, false);

List<Casa> casasAluguel = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);

Predicate<Casa> excelenteNegocio = c -> c.valorAluguel <= 1500 && c.piscina && c.quantidadeQuartos >= 2;
Predicate<Casa> bomNegocio = c -> c.valorAluguel <= 1800 && c.piscina && c.quantidadeQuartos >= 3;
Predicate<Casa> malNegocio = c -> c.valorAluguel >= 2500 && !c.piscina && c.quantidadeQuartos <= 5;

casasAluguel.stream()
            .filter(malNegocio)
            .forEach(System.out::println);
```

---

## 📂 Documentação Oficial
Consulte a documentação oficial de Stream API e interfaces funcionais em Java:
🔗 [Java 8 Stream API](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)

