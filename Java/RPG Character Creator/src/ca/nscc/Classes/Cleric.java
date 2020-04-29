package ca.nscc.Classes;

import ca.nscc.Player;

public class Cleric extends Player {
    private String className = Cleric.class.getSimpleName();

    public Cleric(int weaponID, String characterName, int hitPoints, int defence, int agility, int baseAttack) {
        super(weaponID, characterName, hitPoints, defence, agility, baseAttack);
    }

    public Cleric(int weaponID, String characterName) {
        super(weaponID, characterName, 20, 40, 100, 40);
    }

    public String getClassName() {
        return className;
    }
}
