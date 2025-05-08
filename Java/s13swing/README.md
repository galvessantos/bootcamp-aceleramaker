# 📌 Seção 13 – Swing

Nesta seção explorei o **Java Swing**, biblioteca gráfica que permite criar interfaces de usuário (GUI) ricas e responsivas para aplicações desktop. Embora seja uma tecnologia mais antiga, o Swing ainda é muito relevante em sistemas corporativos e oferece uma base sólida para entender conceitos de programação de interfaces gráficas.

---

## 🔹 Componentes Básicos

### 1 – Containers
Containers são componentes que organizam e agrupam outros componentes:

```java
// Frame - janela principal
JFrame frame = new JFrame("Minha Aplicação");
frame.setSize(500, 400);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Panel - container para organizar componentes
JPanel panel = new JPanel();
frame.add(panel);
```

### 2 – Componentes de Interface
Elementos visuais que o usuário interage:

```java
// Botão
JButton button = new JButton("Clique Aqui");
panel.add(button);

// Campo de texto
JTextField textField = new JTextField(20); // 20 colunas
panel.add(textField);

// Label
JLabel label = new JLabel("Digite seu nome:");
panel.add(label);

// Checkbox
JCheckBox checkBox = new JCheckBox("Concordo com os termos");
panel.add(checkBox);
```

### 3 – Layouts
Gerenciadores de layout que controlam a disposição dos componentes:

```java
// FlowLayout - componentes em sequência
panel.setLayout(new FlowLayout());

// BorderLayout - componentes nos cantos e centro
frame.setLayout(new BorderLayout());
frame.add(new JButton("Norte"), BorderLayout.NORTH);
frame.add(new JButton("Centro"), BorderLayout.CENTER);

// GridLayout - grade de células
JPanel grid = new JPanel(new GridLayout(3, 2)); // 3 linhas, 2 colunas
```

---

## 🔹 Eventos e Listeners

Implementei o tratamento de eventos para tornar a interface interativa:

### ActionListener para botões

```java
JButton btnCalcular = new JButton("Calcular");
btnCalcular.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Código a ser executado quando o botão for clicado
        double valor = Double.parseDouble(txtValor.getText());
        lblResultado.setText("Resultado: " + (valor * 2));
    }
});
```

### Usando Lambda (Java 8+)

```java
btnCalcular.addActionListener(e -> {
    double valor = Double.parseDouble(txtValor.getText());
    lblResultado.setText("Resultado: " + (valor * 2));
});
```

### MouseListener para detectar cliques e movimentos do mouse

```java
panel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicou em: X=" + e.getX() + ", Y=" + e.getY());
    }
});
```

---

## 🔹 Componentes Avançados

Além dos componentes básicos, explorei elementos mais avançados:

### JTable para exibir dados tabulares

```java
String[] colunas = {"Nome", "Idade", "Email"};
Object[][] dados = {
    {"Ana", 25, "ana@email.com"},
    {"Carlos", 30, "carlos@email.com"},
    {"Pedro", 28, "pedro@email.com"}
};

JTable tabela = new JTable(dados, colunas);
JScrollPane scrollPane = new JScrollPane(tabela);
frame.add(scrollPane);
```

### JTree para visualizar dados hierárquicos

```java
DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Categorias");
DefaultMutableTreeNode eletronica = new DefaultMutableTreeNode("Eletrônica");
raiz.add(eletronica);
eletronica.add(new DefaultMutableTreeNode("TV"));
eletronica.add(new DefaultMutableTreeNode("Celular"));

JTree arvore = new JTree(raiz);
frame.add(new JScrollPane(arvore));
```

### JTabbedPane para criar interfaces com abas

```java
JTabbedPane abas = new JTabbedPane();
abas.addTab("Informações Pessoais", painelPessoal);
abas.addTab("Endereço", painelEndereco);
abas.addTab("Contato", painelContato);
frame.add(abas);
```

---

## 📂 Documentação Oficial
Para mais informações sobre Java Swing:
🔗 [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
🔗 [Java Swing API](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html)
