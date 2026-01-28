package br.gov.sc.detran.versionmanager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VersionManagerApp extends Application {
    
    private List<JavaVersion> javaVersions = new ArrayList<>();
    private List<NodeVersion> nodeVersions = new ArrayList<>();
    private List<DotNetVersion> dotnetVersions = new ArrayList<>();
    private List<PythonVersion> pythonVersions = new ArrayList<>();
    private List<MavenVersion> mavenVersions = new ArrayList<>();
    private JavaVersion selectedJava = null;
    private NodeVersion selectedNode = null;
    private DotNetVersion selectedDotNet = null;
    private PythonVersion selectedPython = null;
    private MavenVersion selectedMaven = null;
    private Label statusLabel;
    private VBox javaContainer;
    private VBox nodeContainer;
    private VBox dotnetContainer;
    private VBox pythonContainer;
    private VBox mavenContainer;
    private Label currentJavaLabel;
    private Label currentNodeLabel;
    private Label currentDotNetLabel;
    private Label currentPythonLabel;
    private Label currentMavenLabel;
    
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("⚙️ Gerenciador de Versões");
        
        // Inicializar containers
        javaContainer = new VBox(8);
        nodeContainer = new VBox(8);
        dotnetContainer = new VBox(8);
        pythonContainer = new VBox(8);
        mavenContainer = new VBox(8);
        
        showMainMenu();
        
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    private void showMainMenu() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea, #764ba2);");

        VBox mainCard = new VBox(30);
        mainCard.setPadding(new Insets(40));
        mainCard.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 10);");
        mainCard.setMaxWidth(750);
        mainCard.setAlignment(Pos.CENTER);

        // Logo no topo
        try {
            javafx.scene.image.Image logo = new javafx.scene.image.Image(
                getClass().getResourceAsStream("/images/app-logo.png"));
            javafx.scene.image.ImageView logoView = new javafx.scene.image.ImageView(logo);
            logoView.setFitWidth(100);
            logoView.setFitHeight(100);
            logoView.setPreserveRatio(true);
            mainCard.getChildren().add(logoView);
        } catch (Exception e) {
            // Se não carregar logo, apenas continua
        }

        Label title = new Label("StackFlipick");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#667eea"));
        
        Label subtitle = new Label("Escolha a tecnologia que deseja gerenciar");
        subtitle.setFont(Font.font("Segoe UI", 14));
        subtitle.setTextFill(Color.web("#666666"));

        // Grid de ícones
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        // Linha 1: Java, Node, .NET
        grid.add(createTechCard("java-icon.png", "Java", "#f89820", this::showJavaManager), 0, 0);
        grid.add(createTechCard("nodejs-icon.png", "Node.js", "#68a063", this::showNodeManager), 1, 0);
        grid.add(createTechCard("dotnet-icon.png", ".NET", "#512bd4", this::showDotNetManager), 2, 0);
        
        // Linha 2: Python, Maven
        grid.add(createTechCard("python-icon.png", "Python", "#3776ab", this::showPythonManager), 0, 1);
        grid.add(createTechCard("maven-icon.png", "Maven", "#c71a36", this::showMavenManager), 1, 1);

        mainCard.getChildren().addAll(title, subtitle, grid);
        
        root.getChildren().add(mainCard);
        
        Scene scene = new Scene(root, 850, 650);
        primaryStage.setScene(scene);
    }

    private VBox createTechCard(String imageName, String name, String color, Runnable onClick) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.CENTER);
        card.setStyle(String.format(
            "-fx-background-color: white; -fx-background-radius: 15; " +
            "-fx-border-color: %s; -fx-border-width: 3; -fx-border-radius: 15; " +
            "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);", color));
        card.setPrefSize(220, 180);

        // Carregar imagem do resources
        try {
            javafx.scene.image.Image image = new javafx.scene.image.Image(
                getClass().getResourceAsStream("/images/" + imageName));
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
            
            Label nameLabel = new Label(name);
            nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
            nameLabel.setTextFill(Color.web(color));

            card.getChildren().addAll(imageView, nameLabel);
        } catch (Exception e) {
            // Fallback para emoji se imagem não carregar
            Label iconLabel = new Label("⚙");
            iconLabel.setFont(Font.font(60));
            
            Label nameLabel = new Label(name);
            nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
            nameLabel.setTextFill(Color.web(color));

            card.getChildren().addAll(iconLabel, nameLabel);
        }

        final String baseStyle = card.getStyle();
        card.setOnMouseEntered(e -> 
            card.setStyle(baseStyle.replace("dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3)", 
                                           "dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 8)") + 
                         "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        card.setOnMouseExited(e -> card.setStyle(baseStyle));
        card.setOnMouseClicked(e -> onClick.run());

        return card;
    }

    private void showJavaManager() {
        showTechManager("☕ Java Development Kit (JDK)", "JAVA", 
            () -> { detectJavaVersions(); renderJavaVersions(); },
            javaContainer, currentJavaLabel);
    }

    private void showNodeManager() {
        showTechManager("🟢 Node.js", "NODE", 
            () -> { detectNodeVersions(); renderNodeVersions(); },
            nodeContainer, currentNodeLabel);
    }

    private void showDotNetManager() {
        showTechManager("🔷 .NET SDK", "DOTNET", 
            () -> { detectDotNetVersions(); renderDotNetVersions(); },
            dotnetContainer, currentDotNetLabel);
    }

    private void showPythonManager() {
        showTechManager("🐍 Python", "PYTHON", 
            () -> { detectPythonVersions(); renderPythonVersions(); },
            pythonContainer, currentPythonLabel);
    }

    private void showMavenManager() {
        showTechManager("📦 Apache Maven", "MAVEN", 
            () -> { detectMavenVersions(); renderMavenVersions(); },
            mavenContainer, currentMavenLabel);
    }

    private void showTechManager(String title, String techType, Runnable loadVersions, 
                                 VBox container, Label currentLabel) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea, #764ba2);");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPadding(new Insets(15));

        VBox mainCard = new VBox(20);
        mainCard.setPadding(new Insets(30));
        mainCard.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 10);");
        mainCard.setMaxWidth(700);
        mainCard.setAlignment(Pos.TOP_CENTER);

        // Header com botão voltar
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Button backButton = new Button("◀ Voltar");
        backButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        backButton.setTextFill(Color.WHITE);
        backButton.setPadding(new Insets(8, 18, 8, 18));
        backButton.setStyle("-fx-background-color: #667eea; -fx-background-radius: 20; -fx-cursor: hand;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: derive(#667eea, -15%); -fx-background-radius: 20; -fx-cursor: hand;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #667eea; -fx-background-radius: 20; -fx-cursor: hand;"));
        backButton.setOnAction(e -> showMainMenu());

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#667eea"));

        header.getChildren().addAll(backButton, titleLabel);

        // Status atual - garantir que exista
        final Label finalCurrentLabel;
        if (currentLabel == null) {
            finalCurrentLabel = new Label();
        } else {
            finalCurrentLabel = currentLabel;
        }
        finalCurrentLabel.setFont(Font.font("Segoe UI", 11));
        finalCurrentLabel.setTextFill(Color.WHITE);
        finalCurrentLabel.setPadding(new Insets(10));
        finalCurrentLabel.setMaxWidth(Double.MAX_VALUE);
        finalCurrentLabel.setWrapText(true);
        finalCurrentLabel.setStyle("-fx-background-color: linear-gradient(to right, #11998e, #38ef7d); -fx-background-radius: 12;");
        finalCurrentLabel.setVisible(false); // Ocultar até carregar

        // Loading indicator
        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setMaxSize(40, 40);
        VBox loadingBox = new VBox(10, loadingIndicator, new Label("Carregando versões..."));
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setPadding(new Insets(20));
        container.getChildren().clear();
        container.getChildren().add(loadingBox);

        // Status
        statusLabel = new Label();
        statusLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        statusLabel.setVisible(false);
        statusLabel.setPadding(new Insets(12));
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setStyle("-fx-background-radius: 10;");
        statusLabel.setWrapText(true);

        // Botões
        HBox buttonBox = new HBox(12);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button applyButton = createButton("💾 Aplicar Alterações", "#667eea");
        applyButton.setOnAction(e -> {
            applySingleTechChanges(techType);
            // Atualizar em background
            new Thread(() -> {
                loadVersions.run();
            }).start();
        });
        
        Button refreshButton = createButton("🔄 Atualizar", "#38ef7d");
        refreshButton.setOnAction(e -> {
            // Mostrar loading novamente
            javafx.application.Platform.runLater(() -> {
                finalCurrentLabel.setVisible(false);
                container.getChildren().clear();
                container.getChildren().add(loadingBox);
            });
            // Carregar em background
            new Thread(() -> {
                loadVersions.run();
            }).start();
        });

        buttonBox.getChildren().addAll(applyButton, refreshButton);

        mainCard.getChildren().addAll(header, finalCurrentLabel, container, statusLabel, buttonBox);

        VBox scrollContent = new VBox(mainCard);
        scrollContent.setAlignment(Pos.TOP_CENTER);
        scrollContent.setPadding(new Insets(10));
        scrollPane.setContent(scrollContent);

        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);

        // Carregar versões em background thread
        new Thread(() -> {
            loadVersions.run();
        }).start();
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        button.setTextFill(Color.WHITE);
        button.setPadding(new Insets(10, 28, 10, 28));
        String style = String.format(
            "-fx-background-color: %s; -fx-background-radius: 20; -fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);", color);
        button.setStyle(style);
        
        button.setOnMouseEntered(e -> button.setStyle(String.format(
            "-fx-background-color: derive(%s, -10%%); -fx-background-radius: 20; -fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 12, 0, 0, 6);", color)));
        
        button.setOnMouseExited(e -> button.setStyle(style));
        
        return button;
    }

    private void applySingleTechChanges(String techType) {
        boolean hasSelection = false;
        StringBuilder command = new StringBuilder("powershell -Command \"");

        switch (techType) {
            case "JAVA":
                if (selectedJava != null) {
                    hasSelection = true;
                    command.append(String.format(
                        "[System.Environment]::SetEnvironmentVariable('JAVA_HOME', '%s', 'User'); ",
                        selectedJava.path));
                    command.append(String.format(
                        "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
                        "$newPath = ('%s\\bin;' + ($userPath -replace '(?i)C:\\\\\\\\Program Files.*?Java.*?\\\\\\\\bin;', '')); " +
                        "[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User'); ",
                        selectedJava.path.replace("\\", "\\\\")));
                }
                break;
            case "NODE":
                if (selectedNode != null) {
                    hasSelection = true;
                    command.append(String.format(
                        "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
                        "$newPath = ('%s;' + ($userPath -replace '(?i)C:\\\\\\\\Program Files.*?nodejs;', '')); " +
                        "[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User'); ",
                        selectedNode.path.replace("\\", "\\\\")));
                }
                break;
            case "DOTNET":
                if (selectedDotNet != null) {
                    hasSelection = true;
                    command.append("[System.Environment]::SetEnvironmentVariable('DOTNET_ROOT', 'C:\\Program Files\\dotnet', 'User'); ");
                }
                break;
            case "PYTHON":
                if (selectedPython != null) {
                    hasSelection = true;
                    command.append(String.format(
                        "[System.Environment]::SetEnvironmentVariable('PYTHON_HOME', '%s', 'User'); ",
                        selectedPython.path));
                    command.append(String.format(
                        "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
                        "$newPath = ('%s;%s\\Scripts;' + ($userPath -replace '(?i)C:\\\\\\\\(Python|Program Files.*?Python).*?;', '').Trim(';')); " +
                        "[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User'); ",
                        selectedPython.path.replace("\\", "\\\\"),
                        selectedPython.path.replace("\\", "\\\\")));
                }
                break;
            case "MAVEN":
                if (selectedMaven != null) {
                    hasSelection = true;
                    command.append(String.format(
                        "[System.Environment]::SetEnvironmentVariable('MAVEN_HOME', '%s', 'User'); ",
                        selectedMaven.path));
                    command.append(String.format(
                        "[System.Environment]::SetEnvironmentVariable('M2_HOME', '%s', 'User'); ",
                        selectedMaven.path));
                    command.append(String.format(
                        "$userPath = [System.Environment]::GetEnvironmentVariable('Path', 'User'); " +
                        "$newPath = ('%s\\bin;' + ($userPath -replace '(?i)C:\\\\\\\\.*?maven.*?\\\\\\\\bin;', '')); " +
                        "[System.Environment]::SetEnvironmentVariable('Path', $newPath, 'User'); ",
                        selectedMaven.path.replace("\\", "\\\\")));
                }
                break;
        }

        if (!hasSelection) {
            showStatus("⚠️ Selecione uma versão", "warning");
            return;
        }

        command.append("\"");

        try {
            Process process = Runtime.getRuntime().exec(command.toString());
            process.waitFor();
            showStatus("✅ Alterações aplicadas! Reinicie o terminal/IDE.", "success");
            
            // Atualizar o status da versão atual e re-detectar versões após aplicar mudanças
            new Thread(() -> {
                try {
                    // Delay para garantir que as variáveis foram atualizadas
                    Thread.sleep(800);
                    
                    // Re-detectar versões e atualizar UI
                    switch (techType) {
                        case "JAVA":
                            detectJavaVersions();
                            javafx.application.Platform.runLater(() -> {
                                updateCurrentJavaLabel();
                                renderJavaVersions();
                            });
                            break;
                        case "NODE":
                            detectNodeVersions();
                            javafx.application.Platform.runLater(() -> {
                                updateCurrentNodeLabel();
                                renderNodeVersions();
                            });
                            break;
                        case "DOTNET":
                            detectDotNetVersions();
                            javafx.application.Platform.runLater(() -> {
                                updateCurrentDotNetLabel();
                                renderDotNetVersions();
                            });
                            break;
                        case "PYTHON":
                            detectPythonVersions();
                            javafx.application.Platform.runLater(() -> {
                                updateCurrentPythonLabel();
                                renderPythonVersions();
                            });
                            break;
                        case "MAVEN":
                            detectMavenVersions();
                            javafx.application.Platform.runLater(() -> {
                                updateCurrentMavenLabel();
                                renderMavenVersions();
                            });
                            break;
                    }
                } catch (InterruptedException e) {
                    // Ignorar
                }
            }).start();
            
        } catch (Exception e) {
            showStatus("❌ Erro: " + e.getMessage(), "error");
        }
    }

    private void detectJavaVersions() {
        javaVersions.clear();
        selectedJava = null;
        
        // Ler JAVA_HOME do registro USER (onde salvamos)
        String currentJavaHome = null;
        try {
            Process process = Runtime.getRuntime().exec("powershell -Command \"[System.Environment]::GetEnvironmentVariable('JAVA_HOME', 'User')\"");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            currentJavaHome = reader.readLine();
            if (currentJavaHome != null) {
                currentJavaHome = currentJavaHome.trim();
            }
        } catch (Exception e) {
            // Fallback para System.getenv
            currentJavaHome = System.getenv("JAVA_HOME");
        }
        
        String[] basePaths = {
            "C:\\Program Files\\Eclipse Adoptium",
            "C:\\Program Files\\Java",
            "C:\\Program Files\\OpenJDK"
        };

        for (String basePath : basePaths) {
            File baseDir = new File(basePath);
            if (baseDir.exists() && baseDir.isDirectory()) {
                File[] dirs = baseDir.listFiles(File::isDirectory);
                if (dirs != null) {
                    for (File dir : dirs) {
                        File javaExe = new File(dir, "bin\\java.exe");
                        if (javaExe.exists()) {
                            try {
                                String version = getJavaVersion(javaExe.getAbsolutePath());
                                boolean isCurrent = currentJavaHome != null && dir.getAbsolutePath().equalsIgnoreCase(currentJavaHome);
                                javaVersions.add(new JavaVersion(dir.getName(), dir.getAbsolutePath(), version, isCurrent));
                                if (isCurrent) {
                                    selectedJava = javaVersions.get(javaVersions.size() - 1);
                                }
                            } catch (Exception e) {
                                // Ignorar
                            }
                        }
                    }
                }
            }
        }

        updateCurrentJavaLabel();
    }

    private void detectNodeVersions() {
        nodeVersions.clear();
        selectedNode = null;
        
        String[] basePaths = {
            "C:\\Program Files\\nodejs"
        };

        String currentNodePath = getNodePath();

        for (String basePath : basePaths) {
            File nodeExe = new File(basePath, "node.exe");
            if (nodeExe.exists()) {
                try {
                    String version = getNodeVersion(nodeExe.getAbsolutePath());
                    boolean isCurrent = basePath.equalsIgnoreCase(currentNodePath);
                    nodeVersions.add(new NodeVersion("Node.js", basePath, version, isCurrent));
                    if (isCurrent) {
                        selectedNode = nodeVersions.get(nodeVersions.size() - 1);
                    }
                } catch (Exception e) {
                    // Ignorar
                }
            }
        }

        updateCurrentNodeLabel();
    }

    private void detectDotNetVersions() {
        dotnetVersions.clear();
        selectedDotNet = null;
        
        String dotnetPath = "C:\\Program Files\\dotnet\\sdk";
        File sdkDir = new File(dotnetPath);
        
        String currentDotNetVersion = getCurrentDotNetVersion();
        
        if (sdkDir.exists() && sdkDir.isDirectory()) {
            File[] versions = sdkDir.listFiles(File::isDirectory);
            if (versions != null) {
                for (File versionDir : versions) {
                    String versionName = versionDir.getName();
                    boolean isCurrent = versionName.equals(currentDotNetVersion);
                    dotnetVersions.add(new DotNetVersion(".NET SDK " + versionName, versionDir.getAbsolutePath(), versionName, isCurrent));
                    if (isCurrent) {
                        selectedDotNet = dotnetVersions.get(dotnetVersions.size() - 1);
                    }
                }
            }
        }
        
        updateCurrentDotNetLabel();
    }

    private void detectPythonVersions() {
        pythonVersions.clear();
        selectedPython = null;
        
        String[] basePaths = {
            "C:\\Python",
            "C:\\Program Files\\Python",
            System.getProperty("user.home") + "\\AppData\\Local\\Programs\\Python"
        };

        String currentPythonPath = getPythonPath();

        for (String basePath : basePaths) {
            File baseDir = new File(basePath);
            if (baseDir.exists() && baseDir.isDirectory()) {
                File[] dirs = baseDir.listFiles((dir, name) -> name.startsWith("Python") && new File(dir, name).isDirectory());
                if (dirs != null) {
                    for (File dir : dirs) {
                        File pythonExe = new File(dir, "python.exe");
                        if (pythonExe.exists()) {
                            try {
                                String version = getPythonVersion(pythonExe.getAbsolutePath());
                                boolean isCurrent = dir.getAbsolutePath().equalsIgnoreCase(currentPythonPath);
                                pythonVersions.add(new PythonVersion(dir.getName(), dir.getAbsolutePath(), version, isCurrent));
                                if (isCurrent) {
                                    selectedPython = pythonVersions.get(pythonVersions.size() - 1);
                                }
                            } catch (Exception e) {
                                // Ignorar
                            }
                        }
                    }
                }
            } else {
                File pythonExe = new File(basePath, "python.exe");
                if (pythonExe.exists()) {
                    try {
                        String version = getPythonVersion(pythonExe.getAbsolutePath());
                        boolean isCurrent = basePath.equalsIgnoreCase(currentPythonPath);
                        pythonVersions.add(new PythonVersion(new File(basePath).getName(), basePath, version, isCurrent));
                        if (isCurrent) {
                            selectedPython = pythonVersions.get(pythonVersions.size() - 1);
                        }
                    } catch (Exception e) {
                        // Ignorar
                    }
                }
            }
        }

        updateCurrentPythonLabel();
    }

    private void detectMavenVersions() {
        mavenVersions.clear();
        selectedMaven = null;
        
        String[] basePaths = {
            "C:\\Program Files\\Apache\\maven",
            "C:\\Program Files\\Maven",
            "C:\\apache-maven"
        };

        String currentMavenHome = System.getenv("MAVEN_HOME");
        if (currentMavenHome == null) {
            currentMavenHome = System.getenv("M2_HOME");
        }

        for (String basePath : basePaths) {
            File baseDir = new File(basePath);
            if (baseDir.exists() && baseDir.isDirectory()) {
                File[] dirs = baseDir.listFiles(File::isDirectory);
                if (dirs != null) {
                    for (File dir : dirs) {
                        File mvnCmd = new File(dir, "bin\\mvn.cmd");
                        if (mvnCmd.exists()) {
                            try {
                                String version = getMavenVersion(mvnCmd.getAbsolutePath());
                                boolean isCurrent = currentMavenHome != null && dir.getAbsolutePath().equalsIgnoreCase(currentMavenHome);
                                mavenVersions.add(new MavenVersion(dir.getName(), dir.getAbsolutePath(), version, isCurrent));
                                if (isCurrent) {
                                    selectedMaven = mavenVersions.get(mavenVersions.size() - 1);
                                }
                            } catch (Exception e) {
                                // Ignorar
                            }
                        }
                    }
                }
            } else {
                File mvnCmd = new File(basePath, "bin\\mvn.cmd");
                if (mvnCmd.exists()) {
                    try {
                        String version = getMavenVersion(mvnCmd.getAbsolutePath());
                        boolean isCurrent = currentMavenHome != null && basePath.equalsIgnoreCase(currentMavenHome);
                        mavenVersions.add(new MavenVersion(new File(basePath).getName(), basePath, version, isCurrent));
                        if (isCurrent) {
                            selectedMaven = mavenVersions.get(mavenVersions.size() - 1);
                        }
                    } catch (Exception e) {
                        // Ignorar
                    }
                }
            }
        }

        updateCurrentMavenLabel();
    }

    private String getJavaVersion(String javaExe) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{javaExe, "-version"});
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getErrorStream()));
            String line = reader.readLine();
            if (line != null && line.contains("version")) {
                return line.replaceAll(".*version \"(.*)\".*", "$1");
            }
        } catch (Exception e) {
            // Ignorar
        }
        return "Desconhecida";
    }

    private String getNodeVersion(String nodeExe) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{nodeExe, "-v"});
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String version = reader.readLine();
            return version != null ? version.trim() : "Desconhecida";
        } catch (Exception e) {
            // Ignorar
        }
        return "Desconhecida";
    }

    private String getNodePath() {
        try {
            Process process = Runtime.getRuntime().exec("where node");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String path = reader.readLine();
            if (path != null) {
                return new File(path).getParent();
            }
        } catch (Exception e) {
            // Ignorar
        }
        return "";
    }

    private String getCurrentDotNetVersion() {
        try {
            Process process = Runtime.getRuntime().exec("dotnet --version");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String version = reader.readLine();
            return version != null ? version.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private String getPythonVersion(String pythonExe) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{pythonExe, "--version"});
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String version = reader.readLine();
            return version != null ? version.replace("Python ", "").trim() : "Desconhecida";
        } catch (Exception e) {
            return "Desconhecida";
        }
    }

    private String getPythonPath() {
        try {
            Process process = Runtime.getRuntime().exec("where python");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String path = reader.readLine();
            if (path != null) {
                return new File(path).getParent();
            }
        } catch (Exception e) {
            // Ignorar
        }
        return "";
    }

    private String getMavenVersion(String mvnCmd) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{mvnCmd, "-v"});
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null && line.contains("Apache Maven")) {
                return line.replaceAll("Apache Maven (\\S+).*", "$1");
            }
        } catch (Exception e) {
            // Ignorar
        }
        return "Desconhecida";
    }

    private void updateCurrentJavaLabel() {
        if (currentJavaLabel == null) {
            currentJavaLabel = new Label();
        }
        try {
            // Obter JAVA_HOME do ambiente USER (onde fizemos a alteração)
            Process process = Runtime.getRuntime().exec("powershell -Command \"[System.Environment]::GetEnvironmentVariable('JAVA_HOME', 'User')\"");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            String javaHome = reader.readLine();
            
            if (javaHome != null && !javaHome.isEmpty()) {
                String javaExePath = javaHome + "\\bin\\java.exe";
                String version = getJavaVersion(javaExePath);
                currentJavaLabel.setText(String.format("📍 Versão Atual: %s | JAVA_HOME: %s", 
                    version, javaHome));
            } else {
                currentJavaLabel.setText("📍 Java não detectado ou JAVA_HOME não definido");
            }
            currentJavaLabel.setVisible(true);
        } catch (Exception e) {
            currentJavaLabel.setText("📍 Java não detectado");
            currentJavaLabel.setVisible(true);
        }
    }

    private void updateCurrentNodeLabel() {
        if (currentNodeLabel == null) {
            currentNodeLabel = new Label();
        }
        try {
            String version = getNodeVersion("node");
            String path = getNodePath();
            currentNodeLabel.setText(String.format("📍 Versão Atual: %s | Path: %s", version, path));
            currentNodeLabel.setVisible(true);
        } catch (Exception e) {
            currentNodeLabel.setText("📍 Node.js não detectado");
            currentNodeLabel.setVisible(true);
        }
    }

    private void updateCurrentDotNetLabel() {
        if (currentDotNetLabel == null) {
            currentDotNetLabel = new Label();
        }
        try {
            String version = getCurrentDotNetVersion();
            if (!version.isEmpty()) {
                currentDotNetLabel.setText(String.format("📍 Versão Atual: %s", version));
            } else {
                currentDotNetLabel.setText("📍 .NET SDK não detectado");
            }
            currentDotNetLabel.setVisible(true);
        } catch (Exception e) {
            currentDotNetLabel.setText("📍 .NET SDK não detectado");
            currentDotNetLabel.setVisible(true);
        }
    }

    private void updateCurrentPythonLabel() {
        if (currentPythonLabel == null) {
            currentPythonLabel = new Label();
        }
        try {
            String version = getPythonVersion("python");
            String path = getPythonPath();
            if (!path.isEmpty()) {
                currentPythonLabel.setText(String.format("📍 Versão Atual: %s | Path: %s", version, path));
            } else {
                currentPythonLabel.setText("📍 Python não detectado");
            }
            currentPythonLabel.setVisible(true);
        } catch (Exception e) {
            currentPythonLabel.setText("📍 Python não detectado");
            currentPythonLabel.setVisible(true);
        }
    }

    private void updateCurrentMavenLabel() {
        if (currentMavenLabel == null) {
            currentMavenLabel = new Label();
        }
        String mavenHome = System.getenv("MAVEN_HOME");
        if (mavenHome == null) {
            mavenHome = System.getenv("M2_HOME");
        }
        try {
            String version = getMavenVersion("mvn");
            if (mavenHome != null) {
                currentMavenLabel.setText(String.format("📍 Versão Atual: %s | MAVEN_HOME: %s", version, mavenHome));
            } else {
                currentMavenLabel.setText(String.format("📍 Versão Atual: %s | MAVEN_HOME: Não definido", version));
            }
            currentMavenLabel.setVisible(true);
        } catch (Exception e) {
            currentMavenLabel.setText("📍 Maven não detectado");
            currentMavenLabel.setVisible(true);
        }
    }

    private void renderJavaVersions() {
        javafx.application.Platform.runLater(() -> {
            javaContainer.getChildren().clear();
            
            if (javaVersions.isEmpty()) {
                Label noVersions = new Label("Nenhuma versão do Java encontrada");
                noVersions.setFont(Font.font("Segoe UI", 12));
                noVersions.setTextFill(Color.web("#666666"));
                javaContainer.getChildren().add(noVersions);
                currentJavaLabel.setVisible(false);
                return;
            }

            for (JavaVersion version : javaVersions) {
                javaContainer.getChildren().add(createVersionCard(version));
            }
            
            // Atualizar label após renderizar
            updateCurrentJavaLabel();
        });
    }

    private void renderNodeVersions() {
        javafx.application.Platform.runLater(() -> {
            nodeContainer.getChildren().clear();
            
            if (nodeVersions.isEmpty()) {
                Label noVersions = new Label("Nenhuma versão do Node.js encontrada");
                noVersions.setFont(Font.font("Segoe UI", 12));
                noVersions.setTextFill(Color.web("#666666"));
                nodeContainer.getChildren().add(noVersions);
                currentNodeLabel.setVisible(false);
                return;
            }

            for (NodeVersion version : nodeVersions) {
                nodeContainer.getChildren().add(createVersionCard(version));
            }
            
            updateCurrentNodeLabel();
        });
    }

    private void renderDotNetVersions() {
        javafx.application.Platform.runLater(() -> {
            dotnetContainer.getChildren().clear();
            
            if (dotnetVersions.isEmpty()) {
                Label noVersions = new Label("Nenhuma versão do .NET SDK encontrada");
                noVersions.setFont(Font.font("Segoe UI", 12));
                noVersions.setTextFill(Color.web("#666666"));
                dotnetContainer.getChildren().add(noVersions);
                currentDotNetLabel.setVisible(false);
                return;
            }

            for (DotNetVersion version : dotnetVersions) {
                dotnetContainer.getChildren().add(createVersionCard(version));
            }
            
            updateCurrentDotNetLabel();
        });
    }

    private void renderPythonVersions() {
        javafx.application.Platform.runLater(() -> {
            pythonContainer.getChildren().clear();
            
            if (pythonVersions.isEmpty()) {
                Label noVersions = new Label("Nenhuma versão do Python encontrada");
                noVersions.setFont(Font.font("Segoe UI", 12));
                noVersions.setTextFill(Color.web("#666666"));
                pythonContainer.getChildren().add(noVersions);
                currentPythonLabel.setVisible(false);
                return;
            }

            for (PythonVersion version : pythonVersions) {
                pythonContainer.getChildren().add(createVersionCard(version));
            }
            
            updateCurrentPythonLabel();
        });
    }

    private void renderMavenVersions() {
        javafx.application.Platform.runLater(() -> {
            mavenContainer.getChildren().clear();
            
            if (mavenVersions.isEmpty()) {
                Label noVersions = new Label("Nenhuma versão do Maven encontrada");
                noVersions.setFont(Font.font("Segoe UI", 12));
                noVersions.setTextFill(Color.web("#666666"));
                mavenContainer.getChildren().add(noVersions);
                currentMavenLabel.setVisible(false);
                return;
            }

            for (MavenVersion version : mavenVersions) {
                mavenContainer.getChildren().add(createVersionCard(version));
            }
            
            updateCurrentMavenLabel();
        });
    }

    private VBox createVersionCard(Object version) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.setMaxWidth(Double.MAX_VALUE);
        
        final boolean isCurrent = version instanceof JavaVersion ? ((JavaVersion)version).isCurrent :
                                 version instanceof NodeVersion ? ((NodeVersion)version).isCurrent :
                                 version instanceof DotNetVersion ? ((DotNetVersion)version).isCurrent :
                                 version instanceof PythonVersion ? ((PythonVersion)version).isCurrent :
                                 version instanceof MavenVersion ? ((MavenVersion)version).isCurrent : false;
        
        final boolean isSelected;
        if (version instanceof JavaVersion) {
            isSelected = selectedJava != null && selectedJava.path.equals(((JavaVersion)version).path);
        } else if (version instanceof NodeVersion) {
            isSelected = selectedNode != null && selectedNode.path.equals(((NodeVersion)version).path);
        } else if (version instanceof DotNetVersion) {
            isSelected = selectedDotNet != null && selectedDotNet.path.equals(((DotNetVersion)version).path);
        } else if (version instanceof PythonVersion) {
            isSelected = selectedPython != null && selectedPython.path.equals(((PythonVersion)version).path);
        } else if (version instanceof MavenVersion) {
            isSelected = selectedMaven != null && selectedMaven.path.equals(((MavenVersion)version).path);
        } else {
            isSelected = false;
        }
        
        final String baseStyle = isSelected ?
            "-fx-background-color: linear-gradient(to right, #667eea, #764ba2); " +
            "-fx-background-radius: 12; -fx-cursor: hand; " +
            "-fx-border-color: #4c51bf; -fx-border-width: 2; -fx-border-radius: 12;" :
            "-fx-background-color: linear-gradient(to right, #f5f7fa, #c3cfe2); " +
            "-fx-background-radius: 12; -fx-cursor: hand;";
        
        card.setStyle(baseStyle);

        final String name = version instanceof JavaVersion ? ((JavaVersion)version).name :
                           version instanceof NodeVersion ? ((NodeVersion)version).name :
                           version instanceof DotNetVersion ? ((DotNetVersion)version).name :
                           version instanceof PythonVersion ? ((PythonVersion)version).name :
                           version instanceof MavenVersion ? ((MavenVersion)version).name : "";
        String path = version instanceof JavaVersion ? ((JavaVersion)version).path :
                     version instanceof NodeVersion ? ((NodeVersion)version).path :
                     version instanceof DotNetVersion ? ((DotNetVersion)version).path :
                     version instanceof PythonVersion ? ((PythonVersion)version).path :
                     version instanceof MavenVersion ? ((MavenVersion)version).path : "";
        String versionStr = version instanceof JavaVersion ? ((JavaVersion)version).version :
                           version instanceof NodeVersion ? ((NodeVersion)version).version :
                           version instanceof DotNetVersion ? ((DotNetVersion)version).version :
                           version instanceof PythonVersion ? ((PythonVersion)version).version :
                           version instanceof MavenVersion ? ((MavenVersion)version).version : "";

        HBox headerBox = new HBox(8);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        nameLabel.setTextFill(isSelected ? Color.WHITE : Color.web("#333333"));

        Label versionLabel = new Label(versionStr);
        versionLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        versionLabel.setTextFill(isSelected ? Color.WHITE : Color.web("#667eea"));
        versionLabel.setPadding(new Insets(2, 8, 2, 8));
        versionLabel.setStyle(isSelected ?
            "-fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 10;" :
            "-fx-background-color: rgba(102, 126, 234, 0.2); -fx-background-radius: 10;");

        headerBox.getChildren().addAll(nameLabel, versionLabel);
        
        if (isSelected) {
            Label selectedBadge = new Label("✓ SELECIONADA");
            selectedBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
            selectedBadge.setTextFill(Color.WHITE);
            selectedBadge.setPadding(new Insets(2, 8, 2, 8));
            selectedBadge.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 10;");
            headerBox.getChildren().add(selectedBadge);
        }
        
        if (isCurrent && !isSelected) {
            Label currentBadge = new Label("● SISTEMA");
            currentBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
            currentBadge.setTextFill(Color.web("#38ef7d"));
            currentBadge.setPadding(new Insets(2, 8, 2, 8));
            currentBadge.setStyle("-fx-background-color: rgba(56, 239, 125, 0.2); -fx-background-radius: 10;");
            headerBox.getChildren().add(currentBadge);
        }

        Label pathLabel = new Label(path);
        pathLabel.setFont(Font.font("Segoe UI", 9));
        pathLabel.setTextFill(isSelected ? Color.web("rgba(255,255,255,0.85)") : Color.web("#666666"));
        pathLabel.setWrapText(true);
        pathLabel.setMaxWidth(Double.MAX_VALUE);

        card.getChildren().addAll(headerBox, pathLabel);

        card.setOnMouseClicked(e -> {
            if (version instanceof JavaVersion) {
                selectedJava = (JavaVersion)version;
                javaContainer.getChildren().clear();
                for (JavaVersion v : javaVersions) {
                    javaContainer.getChildren().add(createVersionCard(v));
                }
            } else if (version instanceof NodeVersion) {
                selectedNode = (NodeVersion)version;
                nodeContainer.getChildren().clear();
                for (NodeVersion v : nodeVersions) {
                    nodeContainer.getChildren().add(createVersionCard(v));
                }
            } else if (version instanceof DotNetVersion) {
                selectedDotNet = (DotNetVersion)version;
                dotnetContainer.getChildren().clear();
                for (DotNetVersion v : dotnetVersions) {
                    dotnetContainer.getChildren().add(createVersionCard(v));
                }
            } else if (version instanceof PythonVersion) {
                selectedPython = (PythonVersion)version;
                pythonContainer.getChildren().clear();
                for (PythonVersion v : pythonVersions) {
                    pythonContainer.getChildren().add(createVersionCard(v));
                }
            } else if (version instanceof MavenVersion) {
                selectedMaven = (MavenVersion)version;
                mavenContainer.getChildren().clear();
                for (MavenVersion v : mavenVersions) {
                    mavenContainer.getChildren().add(createVersionCard(v));
                }
            }
            showStatus("✓ Selecionado: " + name, "info");
        });

        card.setOnMouseEntered(e -> {
            if (!isSelected) {
                card.setStyle(baseStyle + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);");
            }
        });

        card.setOnMouseExited(e -> card.setStyle(baseStyle));

        return card;
    }

    private void showStatus(String message, String type) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);
        
        switch (type) {
            case "success":
                statusLabel.setStyle("-fx-background-color: #d4edda; -fx-text-fill: #155724; " +
                    "-fx-border-color: #c3e6cb; -fx-border-width: 2; -fx-background-radius: 8; -fx-border-radius: 8;");
                break;
            case "error":
                statusLabel.setStyle("-fx-background-color: #f8d7da; -fx-text-fill: #721c24; " +
                    "-fx-border-color: #f5c6cb; -fx-border-width: 2; -fx-background-radius: 8; -fx-border-radius: 8;");
                break;
            case "warning":
            case "info":
                statusLabel.setStyle("-fx-background-color: #d1ecf1; -fx-text-fill: #0c5460; " +
                    "-fx-border-color: #bee5eb; -fx-border-width: 2; -fx-background-radius: 8; -fx-border-radius: 8;");
                break;
        }
        
        if (type.equals("info")) {
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    javafx.application.Platform.runLater(() -> statusLabel.setVisible(false));
                } catch (InterruptedException e) {
                    // Ignorar
                }
            }).start();
        }
    }

    static class JavaVersion {
        String name;
        String path;
        String version;
        boolean isCurrent;

        JavaVersion(String name, String path, String version, boolean isCurrent) {
            this.name = name;
            this.path = path;
            this.version = version;
            this.isCurrent = isCurrent;
        }
    }

    static class NodeVersion {
        String name;
        String path;
        String version;
        boolean isCurrent;

        NodeVersion(String name, String path, String version, boolean isCurrent) {
            this.name = name;
            this.path = path;
            this.version = version;
            this.isCurrent = isCurrent;
        }
    }

    static class DotNetVersion {
        String name;
        String path;
        String version;
        boolean isCurrent;

        DotNetVersion(String name, String path, String version, boolean isCurrent) {
            this.name = name;
            this.path = path;
            this.version = version;
            this.isCurrent = isCurrent;
        }
    }

    static class PythonVersion {
        String name;
        String path;
        String version;
        boolean isCurrent;

        PythonVersion(String name, String path, String version, boolean isCurrent) {
            this.name = name;
            this.path = path;
            this.version = version;
            this.isCurrent = isCurrent;
        }
    }

    static class MavenVersion {
        String name;
        String path;
        String version;
        boolean isCurrent;

        MavenVersion(String name, String path, String version, boolean isCurrent) {
            this.name = name;
            this.path = path;
            this.version = version;
            this.isCurrent = isCurrent;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
