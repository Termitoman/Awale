package awale.awalegraphique.partie.joueur;

import awale.awalegraphique.partie.Partie;
import awale.awalegraphique.partie.plateau.Plateau;

import java.util.ArrayList;
import java.util.HashMap;

public class IA extends Joueur{
    public IA(int numLigne) {
        super(numLigne);
    }

    public IA(IA ia) {
        super(ia);
    }

    public static int minimax(Partie etat, int c) {
        ArrayList<Partie> successeurs = new ArrayList<>();
        float score, scoreMax;

        HashMap<Partie, Integer> correspondance_coup_etat = new HashMap<>();

        ArrayList<Integer> coupsPossibles = etat.coupsPossibles();
        for(Integer i : coupsPossibles) {
            Partie copiePartie = new Partie(new Plateau(etat.getPlateau()), new Joueur(etat.getJoueur()), new IA(etat.getIa()), etat.getLigneJoueurActuel());
            copiePartie.jouerCoup(i);
            successeurs.add(copiePartie);
            correspondance_coup_etat.put(copiePartie,i);
        }

        scoreMax = Integer.MIN_VALUE;
        Partie e_sortie = null;

        System.out.println("-------------------------------");
        for (Partie successeur : successeurs) {
            score = evaluationAlphaBeta(c,successeur,Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("Score du successeur : " + score);
            if (score >= scoreMax) {
                e_sortie = successeur;
                scoreMax = score;
            }
        }

        return e_sortie == null ? -1 : correspondance_coup_etat.get(e_sortie);
    }

    public static float evaluationAlphaBeta(int c, Partie etat, float alpha, float beta) {
        ArrayList<Partie> successeurs = new ArrayList<>();
        float scoreMax, scoreMin;

        if(etat.isFinDePartie()) {
            return (etat.getGagnant() == etat.getJoueur().getNumLigne() ? Integer.MIN_VALUE : (etat.getGagnant() == etat.getIa().getNumLigne()) ? Integer.MAX_VALUE : 0);
        }
        if(c == 0) {
            return etat.eval0();
        }
        ArrayList<Integer> coupsPossibles = etat.coupsPossibles();
        for(Integer i : coupsPossibles) {
            Partie copiePartie = new Partie(new Plateau(etat.getPlateau()), new Joueur(etat.getJoueur()), new IA(etat.getIa()), etat.getLigneJoueurActuel());
            copiePartie.jouerCoup(i);
            successeurs.add(copiePartie);
        }
        if(etat.getLigneJoueurActuel() == etat.getIa().getNumLigne()) {
            scoreMax = Integer.MIN_VALUE;
            for (Partie successeur : successeurs) {
                scoreMax = Math.max(scoreMax, evaluationAlphaBeta(c-1,successeur,alpha,beta));
                if(scoreMax >= beta) {
                    return scoreMax;
                }
                alpha = Math.max(alpha, scoreMax);
            }
            return scoreMax;
        }
        else {
            scoreMin = Integer.MAX_VALUE;
            for (Partie successeur : successeurs) {
                scoreMin = Math.min(scoreMin, evaluationAlphaBeta(c-1,successeur,alpha,beta));
                if(scoreMin <= alpha) {
                    return scoreMin;
                }
                beta = Math.min(beta, scoreMin);
            }
            return scoreMin;
        }
    }
}
