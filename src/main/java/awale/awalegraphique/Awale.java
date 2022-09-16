package awale.awalegraphique;

import awale.awalegraphique.partie.Partie;
import awale.awalegraphique.partie.joueur.IA;
import awale.awalegraphique.partie.joueur.Joueur;
import awale.awalegraphique.partie.plateau.Plateau;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Awale extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Partie partie = new Partie(new Plateau(4), new Joueur(1), new IA(0), new Random().nextBoolean() ? 0 : 1);

        FXMLLoader fxmlLoader = new FXMLLoader(Awale.class.getResource("awale.fxml"));

        ControlleurPrincipal ControlleurPrincipal = new ControlleurPrincipal(partie);

        fxmlLoader.setControllerFactory(ic -> {
            if (ic.equals(ControlleurPrincipal.class)) return ControlleurPrincipal;
            return null;
        });

        Scene scene = new Scene(fxmlLoader.load(), 521, 271);
        stage.setTitle("Awale");
        stage.setScene(scene);
        stage.show();
        partie.notifierObservateur(); // On met à jour l'interface avec les données de début de partie
    }

    public static void main(String[] args) {
        launch();
    }
}