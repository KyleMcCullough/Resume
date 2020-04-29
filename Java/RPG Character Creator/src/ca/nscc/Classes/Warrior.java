package ca.nscc.Classes;

import ca.nscc.Player;

public class Warrior extends Player {
    private String className = Warrior.class.getSimpleName();

    public Warrior(int weaponID, String characterName, int hitPoints, int defence, int agility, int baseAttack) {
        super(weaponID, characterName, hitPoints, defence, agility, baseAttack);
    }

    public Warrior(int weaponID, String characterName) {
        super(weaponID, characterName, 100, 80, 20,60);
    }

    public String getClassName() {
        return className;
    }
}
