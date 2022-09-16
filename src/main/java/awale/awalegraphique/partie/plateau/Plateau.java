package awale.awalegraphique.partie.plateau;

import awale.awalegraphique.SujetObserve;

public class Plateau extends SujetObserve {
    private int[][] plateau;

    /**
     * Constructeur de la classe Plateau
     *
     * @param valDepart le nombre de graines que l'on mets dans chacune les cases du partie.plateau.
     */
    public Plateau (int valDepart) {
        plateau = new int[2][6];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                plateau[i][j] = valDepart;
            }
        }
    }

    public Plateau(Plateau plateau) {
        this.plateau = new int[2][6];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                 this.plateau[i][j] = plateau.getPlateau()[i][j];
            }
        }
    }

    public int[][] getPlateau() {
        return plateau;
    }

    public void setPlateau(int[][] plateau) {
        this.plateau = plateau;
    }

    /**
     * Fonction qui effectue une action "jouer" sur la case précisée en paramètres.
     *
     * @param ligne le numéro de ligne de la case.
     * @param col le numéro de la colonne de la case.
     * @return le nombre de graines collectées lors de l'action.
     */
    public int jouer(int ligne, int col) {
        //Initialisation des variables utiles
        int ligne_debut = ligne;
        int graines = viderCase(ligne, col); //On vide la case demandée

        //Parcours du partie.plateau pour déposer les graines
        for (int i = 0; i < graines; i++) {
            //On passe à la case suivante
            if (ligne == 0 && col == 0)
                ligne = 1;
            else if (ligne == 1 && col == 5)
                ligne = 0;
            else if (ligne == 0)
                col--;
            else
                col++;

            //On saute la case si c'est celle que l'on à vidé
            if (graines >= 12 && (i % 11 == 0 && i != 0)) {
                graines++;
            } else {
                plateau[ligne][col] += 1;
            }
        }

        //On gère la rafle
        Plateau temp = new Plateau(this);
        temp.rafler(ligne, col, ligne_debut);
        int graines_capturees = 0;
        if (!temp.ligneEstVide(ligne_debut == 0 ? 1 : 0))
            graines_capturees = this.rafler(ligne, col, ligne_debut);
        return graines_capturees;
    }

    private int rafler(int ligne, int col, int ligne_debut) {
        boolean peut_rafle = true;
        int graines_capturees = 0;
        while (peut_rafle) {
            //On vérifie si l'on peut rafler les graines de la case actuelle.
            if ((plateau[ligne][col] == 2 || plateau[ligne][col] == 3) && ligne_debut != ligne) {
                //Si on peut, on rafle.
                graines_capturees += plateau[ligne][col];
                plateau[ligne][col] = 0;
                //Puis on passe sur la case précédente
                if (ligne == 1 && col == 0)
                    ligne = 0;
                else if (ligne == 0 && col == 5)
                    ligne = 1;
                else if (ligne == 1)
                    col--;
                else
                    col++;
            } else {
                //Si on ne peut pas, on arrête de rafler
                peut_rafle = false;
            }
        }
        return graines_capturees;
    }

    /**
     * Fonction qui vide une case de ces graines et les renvoies.
     *
     * @param ligne le numéro de ligne de la case.
     * @param col le numéro de la colonne de la case.
     * @return le nombre de graines retirées de la case.
     */
    public int viderCase(int ligne, int col) {
        int temp = plateau[ligne][col];
        plateau[ligne][col] = 0;
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] t : plateau) {
            for (int x : t) {
                sb.append("(").append(x).append(") ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean ligneEstVide(int ligne) {
        boolean estVide = true;
        for (int i : plateau[ligne])
            if (i > 0) {
                estVide = false;
                break;
            }
        return estVide;
    }


}
