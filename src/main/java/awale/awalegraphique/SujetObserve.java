package awale.awalegraphique;

import java.util.ArrayList;

/**
 * La classe SujetObservé
 */
public class SujetObserve {
    private final transient ArrayList<Observateur> observateurs;

    public SujetObserve() {
        observateurs = new ArrayList<>(10);
    }

    /**
     * Fonction qui ajoute un observateur à ce sujetObservé.
     *
     * @param obs l'observateur
     */
    public void ajouterObservateurs(Observateur obs) {
        observateurs.add(obs);
    }

    /**
     * Procédure qui notifie les observateurs d'un changement dans le modèle qui nécessite que l'interface graphique change.
     */
    public void notifierObservateur() {
        for (Observateur obs : observateurs)
            obs.reagir();
    }
}