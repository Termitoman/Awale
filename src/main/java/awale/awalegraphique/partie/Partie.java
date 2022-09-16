package awale.awalegraphique.partie;

import awale.awalegraphique.SujetObserve;
import awale.awalegraphique.partie.joueur.IA;
import awale.awalegraphique.partie.joueur.Joueur;
import awale.awalegraphique.partie.plateau.Plateau;

import java.util.ArrayList;

public class Partie extends SujetObserve {
    private final Plateau plateau;
    private final Joueur joueur;
    private final IA ia;
    private boolean finDePartie = false;
    private int ligneJoueurActuel;

    public Partie(Plateau plateau, Joueur joueur, IA ia, int premierJoueur) {
        this.plateau = plateau;
        this.joueur = joueur;
        this.ia = ia;
        this.ligneJoueurActuel = premierJoueur;
    }

    public boolean isFinDePartie() {
        return finDePartie;
    }

    public String afficherPlateau() {
        return "Plateau actuel :\n" + plateau.toString() ;
    }

    public ArrayList<Integer> coupsPossibles() {
        ArrayList<Integer> coups = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            Plateau temp = new Plateau(plateau);
            temp.jouer(ligneJoueurActuel, i);
            if (!temp.ligneEstVide(ligneJoueurActuel == 0 ? 1 : 0) && this.getPlateau().getPlateau()[ligneJoueurActuel][i] != 0)
                coups.add(i);
        }
        if (coups.size() == 0) {
            finDePartie = true;
            if (getPlateau().ligneEstVide(ligneJoueurActuel)) { //Si le joueur actuel n'a plus de graines
                //Alors, on fait ramasser à l'adversaire toutes ses graines.
                for (int i = 0; i < 6; i++) {
                    if (ligneJoueurActuel == joueur.getNumLigne())
                        ia.ajouterAuGrenier(plateau.viderCase(ia.getNumLigne(), i));
                    else
                        joueur.ajouterAuGrenier(plateau.viderCase(joueur.getNumLigne(), i));
                }
            } else { //Sinon, si le joueur en face n'a plus de graines et que l'on ne peut pas le nourrir.
                //Alors, on fait ramasser au joueur actuel toutes ses graines.
                for (int i = 0; i < 6; i++) {
                    if (ligneJoueurActuel == joueur.getNumLigne())
                        joueur.ajouterAuGrenier(plateau.viderCase(joueur.getNumLigne(), i));
                    else
                        ia.ajouterAuGrenier(plateau.viderCase(ia.getNumLigne(), i));
                }
            }
        }
        return coups;
    }

    public void jouerCoup(int col) {
        if (ligneJoueurActuel == joueur.getNumLigne())
            joueur.ajouterAuGrenier(plateau.jouer(ligneJoueurActuel, col));
        else
            ia.ajouterAuGrenier(plateau.jouer(ligneJoueurActuel, col));

        //La partie se termine lorsque l'un des joueurs à 25 graines ou plus dans son grenier.
        if (joueur.getGrenier() >= 25)
            finDePartie = true;
        else if (ia.getGrenier() >= 25)
            finDePartie = true;
        else
            ligneJoueurActuel = ligneJoueurActuel == 0 ? 1 : 0;
    }

    public String afficherGagnant() {
        return (joueur.getGrenier() == ia.getGrenier() ? "Personne " : (joueur.getGrenier() > ia.getGrenier() ? "Le joueur" : "L'IA")) + " à gagné !";
    }

    public int getGagnant() {
        return joueur.getGrenier() == ia.getGrenier() ? -1 : (joueur.getGrenier() > ia.getGrenier() ? joueur.getNumLigne() : ia.getNumLigne());
    }

    public String afficherGreniers() {
        return "Le joueur à " + joueur.getGrenier() + " graines dans son grenier.\n" +
                "L'IA à " + ia.getGrenier() + " graines dans son grenier.\n";
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public IA getIa() {
        return ia;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public int getLigneJoueurActuel() {
        return ligneJoueurActuel;
    }

    public float eval0() {
        return ia.getGrenier() - joueur.getGrenier();
    }
}
