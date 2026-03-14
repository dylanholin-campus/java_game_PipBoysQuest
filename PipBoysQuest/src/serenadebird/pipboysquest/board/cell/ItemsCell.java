package serenadebird.pipboysquest.board.cell;

import serenadebird.pipboysquest.character.Character;
import serenadebird.pipboysquest.character.Wizard;
import serenadebird.pipboysquest.equipment.DefensiveEquipment;
import serenadebird.pipboysquest.equipment.OffensiveEquipment;
import serenadebird.pipboysquest.equipment.defensive.Potion;
import serenadebird.pipboysquest.equipment.offensive.Weapon;
import serenadebird.pipboysquest.game.Menu;

/**
 * Case contenant un loot offensif ou defensif.
 *
 * <p>Le loot peut etre modele soit par un objet Equipment (mode recommande),
 * soit par un triplet String/type/valeur (mode legacy).</p>
 */
public class ItemsCell extends Cell {
    // Type legacy (POTION/WEAPON/SPELL) lorsque la case n'embarque pas d'objet Equipment.
    private String itemType;
    // Nom lisible de l'objet affiche dans les logs.
    private String itemName;
    // Valeur numerique du loot (attaque ou defense selon le type).
    private int itemValue;
    // Objet concret moderne (OffensiveEquipment ou DefensiveEquipment).
    private Object item;
    // Empeche de recuperer plusieurs fois le meme loot.
    private boolean looted;

    // Constructeur legacy: initialise la case a partir de champs texte.
    public ItemsCell(int position, String itemType, String itemName, int itemValue) {
        super(position);
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    // Constructeur moderne pour un loot offensif.
    public ItemsCell(int position, OffensiveEquipment item) {
        super(position);
        this.item = item;
        // Duplication volontaire en champs simples pour garder un rendu uniforme.
        this.itemType = item.getType();
        this.itemName = item.getName();
        this.itemValue = item.getAttackLevel();
    }

    // Constructeur moderne pour un loot defensif.
    public ItemsCell(int position, DefensiveEquipment item) {
        super(position);
        this.item = item;
        // Duplication volontaire en champs simples pour garder un rendu uniforme.
        this.itemType = item.getType();
        this.itemName = item.getName();
        this.itemValue = item.getDefenseLevel();
    }

    // Accesseur du nom de loot (utile pour serialisation/debug).
    public String getItemName() {return itemName;}
    // Mutateur du nom de loot.
    public void setItemName(String itemName) {this.itemName = itemName;}

    // Accesseur de la valeur numerique du loot.
    public int getItemValue() {return itemValue;}
    // Mutateur de la valeur numerique du loot.
    public void setItemValue(int itemValue) {this.itemValue = itemValue;}

    // Accesseur du type de loot.
    public String getItemType() {return itemType;}
    // Mutateur du type de loot.
    public void setItemType(String itemType) {this.itemType = itemType;}

    // Retourne l'objet loot concret quand il existe.
    public Object getItem() { return item; }
    // Met a jour l'objet loot concret.
    public void setItem(Object item) { this.item = item; }

    @Override
    // Description courte de la case pour l'apercu plateau.
    public String toString() {
        if (item != null) {
            return "Case " + getPosition() + " : loot Fallout -> " + item;
        }
        return "Case " + getPosition() + " : objet a looter (" + itemType + " - " + itemName + " +" + itemValue + ")";
    }

    /**
     * Gere la resolution du loot quand le joueur arrive sur la case.
     *
     * <p>La case ne peut etre fouillee qu'une seule fois (flag looted).</p>
     */
    @Override
    public void interact(Character character, Menu menu) {
        // Garde-fou: une caisse deja ouverte ne redonne pas de loot.
        if (looted) {
            System.out.println("Cette caisse a deja ete fouillee.");
            return;
        }

        printLootHeader();

        // Branche moderne: gestion specialisee pour equipements offensifs.
        if (item instanceof OffensiveEquipment) {
            handleOffensiveLoot(character, (OffensiveEquipment) item, menu);
            return;
        }
        // Branche moderne: gestion specialisee pour equipements defensifs.
        if (item instanceof DefensiveEquipment) {
            handleDefensiveLoot(character, (DefensiveEquipment) item, menu);
            return;
        }

        // Fallback legacy si la case a ete creee avec l'ancien constructeur String.
        System.out.println("Vous trouvez: " + itemName + " (" + itemType + " +" + itemValue + ")");
        printCharacterSnapshot(character);
        if ("POTION".equalsIgnoreCase(itemType)) {
            System.out.println("Gain potentiel de vie: +" + itemValue);
            if (!menu.askYesNo("Ramasser l'objet ? (o/n): ")) {
                System.out.println(character.getName() + " n'utilise pas " + itemName + ".");
                return;
            }
            // Applique un soin avec plafonnement de classe.
            character.increaseHealthWithCap(itemValue);
            looted = true;
            System.out.println("Vie actuelle -> " + character.getHealthLevel());
            return;
        }
        // Arme legacy: utilisable par toutes les classes.
        if ("WEAPON".equalsIgnoreCase(itemType)) {
            System.out.println("Gain potentiel de force: +" + itemValue);
            if (!menu.askYesNo("Ramasser l'objet ? (o/n): ")) {
                System.out.println(character.getName() + " laisse " + itemName + " sur place.");
                return;
            }
            // Legacy: remplace l'arme courante au lieu d'empiler les bonus.
            character.setOffensiveEquipment(new Weapon(itemName, itemValue));
            character.setAttackStrength(character.computeAttackWithEquipment(character.getOffensiveEquipment()));
            looted = true;
            System.out.println("Force actuelle -> " + character.getAttackStrength());
            return;
        }
        // Sort legacy: uniquement pour Wizard.
        if ("SPELL".equalsIgnoreCase(itemType) && character instanceof Wizard) {
            System.out.println("Gain potentiel de force: +" + itemValue);
            if (!menu.askYesNo("Ramasser l'objet ? (o/n): ")) {
                System.out.println(character.getName() + " laisse " + itemName + " sur place.");
                return;
            }
            // Legacy: conserve la logique de remplacement d'equipement offensif.
            character.setOffensiveEquipment(new Weapon(itemName, itemValue));
            character.setAttackStrength(character.computeAttackWithEquipment(character.getOffensiveEquipment()));
            looted = true;
            System.out.println("Force actuelle -> " + character.getAttackStrength());
            return;
        }
        // Aucune regle de compatibilite valide.
        System.out.println("Loot incompatible avec votre classe.");
    }

    /**
     * Applique un loot offensif en validant la compatibilite classe/equipement:
     * tout personnage peut utiliser Weapon.
     */
    private void handleOffensiveLoot(Character character, OffensiveEquipment equipment, Menu menu) {
        // Regle metier: toutes les armes de ce catalogue sont utilisables par les deux classes.
        boolean canTake = equipment instanceof Weapon;

        // Refuse l'equipement si la classe du joueur n'est pas compatible.
        if (!canTake) {
            System.out.println("Loot incompatible: " + character.getType() + " ne peut pas utiliser " + equipment.getType() + ".");
            return;
        }

        System.out.println("[LOOT] Case " + getPosition() + " : loot Fallout -> " + equipment);
        System.out.println("Vous trouvez: " + equipment.getName() + " (" + equipment.getType() + " +" + equipment.getAttackLevel() + ")");
        printCharacterSnapshot(character);
        System.out.println("Offensif actuel: " + describeOffensiveEquipment(character));
        System.out.println("Gain potentiel de force: " + formatSignedDelta(computeAttackGain(character, equipment)));

        // Demande une confirmation explicite avant d'equiper.
        if (!menu.askYesNo("Ramasser l'objet ? (o/n): ")) {
            System.out.println(character.getName() + " laisse " + equipment.getName() + " sur place.");
            return;
        }

        // Capture l'ancienne valeur pour afficher un recap de progression.
        int oldAttack = character.getAttackStrength();
        // Equipe l'objet concret.
        character.setOffensiveEquipment(equipment);
        // Recalcule la force comme base + nouvelle arme (pas de cumul historique).
        character.setAttackStrength(character.computeAttackWithEquipment(equipment));
        // Marque la case comme pillee.
        looted = true;

        System.out.println(character.getName() + " equipe " + equipment.getName() + ".");
        System.out.println("Force: " + oldAttack + " -> " + character.getAttackStrength());
    }

    /**
     * Applique un loot defensif (potion/bouclier) et met a jour la vie sous cap.
     */
    private void handleDefensiveLoot(Character character, DefensiveEquipment equipment, Menu menu) {
        if (equipment instanceof Potion) {
            handlePotionLoot(character, equipment, menu);
            return;
        }

        System.out.println("[LOOT] Case " + getPosition() + " : loot Fallout -> " + equipment);
        System.out.println("Vous trouvez: " + equipment.getName() + " (" + equipment.getType() + " +" + equipment.getDefenseLevel() + ")");
        printCharacterSnapshot(character);
        System.out.println("Defensif actuel: " + describeDefensiveEquipment(character));
        System.out.println("Gain potentiel de vie: +" + computeHealthGain(character, equipment));

        // Demande une confirmation explicite avant d'utiliser le soin/protection.
        if (!menu.askYesNo("Ramasser l'objet ? (o/n): ")) {
            System.out.println(character.getName() + " n'utilise pas " + equipment.getName() + ".");
            return;
        }

        // Capture l'ancienne valeur pour afficher un recap de progression.
        int oldHealth = character.getHealthLevel();
        // Equipe l'objet defensif concret.
        character.setDefensiveEquipment(equipment);
        // Applique le bonus de vie sous cap de classe.
        character.increaseHealthWithCap(equipment.getDefenseLevel());
        // Marque la case comme pillee.
        looted = true;

        System.out.println(character.getName() + " utilise " + equipment.getName() + ".");
        System.out.println("Vie: " + oldHealth + " -> " + character.getHealthLevel());
    }

    /**
     * Applique un loot de potion en mode consommable (sans comparaison d'armure).
     */
    private void handlePotionLoot(Character character, DefensiveEquipment potion, Menu menu) {
        System.out.println("[LOOT] Case " + getPosition() + " : loot Fallout -> " + potion);
        System.out.println("Vous trouvez: " + potion.getName() + " (+" + potion.getDefenseLevel() + " vie)");

        int oldHealth = character.getHealthLevel();
        int healGain = computeHealthGain(character, potion);
        System.out.println("Vie actuelle: " + oldHealth);
        System.out.println("Gain potentiel de vie: +" + healGain);

        if (!menu.askYesNo("Utiliser la potion ? (o/n): ")) {
            System.out.println(character.getName() + " garde " + potion.getName() + " pour plus tard.");
            return;
        }

        character.setDefensiveEquipment(potion);
        character.increaseHealthWithCap(potion.getDefenseLevel());
        looted = true;

        System.out.println("Vous utilisez " + potion.getName() + " et recuperez "
                + (character.getHealthLevel() - oldHealth) + " PV.");
        System.out.println("Vie: " + oldHealth + " -> " + character.getHealthLevel());
    }

    /**
     * Affiche un entete de loot proche de l'UX capturee sur la release.
     */
    private void printLootHeader() {
        System.out.println();
        System.out.println("----- LOOT -----");
    }

    /**
     * Affiche un resume compact des statistiques courantes du personnage.
     */
    private void printCharacterSnapshot(Character character) {
        System.out.println("Stats actuelles -> Vie: " + character.getHealthLevel() + ", Force: " + character.getAttackStrength());
    }

    /**
     * Retourne le libelle de l'equipement offensif actuellement equipe.
     */
    private String describeOffensiveEquipment(Character character) {
        OffensiveEquipment current = character.getOffensiveEquipment();
        if (current == null) {
            return "aucun";
        }
        return current.getName() + " (+" + current.getAttackLevel() + ")";
    }

    /**
     * Retourne le libelle de l'equipement defensif actuellement equipe.
     */
    private String describeDefensiveEquipment(Character character) {
        DefensiveEquipment current = character.getDefensiveEquipment();
        if (current == null) {
            return "aucun";
        }
        return current.getName() + " (+" + current.getDefenseLevel() + ")";
    }

    /**
     * Calcule le gain reel d'attaque avant plafonnement de classe.
     */
    private int computeAttackGain(Character character, OffensiveEquipment equipment) {
        int projectedAttack = character.computeAttackWithEquipment(equipment);
        return projectedAttack - character.getAttackStrength();
    }

    /**
     * Calcule le gain reel de vie avant plafonnement de classe.
     */
    private int computeHealthGain(Character character, DefensiveEquipment equipment) {
        int cappedHealth = Math.min(character.getHealthLevel() + Math.max(0, equipment.getDefenseLevel()), character.getMaxHealthLevel());
        return cappedHealth - character.getHealthLevel();
    }

    /**
     * Formate un delta avec son signe explicite.
     */
    private String formatSignedDelta(int delta) {
        if (delta > 0) {
            return "+" + delta;
        }
        return Integer.toString(delta);
    }
}
