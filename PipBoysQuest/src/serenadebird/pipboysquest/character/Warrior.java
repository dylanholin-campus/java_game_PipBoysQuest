package serenadebird.pipboysquest.character;

import serenadebird.pipboysquest.equipment.defensive.Shield;
import serenadebird.pipboysquest.equipment.offensive.Weapon;

public class Warrior extends Character {
    public Warrior(String name) {
        super("Warrior", name);
        setHealthLevel(120);
        setAttackStrength(15);
        setOffensiveEquipment(new Weapon("Laser Rifle", 5));
        setDefensiveEquipment(new Shield("Power Armor Shield", 10));
    }

    @Override
    public String getSpecialAction() {
        return "Charge brutale";
    }

    @Override
    public String toString() {
        return super.toString() + ", Action speciale : " + getSpecialAction();
    }
}
