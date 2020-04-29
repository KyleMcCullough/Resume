package ca.nscc.Classes;

import ca.nscc.Player;

public class Wizard extends Player {
    private String className = Wizard.class.getSimpleName();

    public Wizard(int weaponID, String characterName, int hitPoints, int defence, int agility, int baseAttack) {
        super(weaponID, characterName, hitPoints, defence, agility, baseAttack);
    }

    public Wizard(int weaponID, String characterName) {
        super(weaponID, characterName, 40, 20, 60, 100);
    }

    public String getClassName() {
        return className;
    }
}
