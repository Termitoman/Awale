package awale.awalegraphique;

import awale.awalegraphique.partie.Partie;
import awale.awalegraphique.partie.joueur.IA;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ControlleurPrincipal implements Observateur{
    @FXML
    public Text textc5l1;
    @FXML
    public Text textc4l1;
    @FXML
    public Text textc3l1;
    @FXML
    public Text textc2l1;
    @FXML
    public Text textc1l1;
    @FXML
    public Text textc0l1;
    @FXML
    public Text textc5l0;
    @FXML
    public Text textc4l0;
    @FXML
    public Text textc3l0;
    @FXML
    public Text textc2l0;
    @FXML
    public Text textc1l0;
    @FXML
    public Text textc0l0;
    @FXML
    public Text grenierIA;
    @FXML
    public Text grenierJoueur;

    @FXML
    public Circle cercle0l0;
    @FXML
    public Circle cercle1l0;
    @FXML
    public Circle cercle2l0;
    @FXML
    public Circle cercle3l0;
    @FXML
    public Circle cercle4l0;
    @FXML
    public Circle cercle5l0;
    @FXML
    public Circle cercle0l1;
    @FXML
    public Circle cercle1l1;
    @FXML
    public Circle cercle2l1;
    @FXML
    public Circle cercle3l1;
    @FXML
    public Circle cercle4l1;
    @FXML
    public Circle cercle5l1;

    @FXML
    public Text informations;
    @FXML
    public TextField profondeur;
    @FXML
    public Text infoDebut;

    private final Partie partie;
    private int profondeurIA = 0;



    public ControlleurPrincipal(Partie partie) {
        partie.ajouterObservateurs(this);
        this.partie = partie;
    }

    @FXML
    public void clicSurCercle0l1(MouseEvent mouseEvent) {
        partie.jouerCoup(0);
        partie.notifierObservateur();
    }

    @FXML
    public void clicSurCercle1l1(MouseEvent mouseEvent) {
        partie.jouerCoup(1);
        partie.notifierObservateur();
    }

    @FXML
    public void clicSurCercle2l1(MouseEvent mouseEvent) {
        partie.jouerCoup(2);
        partie.notifierObservateur();
    }

    @FXML
    public void clicSurCercle3l1(MouseEvent mouseEvent) {
        partie.jouerCoup(3);
        partie.notifierObservateur();
    }

    @FXML
    public void clicSurCercle4l1(MouseEvent mouseEvent) {
        partie.jouerCoup(4);
        partie.notifierObservateur();
    }

    @FXML
    public void clicSurCercle5l1(MouseEvent mouseEvent) {
        partie.jouerCoup(5);
        partie.notifierObservateur();
    }

    @Override
    public void reagir() {
        grenierJoueur.setText(String.valueOf(partie.getJoueur().getGrenier()));
        grenierIA.setText(String.valueOf(partie.getIa().getGrenier()));

        raffraichirPlateau();

        if (partie.isFinDePartie()) {
            informations.setText("C'est la fin de la partie ! " + partie.afficherGagnant());

            desactiverBoutons();

        } else {
            if (partie.getLigneJoueurActuel() == partie.getIa().getNumLigne()) {
                //On utilise un Thread pour pouvoir mettre le jeu en pause (nécessaire avec javaFX)
                Thread taskThread = new Thread(() -> {
                    try {
                        informations.setText("C'est à l'IA de jouer !");

                        desactiverBoutons();

                        //Petite pause pour permettre au joueur de bien voir l'IA jouer.
                        Thread.sleep(3000);

                        int coupAjouer = IA.minimax(partie, profondeurIA);
                        if (coupAjouer != -1)
                            partie.jouerCoup(coupAjouer);
                        else
                            System.out.println("L'ia s'est trompé !");
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    Platform.runLater(partie::notifierObservateur);
                });
                taskThread.start();
            } else {
                informations.setText("C'est à vous de jouer ! Choisissez un coup parmi les cercles colorés et cliquez dessus pour le jouer !");

                afficherCouleurs();
            }
        }
    }

    private void desactiverBoutons() {
        cercle0l1.setFill(new Color(0, 0, 0, 0));
        cercle0l1.setDisable(true);
        textc0l1.setDisable(true);

        cercle1l1.setFill(new Color(0, 0, 0, 0));
        cercle1l1.setDisable(true);
        textc1l1.setDisable(true);

        cercle2l1.setFill(new Color(0, 0, 0, 0));
        cercle2l1.setDisable(true);
        textc2l1.setDisable(true);

        cercle3l1.setFill(new Color(0, 0, 0, 0));
        cercle3l1.setDisable(true);
        textc3l1.setDisable(true);

        cercle4l1.setFill(new Color(0, 0, 0, 0));
        cercle4l1.setDisable(true);
        textc4l1.setDisable(true);

        cercle5l1.setFill(new Color(0, 0, 0, 0));
        cercle5l1.setDisable(true);
        textc5l1.setDisable(true);
    }

    private void raffraichirPlateau() {
        textc0l0.setText(String.valueOf(partie.getPlateau().getPlateau()[0][0]));
        textc1l0.setText(String.valueOf(partie.getPlateau().getPlateau()[0][1]));
        textc2l0.setText(String.valueOf(partie.getPlateau().getPlateau()[0][2]));
        textc3l0.setText(String.valueOf(partie.getPlateau().getPlateau()[0][3]));
        textc4l0.setText(String.valueOf(partie.getPlateau().getPlateau()[0][4]));
        textc5l0.setText(String.valueOf(partie.getPlateau().getPlateau()[0][5]));
        textc0l1.setText(String.valueOf(partie.getPlateau().getPlateau()[1][0]));
        textc1l1.setText(String.valueOf(partie.getPlateau().getPlateau()[1][1]));
        textc2l1.setText(String.valueOf(partie.getPlateau().getPlateau()[1][2]));
        textc3l1.setText(String.valueOf(partie.getPlateau().getPlateau()[1][3]));
        textc4l1.setText(String.valueOf(partie.getPlateau().getPlateau()[1][4]));
        textc5l1.setText(String.valueOf(partie.getPlateau().getPlateau()[1][5]));
    }

    private void afficherCouleurs() {
        ArrayList<Integer> coupsPossibles = partie.coupsPossibles();

        if (coupsPossibles.contains(0)) {
            cercle0l1.setFill(new Color(0, 0.5, 0.5, 0.5));
            cercle0l1.setDisable(false);
            textc0l1.setDisable(false);
        } else {
            cercle0l1.setFill(new Color(0, 0, 0, 0));
            cercle0l1.setDisable(true);
            textc0l1.setDisable(true);
        }
        if (coupsPossibles.contains(1)) {
            cercle1l1.setFill(new Color(0, 0.5, 0.5, 0.5));
            cercle1l1.setDisable(false);
            textc1l1.setDisable(false);
        } else {
            cercle1l1.setFill(new Color(0, 0, 0, 0));
            cercle1l1.setDisable(true);
            textc1l1.setDisable(true);
        }
        if (coupsPossibles.contains(2)) {
            cercle2l1.setFill(new Color(0, 0.5, 0.5, 0.5));
            cercle2l1.setDisable(false);
            textc2l1.setDisable(false);
        } else {
            cercle2l1.setFill(new Color(0, 0, 0, 0));
            cercle2l1.setDisable(true);
            textc2l1.setDisable(true);
        }
        if (coupsPossibles.contains(3)) {
            cercle3l1.setFill(new Color(0, 0.5, 0.5, 0.5));
            cercle3l1.setDisable(false);
            textc3l1.setDisable(false);
        } else {
            cercle3l1.setFill(new Color(0, 0, 0, 0));
            cercle3l1.setDisable(true);
            textc3l1.setDisable(true);
        }
        if (coupsPossibles.contains(4)) {
            cercle4l1.setFill(new Color(0, 0.5, 0.5, 0.5));
            cercle4l1.setDisable(false);
            textc4l1.setDisable(false);
        } else {
            cercle4l1.setFill(new Color(0, 0, 0, 0));
            cercle4l1.setDisable(true);
            textc4l1.setDisable(true);
        }
        if (coupsPossibles.contains(5)) {
            cercle5l1.setFill(new Color(0, 0.5, 0.5, 0.5));
            cercle5l1.setDisable(false);
            textc5l1.setDisable(false);
        } else {
            cercle5l1.setFill(new Color(0, 0, 0, 0));
            cercle5l1.setDisable(true);
            textc5l1.setDisable(true);
        }
    }

    public void nouvelleProfondeur(ActionEvent actionEvent) {
        //On demande la profondeur de recherche avant de commencer la partie
        profondeurIA = Integer.parseInt(String.valueOf(profondeur.getCharacters()));
        System.out.println("Changement de profondeur : " + profondeurIA);
    }
}