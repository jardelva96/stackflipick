# StackFlipick

🚀 **Gerenciador de Versões para Desenvolvedores Windows**

StackFlipick é uma aplicação desktop moderna desenvolvida em JavaFX que permite alternar facilmente entre diferentes versões de ferramentas de desenvolvimento instaladas no Windows.

## 📋 Recursos

- ☕ **Java (JDK/JRE)** - Gerenciar versões do Java
- 🟢 **Node.js** - Alternar entre versões do Node
- 🔷 **.NET SDK** - Controlar versões do .NET
- 🐍 **Python** - Gerenciar instalações Python
- 📦 **Apache Maven** - Selecionar versão do Maven

## 🎨 Interface

- Design moderno com gradientes roxos
- Navegação por cards visuais
- Indicadores de versão atual e selecionada
- Interface responsiva e intuitiva
- Carregamento assíncrono (sem travamentos)

## 🛠️ Tecnologias

- **Java 17** (Eclipse Adoptium)
- **JavaFX 21.0.1**
- **Maven** (build tool)
- **PowerShell** (integração com Windows)

## 📦 Como Usar

### Pré-requisitos

- Windows 10/11
- Java 17 ou superior instalado
- Maven (incluído via wrapper)

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/jardelva96/stackflipick.git
cd stackflipick
```

2. Execute a aplicação:
```bash
# No PowerShell
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot'
.\mvnw.cmd clean javafx:run
```

### Como Funciona

1. **Selecione a tecnologia** no menu principal (Java, Node, .NET, etc)
2. **Escolha a versão** desejada clicando no card
3. **Clique em "Aplicar Alterações"** para definir como padrão
4. As variáveis de ambiente USER são atualizadas automaticamente
5. **Reinicie terminais/IDEs** para as mudanças terem efeito

## 🔧 Estrutura do Projeto

```
stackflipick/
├── src/main/java/br/gov/sc/detran/versionmanager/
│   └── VersionManagerApp.java      # Aplicação principal
├── pom.xml                          # Configuração Maven
├── mvnw.cmd                         # Maven Wrapper (Windows)
└── .mvn/                            # Configuração Maven Wrapper
```

## 📝 Funcionalidades Técnicas

### Detecção Automática
- Escaneia diretórios padrão de instalação
- Detecta versões instaladas automaticamente
- Identifica a versão atualmente ativa no sistema

### Modificação de Ambiente
- Altera variáveis USER (JAVA_HOME, PATH, etc)
- Utiliza PowerShell para persistir mudanças no registro
- Não requer privilégios de administrador

### Performance
- Carregamento assíncrono em background threads
- Interface nunca trava durante detecção
- Atualizações instantâneas na seleção

## 🎯 Roadmap

- [ ] Adicionar suporte para mais tecnologias
- [ ] Exportar configuração para script
- [ ] Ícone da aplicação e logo
- [ ] Atalho de teclado para navegação
- [ ] Sistema de perfis (dev, prod, etc)
- [ ] Notificações de atualização de versões

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se livre para:

1. Fazer fork do projeto
2. Criar uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abrir um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

Desenvolvido por **DETRAN/SC**

## 🙏 Agradecimentos

- Comunidade JavaFX
- Eclipse Adoptium (OpenJDK)
- Todos os desenvolvedores que testaram e deram feedback

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no GitHub!
