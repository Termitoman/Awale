package awale.awalegraphique;


/**
 * L'interface Observateur
 */
public interface Observateur {
    /**
     * Procédure qui est appelée quand un changement du modèle nécessite un changement dans l'affichage.
     */
    void reagir();
}