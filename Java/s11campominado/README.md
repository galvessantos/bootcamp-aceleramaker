# 📌 Seção 11 – Campo Minado

Nesta seção desenvolvi uma versão do clássico jogo Campo Minado utilizando Java, aplicando conceitos de programação orientada a objetos, tratamento de exceções e testes unitários. Este projeto representa a consolidação dos conceitos aprendidos nas seções anteriores em um projeto prático e completo.

---

## 🔹 Arquitetura do Projeto

O projeto foi estruturado seguindo princípios de organização e separação de responsabilidades:

### 1 – Camada de Modelo (model)
- **Campo**: Representa cada célula do tabuleiro, com atributos como linha, coluna, estado (aberto, fechado, marcado) e comportamentos.
- **Tabuleiro**: Gerencia a coleção de campos e implementa a lógica do jogo.

### 2 – Camada de Visualização (view)
- **TabuleiroView**: Interface com o usuário via console para apresentar o tabuleiro e receber comandos.

### 3 – Camada de Exceções (exceptions)
- **ExplosaoException**: Exceção personalizada disparada quando o jogador abre um campo com mina.
- **SairException**: Exceção para controlar o encerramento do jogo a pedido do usuário.

---

## 🔹 Conceitos Aplicados

### Tratamento de Exceções

Utilizei exceções para controlar fluxos não-padrão de execução:

```java
try {
    campos.parallelStream()
            .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
            .findFirst()
            .ifPresent(c -> c.abrir());
} catch (ExplosaoException e) {
    campos.forEach(c -> c.setAberto(true));
    throw e;
}
```

### Programação Funcional e Lambda

Implementei operações usando recursos funcionais do Java:

```java
public boolean objetivoAlcancado() {
    return campos.stream().allMatch(Campo::objetivoAlcancado);
}
```

### Test-Driven Development (TDD)

Desenvolvi testes unitários para validar a lógica do jogo:

```java
@Test
void testeVizinhoDistancia1Esquerda() {
    Campo vizinho = new Campo(3, 2);
    boolean resultado = campo.adicionarVizinho(vizinho);
    assertTrue(resultado);
}
```

---

## 🔹 Mecânica do Jogo

O jogo segue as regras clássicas do Campo Minado:

1. O jogador pode abrir um campo ou marcá-lo como potencial mina.
2. Ao abrir um campo:
   - Se houver uma mina, o jogo termina (derrota).
   - Se não houver mina, revela a quantidade de minas vizinhas.
   - Se não houver minas vizinhas, abre automaticamente os campos adjacentes.
3. O objetivo é abrir todos os campos sem minas.

---

## 📂 Documentação Oficial
Para mais informações sobre as APIs utilizadas:
🔗 [Java 8 Stream API](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
🔗 [Java Exception Handling](https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html)
