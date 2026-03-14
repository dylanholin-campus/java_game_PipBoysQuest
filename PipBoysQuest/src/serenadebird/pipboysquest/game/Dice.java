package serenadebird.pipboysquest.game;

import java.util.Random;

/**
 * Modele un de virtuel configurable.
 */
public class Dice {
    // Borne minimale incluse.
    private int min = 1;
    // Borne maximale incluse.
    private int max = 6;
    // Generateur pseudo-aleatoire utilise pour les tirages.
    private Random random = new Random();

    /**
     * Construit un de standard (1-6).
     */
    public Dice() {
        // Conserve les bornes par defaut 1..6.
    }

    /**
     * Construit un de avec des bornes personnalisees.
     *
     * @param min valeur minimale
     * @param max valeur maximale
     */
    public Dice(int min, int max) {
        // Affecte les bornes personnalisees du de.
        this.min = min;
        this.max = max;
    }

    /**
     * Lance le de.
     *
     * @return valeur aleatoire comprise entre min et max
     */
    public int roll() {
        // Tirage uniforme dans l'intervalle [min, max].
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Retourne la borne minimale utilisee par le de.
     *
     * @return borne minimale
     */
    public int getMin() { return min; }

    /**
     * Met a jour la borne minimale du de.
     *
     * @param min nouvelle borne minimale
     */
    public void setMin(int min) { this.min = min; }

    /**
     * Retourne la borne maximale utilisee par le de.
     *
     * @return borne maximale
     */
    public int getMax() { return max; }

    /**
     * Met a jour la borne maximale du de.
     *
     * @param max nouvelle borne maximale
     */
    public void setMax(int max) { this.max = max; }

    /**
     * Retourne le generateur aleatoire associe.
     *
     * @return generateur aleatoire
     */
    public Random getRandom() { return random; }

    /**
     * Remplace le generateur aleatoire associe.
     *
     * @param random nouveau generateur aleatoire
     */
    public void setRandom(Random random) { this.random = random; }

    /**
     * Retourne une representation textuelle du de.
     *
     * @return description de l'instance
     */
    @Override
    public String toString() {
        // Resume compact des bornes actuelles du de.
        return "Dice{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
