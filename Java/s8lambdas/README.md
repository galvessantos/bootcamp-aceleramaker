# 💡 Seção 8 – Lambdas

Expressões lambda são funções anônimas, ou seja, funções que não possuem um nome explícito. Elas são frequentemente utilizadas para escrever código de forma mais concisa, especialmente quando precisamos passar um comportamento como argumento para métodos.

No Java, expressões lambda são usadas para implementar interfaces funcionais, e o próprio Java fornece várias interfaces funcionais na API `java.util.function`.

### Implementação Manual de uma Interface Funcional

Nesta seção, aprendemos inicialmente a criar uma função simples de soma de dois números utilizando expressão lambda, mas sem utilizar a API do Java.

Criamos uma interface funcional `Calculo`:

```java
package lambdas;

public interface Calculo {
    double executar(double a, double b);
}
```

Depois, implementamos a expressão lambda para realizar a soma:

```java
package lambdas;

public class CalculoTeste {
    public static void main(String[] args) {
        Calculo calc = (x, y) -> { return x + y; };
        System.out.println(calc.executar(2, 3)); // Saída: 5
    }
}
```

Quando a interface tem apenas um método, podemos simplificar a expressão removendo as chaves:

```java
calc = (x, y) -> x * y;
```

### Interfaces Funcionais e `@FunctionalInterface`

Interfaces funcionais são interfaces que possuem apenas um único método abstrato. Elas podem conter métodos `default` e `static`. A anotação `@FunctionalInterface` indica explicitamente que a interface seguirá essa regra.

#### Uso de `BinaryOperator<T>`

O Java fornece interfaces funcionais prontas no pacote `java.util.function`. Para substituir nossa interface `Calculo`, podemos utilizar `BinaryOperator<T>`:

```java
import java.util.function.BinaryOperator;

BinaryOperator<Double> calc = (x, y) -> { return x + y; };
System.out.println(calc.apply(2.0, 3.0)); // Saída: 5.0
```

#### Foreach com Expressões Lambda

Aprendemos diferentes formas de percorrer listas usando `forEach`:

```java
import java.util.Arrays;
import java.util.List;

List<String> aprovados = Arrays.asList("Ana", "Bia", "Lia", "Gui");

System.out.println("Forma tradicional");
for(String nome : aprovados) {
    System.out.println(nome);
}

System.out.println("Lambda");
aprovados.forEach(nome -> System.out.println(nome));

System.out.println("Method Reference");
aprovados.forEach(System.out::println);
```

#### Interface `Function<T, R>`

A interface `Function<T, R>` permite transformar um dado de tipo `T` em um resultado de tipo `R`.

Exemplo para verificar se um número é par ou ímpar:

```java
import java.util.function.Function;

Function<Integer, String> parOuImpar =
        numero -> numero % 2 == 0 ? "Par" : "Ímpar";

System.out.println(parOuImpar.apply(32)); // Saída: Par
```

#### Encadeamento com `andThen()`

Podemos encadear transformações com `andThen()`:

```java
Function<String, String> oResultadoE =
        valor -> "O resultado é: " + valor;

System.out.println(parOuImpar.andThen(oResultadoE).apply(32));
// Saída: O resultado é: Par
```

#### Interface `UnaryOperator<T>`

A interface `UnaryOperator<T>` representa uma operação sobre um único operando do mesmo tipo.

```java
import java.util.function.UnaryOperator;

UnaryOperator<Integer> dobrar = x -> x * 2;
UnaryOperator<Integer> adicionarTres = x -> x + 3;
UnaryOperator<Integer> operacaoComposta = dobrar.andThen(adicionarTres);

int resultado = operacaoComposta.apply(5); // (5 * 2) + 3 = 13
System.out.println(resultado); // Saída: 13
```

#### Interface `BiFunction<T, U, R>`

A interface `BiFunction<T, U, R>` recebe dois argumentos e retorna um resultado:

```java
import java.util.function.BiFunction;

BiFunction<Double, Double, String> calcularSituacao = (nota1, nota2) -> {
    double media = (nota1 + nota2) / 2;
    return media >= 6.0 ? "Aprovado" : "Reprovado";
};

System.out.println(calcularSituacao.apply(7.0, 8.0)); // Saída: Aprovado
System.out.println(calcularSituacao.apply(5.0, 4.5)); // Saída: Reprovado
```

## 📑 Desafio da Seção

No desafio, tivemos que calcular o preço final de um produto aplicando um desconto, imposto municipal, frete, arredondando e formatando o resultado.

#### Utilizando `Function` para calcular o preço com desconto

```java
import java.util.function.Function;

Function<Produto, Double> precoFinal =
        produto -> produto.preco * (1 - produto.desconto);
```

#### Utilizando `UnaryOperator` para calcular o preço com imposto

```java
import java.util.function.UnaryOperator;

UnaryOperator<Double> impostoMunicipal =
        preco -> preco >= 2500 ? preco * 1.085 : preco;
```
## 📂 Documentação Oficial
Consulte a documentação oficial de interfaces funcionais em Java:
🔗 [Java 8 Functional Interfaces](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html).

---



