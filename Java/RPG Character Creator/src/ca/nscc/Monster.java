package ca.nscc;

import ca.nscc.Classes.Character;

public class Monster extends Character {
    private String enemyType;

    public Monster(String characterName, int hitPoints, int defence, int agility, int baseAttack) {
        super(characterName, hitPoints, defence, agility, baseAttack);
    }

    public String getCharacterName() {
        return "Monster: " + this.getName();
    }
}
