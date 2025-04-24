# 📋 Task Management Application

## 📝 Descrição

Bem-vindo ao **Task Management Application**, um aplicativo de gerenciamento de tarefas simples e eficiente! 🚀

Este projeto foi desenvolvido em **Java** com o framework **JavaFX** para a interface gráfica e **PostgreSQL** como banco de dados. Ele permite que você organize suas tarefas de maneira prática, facilitando o acompanhamento do progresso.

---

## ✨ Funcionalidades

✅ **Adicionar Tarefas**: Registre novas tarefas com título, descrição, data de entrega e status.

✅ **Listar Tarefas**: Exiba todas as tarefas registradas em uma tabela interativa.

✅ **Atualizar Tarefas**: Altere o status ou informações de tarefas existentes.

✅ **Excluir Tarefas**: Remova tarefas que não são mais necessárias.

✅ **Relógio em Tempo Real**: Exibição dinâmica da data e hora na interface.

---

## 📂 Estrutura do Projeto

A estrutura do projeto segue o padrão **MVC** (Model-View-Controller):

```
src/
├── main/
│   ├── java/
│   │   ├── com.example.taskmanagement/
│   │   │   ├── Main.java        # Inicializa o aplicativo
│   │   │   ├── model/
│   │   │   │   └── Tarefa.java  # Define a classe Tarefa
│   │   │   ├── controller/
│   │   │   │   └── TarefaController.java # Lógica principal
│   │   │   ├── dao/
│   │   │   │   ├── ConnectionFactory.java # Conexão com o banco
│   │   │   │   └── TarefaDAO.java        # Operações de CRUD
│   ├── resources/
│       ├── view/
│       │   └── MainView.fxml    # Interface gráfica
└── test/
```

---

## 🛠️ Requisitos

### 📦 Dependências

As dependências estão listadas no arquivo `pom.xml`:

- **Java 24** ou superior
- **JavaFX 17.0.6**
- **PostgreSQL JDBC Driver 42.7.3**
- **JUnit 5.10.2** (para testes)

### 🗄️ Banco de Dados

Certifique-se de configurar o banco de dados **PostgreSQL** corretamente. Altere as credenciais no arquivo `ConnectionFactory.java` se necessário:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/tarefas_db";
private static final String USER = "postgres";
private static final String PASSWORD = "123123";
```

Crie a tabela `tarefas` com o seguinte comando SQL:

```sql
CREATE TABLE tarefas (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_entrega DATE,
    status VARCHAR(50)
);
```

---

## ⚙️ Como Configurar e Executar

1. **Clone o Repositório**
   ```bash
   git clone <url_do_repositorio>
   cd TaskManagement
   ```

2. **Configure o Banco de Dados**
   - Certifique-se de que o PostgreSQL está rodando.
   - Altere as credenciais de conexão, se necessário.
   - Execute o script SQL fornecido para criar a tabela.

3. **Compile o Projeto**
   ```bash
   mvn clean package
   ```

4. **Execute a Aplicação**
   ```bash
   mvn javafx:run
   ```

---

## 🎥 Vídeo de Apresentação

👉 **[Link do Vídeo](https://www.youtube.com/watch?v=H6TV_6dJiRs)** 👈

---

## 🧑‍💻 Contribuição

Quer contribuir? Siga estes passos: 

1. Faça um fork do repositório.
2. Crie um branch para sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Faça suas alterações e envie:
   ```bash
   git push origin minha-feature
   ```
4. Abra um Pull Request.


## 📬 Contato

Desenvolvido por **Jhonzito**. Entre em contato!  
📧 **Email**: joaopedroaraujosilvabarbosa@gmail.com  
🐙 **GitHub**: [jhonzito66](https://github.com/jhonzito66)
