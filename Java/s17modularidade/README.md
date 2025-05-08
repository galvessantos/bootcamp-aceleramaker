# 📌 Seção 17 – Modularidade em Java

Nesta seção explorei o **Sistema de Módulos do Java** (JPMS - Java Platform Module System), introduzido no Java 9. A modularidade permite estruturar as aplicações em componentes bem definidos, melhorando encapsulamento, dependências explícitas e segurança, resultando em aplicações mais robustas, organizadas e escaláveis.

---

## 🔹 Conceitos Fundamentais

### 1 – O que são Módulos?

Módulos são unidades auto-contidas de código e dados relacionados com fronteiras bem definidas:

- Encapsulam pacotes e recursos
- Declaram explicitamente suas dependências
- Expõem seletivamente seus pacotes para uso por outros módulos
- São descritos por um arquivo `module-info.java`

### 2 – Vantagens da Modularidade

A modularidade traz diversos benefícios para o desenvolvimento:

- **Encapsulamento forte**: Controle preciso sobre o que é visível fora do módulo
- **Dependências explícitas**: Clareza sobre o que um módulo precisa para funcionar
- **Segurança melhorada**: Acesso restrito à APIs internas
- **Melhor desempenho**: Carregamento seletivo e otimizado de módulos
- **Facilidade de manutenção**: Componentes com responsabilidades bem definidas
- **Escalabilidade**: Desenvolvimento independente e paralelo de módulos

---

## 🔹 Estrutura Básica de um Módulo

### 1 – Arquivo `module-info.java`

O arquivo `module-info.java` é o descritor do módulo, contendo suas características e relacionamentos:

```java
module com.minhaempresa.app {
    // Exporta pacotes que serão visíveis para outros módulos
    exports com.minhaempresa.app.api;
    exports com.minhaempresa.app.model;
    
    // Exporta pacotes apenas para módulos específicos
    exports com.minhaempresa.app.internal to com.minhaempresa.plugin;
    
    // Declara dependências de outros módulos
    requires java.base;  // implícito, mas pode ser explicitado
    requires java.sql;
    requires transitive java.logging;  // transitivamente expõe java.logging
    
    // Usa serviços definidos em outros módulos
    uses com.minhaempresa.service.PaymentService;
    
    // Fornece implementações de serviços
    provides com.minhaempresa.service.PaymentService 
             with com.minhaempresa.app.CreditCardPaymentService;
}
```

### 2 – Organização Física

Organizei os módulos seguindo uma estrutura física clara:

```
projeto/
├── src/
│   ├── modulo.core/
│   │   ├── com/minhaempresa/core/
│   │   │   ├── domain/
│   │   │   │   └── ...
│   │   │   ├── service/
│   │   │   │   └── ...
│   │   │   └── util/
│   │   │       └── ...
│   │   └── module-info.java
│   │
│   ├── modulo.persistencia/
│   │   ├── com/minhaempresa/persistencia/
│   │   │   ├── repository/
│   │   │   │   └── ...
│   │   │   └── config/
│   │   │       └── ...
│   │   └── module-info.java
│   │
│   └── modulo.ui/
│       ├── com/minhaempresa/ui/
│       │   ├── controllers/
│       │   │   └── ...
│       │   └── views/
│       │       └── ...
│       └── module-info.java
│
└── mods/
    ├── modulo.core.jar
    ├── modulo.persistencia.jar
    └── modulo.ui.jar
```

---

## 🔹 Diretivas do Sistema de Módulos

### 1 – Exports

A diretiva `exports` controla quais pacotes são visíveis para outros módulos:

```java
// Exporta o pacote para qualquer módulo que requisitá-lo
exports com.minhaempresa.api;

// Exporta o pacote apenas para módulos específicos
exports com.minhaempresa.internal.service to com.minhaempresa.admin, com.minhaempresa.utils;
```

### 2 – Requires

A diretiva `requires` declara dependências entre módulos:

```java
// Dependência básica
requires java.sql;

// Dependência transitiva (reexporta o módulo)
requires transitive java.logging;

// Dependência estática (em tempo de compilação apenas)
requires static java.desktop;
```

### 3 – Uses/Provides

As diretivas `uses` e `provides` habilitam o Service Provider Interface (SPI):

```java
// Consumidor de serviço
uses com.minhaempresa.api.PaymentService;

// Fornecedor de serviço
provides com.minhaempresa.api.PaymentService with com.minhaempresa.impl.CreditCardPaymentService;
```

### 4 – Opens

A diretiva `opens` permite acesso reflexivo aos tipos de um pacote:

```java
// Abre pacote para reflexão para qualquer módulo
opens com.minhaempresa.model;

// Abre pacote para reflexão apenas para módulos específicos
opens com.minhaempresa.config to java.persistence, org.hibernate;
```

---

## 🔹 Compilação e Execução de Aplicações Modulares

### 1 – Compilando uma Aplicação Modular

Utilizei o javac para compilar os módulos:

```bash
# Compilando múltiplos módulos
javac -d mods/modulo.core src/modulo.core/module-info.java src/modulo.core/com/minhaempresa/core/**/*.java

# Compilando com dependências de outros módulos
javac --module-path mods -d mods/modulo.persistencia src/modulo.persistencia/module-info.java src/modulo.persistencia/com/minhaempresa/persistencia/**/*.java
```

### 2 – Executando uma Aplicação Modular

Executei a aplicação modular usando o java com as opções apropriadas:

```bash
# Executando um módulo
java --module-path mods -m modulo.ui/com.minhaempresa.ui.Main

# Listando módulos disponíveis
java --list-modules

# Descrevendo o conteúdo de um módulo
java --describe-module java.sql
```

---

## 🔹 Migração para Aplicação Modular

### 3 – Estratégias de Migração

Aprendi algumas estratégias para migrar aplicações existentes para o sistema modular:

1. **Bottom-up**: Modularizar primeiro as dependências e depois a aplicação principal
2. **Top-down**: Modularizar primeiro a aplicação principal, deixando as dependências no classpath
3. **Inside-out**: Modularizar componentes internos gradualmente
4. **Módulo automático**: Usar JARs não modulares como módulos temporários

### 4 – Módulos Automáticos

Utilizei módulos automáticos como ponte entre código modular e não modular:

```java
// Um JAR não modular colocado no module-path
// se torna um módulo automático com nome derivado do arquivo
module com.minhaempresa.app {
    requires commons.lang3;  // commons-lang3-3.12.0.jar (módulo automático)
}
```

### 5 – Módulo Unnamed (Classpath)

Utilizei o módulo unnamed para compatibilidade com código legado:

```java
// Permite que código no módulo acesse código no classpath
module com.minhaempresa.app {
    requires java.sql;
    
    // Acesso ao código no classpath
    exports com.minhaempresa.app.legacy to unnamed;
}
```

---

## 🔹 Lições Aprendidas e Boas Práticas

Ao longo do desenvolvimento com módulos, identifiquei algumas boas práticas:

1. **Projeto Orientado a Domínio**: Organizar módulos seguindo os limites do domínio
2. **Princípio da Revelação Mínima**: Exportar apenas o necessário
3. **Dependências Claras**: Declarar explicitamente todas as dependências
4. **Interfaces bem definidas**: Desenhar APIs claras entre módulos
5. **Évitar módulos cíclicos**: Dependências circulares entre módulos devem ser evitadas
6. **Isolamento de dependências externas**: Encapsular bibliotecas externas em módulos específicos
7. **Teste Modular**: Estruturar testes respeitando a modularidade

---

## 📂 Documentação Oficial
Para aprofundar o conhecimento em Modularidade no Java:
🔗 [Java Platform Module System](https://openjdk.org/projects/jigsaw/spec/)
🔗 [JEP 261: Module System](https://openjdk.org/jeps/261)
🔗 [The Java Module System](https://www.oracle.com/corporate/features/understanding-java-9-modules.html)
