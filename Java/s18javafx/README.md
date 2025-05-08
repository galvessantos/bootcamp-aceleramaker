# 📌 Seção 18 – JavaFX

Nesta seção estudei o **JavaFX**, a moderna biblioteca gráfica do Java para desenvolvimento de interfaces de usuário. Embora atualmente existam alternativas mais populares para interfaces gráficas, o JavaFX oferece uma visão importante sobre a evolução das GUIs em Java.

---

## 🔹 Visão Geral

JavaFX é uma plataforma para criar aplicações desktop que substituiu o Swing como a API recomendada para interfaces gráficas em Java. Suas principais características incluem:

- Suporte a gráficos acelerados por hardware
- Estilização com CSS
- Arquitetura baseada em grafo de cena (scene graph)
- Separação de apresentação e lógica com FXML
- Suporte a efeitos visuais e animações

---

## 🔹 Componentes Básicos

O JavaFX oferece diversos componentes para construção de interfaces:

```java
// Componentes de texto
Label label = new Label("Texto simples");
TextField textField = new TextField("Campo de texto");
TextArea textArea = new TextArea("Área de texto");

// Botões
Button button = new Button("Clique aqui");
CheckBox checkBox = new CheckBox("Marque esta opção");
RadioButton radioButton = new RadioButton("Selecione");

// Seletores
ComboBox<String> comboBox = new ComboBox<>();
ListView<String> listView = new ListView<>();
```

---

## 🔹 Layouts

Os layouts determinam como os componentes são organizados na tela:

```java
// VBox - organiza componentes verticalmente
VBox vbox = new VBox(10); // 10px de espaçamento
vbox.getChildren().addAll(new Label("Nome:"), new TextField());

// HBox - organiza componentes horizontalmente 
HBox hbox = new HBox(10);
hbox.getChildren().addAll(new Button("OK"), new Button("Cancelar"));

// BorderPane - layout com 5 regiões (top, right, bottom, left, center)
BorderPane borderPane = new BorderPane();
borderPane.setTop(new Label("Topo"));
borderPane.setCenter(new Button("Centro"));

// GridPane - organiza em grade
GridPane gridPane = new GridPane();
gridPane.add(new Label("Linha 1, Coluna 1"), 0, 0);
gridPane.add(new Button("Linha 1, Coluna 2"), 1, 0);
```

---

## 🔹 Tratamento de Eventos

O JavaFX utiliza um sistema de eventos para responder às interações do usuário:

```java
// Usando lambda (Java 8+)
button.setOnAction(event -> System.out.println("Botão clicado!"));

// Usando classe anônima
button.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
        System.out.println("Botão clicado!");
    }
});
```

---

## 🔹 FXML e Scene Builder

O FXML permite separar a interface do código Java:

```xml
<!-- exemplo.fxml -->
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<VBox xmlns="http://javafx.com/javafx"
      xmlns:fx="http://javafx.com/fxml"
      fx:controller="meu.pacote.MeuController"
      spacing="10" alignment="center">
    <Label text="Olá, JavaFX!"/>
    <Button text="Clique Aqui" onAction="#handleButtonAction"/>
</VBox>
```

```java
// MeuController.java
public class MeuController {
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Botão FXML clicado!");
    }
}
```

---

## 🔹 Propriedades e Binding

O binding permite sincronizar valores automaticamente:

```java
// Propriedades
IntegerProperty contador = new SimpleIntegerProperty(0);
StringProperty mensagem = new SimpleStringProperty("Valor: ");

// Binding unidirecional
Label label = new Label();
label.textProperty().bind(mensagem.concat(contador.asString()));

// Atualização automática
contador.set(contador.get() + 1); // Label mostrará "Valor: 1"
```

---

## 🔹 CSS em JavaFX

JavaFX permite estilização com CSS:

```css
/* estilo.css */
.button {
    -fx-background-color: #4CAF50;
    -fx-text-fill: white;
}

.label {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
}
```

```java
// Aplicando o CSS
scene.getStylesheets().add("estilo.css");
```

---

## 📂 Documentação Oficial
Para mais informações sobre JavaFX:
🔗 [JavaFX Documentation](https://openjfx.io/javadoc/17/)
🔗 [Getting Started with JavaFX](https://openjfx.io/openjfx-docs/)
