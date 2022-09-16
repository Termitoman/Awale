package awale.awalegraphique.partie.joueur;

public class Joueur {
    private int grenier = 0;
    private final int numLigne;

    public Joueur(int numLigne) {
        this.numLigne = numLigne;
    }

    public Joueur(Joueur joueur) {
        this.numLigne = joueur.getNumLigne();
        this.grenier = joueur.getGrenier();
    }

    public void ajouterAuGrenier(int n) {
        grenier += n;
    }

    public int getGrenier() {
        return grenier;
    }

    public int getNumLigne() {
        return numLigne;
    }
}
