package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.defensive.Potion;
import serenadebird.pipboysquest.equipment.offensive.Spell;

public class Wizard extends Character {
    public Wizard(String name) {
        super("Wizard", name);
        setHealthLevel(100);
        setAttackStrength(12);
        setOffensiveEquipment(new Spell("Gamma Ray", 6));
        setDefensiveEquipment(new Potion("Stimpak", 4));
    }

    @Override
    public String getSpecialAction() {
        return "Concentration arcanique";
    }

    @Override
    public String toString() {
        return super.toString() + ", Action speciale : " + getSpecialAction();
    }
}
