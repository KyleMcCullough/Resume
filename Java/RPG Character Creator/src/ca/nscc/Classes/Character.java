package ca.nscc.Classes;

public abstract class Character {
    private String characterName;
    private int hitPoints, defence, agility, baseAttack;

    public Character(String characterName, int hitPoints, int defence, int agility, int baseAttack) {
        this.characterName = characterName;
        this.hitPoints = hitPoints;
        this.defence = defence;
        this.agility = agility;
        this.baseAttack = baseAttack;
    }

    public String getCharacterName() {
        return "Player: " + this.characterName;
    }

    public String getName() {
        return this.characterName;
    }

    public boolean checkActive() {
        return this.agility != 0;
    }

    public String getStats() {
        return "HP: " + this.hitPoints + "\tDefence: " + this.defence + "\tAgility: " + this.agility + "\tBase Attack: " + this.baseAttack;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getDefence() {
        return defence;
    }

    public int getAgility() {
        return agility;
    }

    public int getBaseAttack() {
        return baseAttack;
    }
}
