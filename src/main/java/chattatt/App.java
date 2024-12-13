package chattatt;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import chattatt.Dao.MembreDaoImpl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        Text label = new Text("Ajouter Nouveau Membre");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        label.setFill(Color.DARKBLUE);


        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();

        String textFieldStyle = "-fx-background-radius: 10; -fx-padding: 10; -fx-border-radius: 10; -fx-border-color: lightgray;";
        nomField.setStyle(textFieldStyle);
        prenomField.setStyle(textFieldStyle);
        emailField.setStyle(textFieldStyle);
        phoneField.setStyle(textFieldStyle);


        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);
        grid.add(new Text("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Text("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Text("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Text("Téléphone:"), 0, 3);
        grid.add(phoneField, 1, 3);

        Button insertButton = new Button("Insérer");
        Button loadCsvButton = new Button("Charger Membres CSV");

 
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-size: 16px;";
        insertButton.setStyle(buttonStyle);
        loadCsvButton.setStyle(buttonStyle);

        insertButton.setOnAction(event -> {
            try {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();

                if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                    String randomIdentifiant = UUID.randomUUID().toString();

                    MembreDaoImpl membreDaoImpl = new MembreDaoImpl();
                    boolean exists = membreDaoImpl.doesIdentifiantExist(randomIdentifiant);
                    if (exists) {
                        randomIdentifiant = UUID.randomUUID().toString();
                    }

                    Membre membre = new Membre(randomIdentifiant, nom, prenom, email, phone);
                    membreDaoImpl.insertMembre(membre);
                    nomField.clear();
                    prenomField.clear();
                    emailField.clear();
                    phoneField.clear();
                } else {
                    Alert a=new Alert(AlertType.ERROR);
                    a.setContentText("Veuillez remplir tous les champs.");
                    a.show();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        });

        Text loadedMembersMessage = new Text();
        loadedMembersMessage.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        loadedMembersMessage.setFill(Color.DARKGREEN);

        loadCsvButton.setOnAction(event -> {
            Set<Membre> membres = chargerListeMembre("/Users/anaschattat/Documents/javafic/examen_blaanc/src/main/java/chattatt/fichier.csv");
            String message = "Nombre de membres chargés : " + membres.size()+"\n";
            for (Membre element : membres) {
            message=message+ element+"\n" ;
            }
            loadedMembersMessage.setText(message);
        });

        VBox vbox = new VBox(20, grid, insertButton, loadCsvButton, loadedMembersMessage);
        vbox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(label);
        pane.setCenter(vbox);
        BorderPane.setAlignment(label, Pos.CENTER);

        Scene scene = new Scene(pane, 800, 800);
        scene.setFill(Color.LIGHTGRAY); 
        primaryStage.setTitle("Examen-blanc");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Set<Membre> chargerListeMembre(String nomFichier) {
        Set<Membre> membres = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 4) {
                    String nom = data[0].trim();
                    String prenom = data[1].trim();
                    String email = data[2].trim();
                    String phone = data[3].trim();

                    Membre membre = new Membre(UUID.randomUUID().toString(), nom, prenom, email, phone);
                    membres.add(membre);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }

        return membres;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

