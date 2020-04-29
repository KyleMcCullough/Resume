package ca.nscc;

import ca.nscc.Classes.Character;
import ca.nscc.Weapons.Dagger;
import ca.nscc.Weapons.Mace;
import ca.nscc.Weapons.Sword;
import ca.nscc.Weapons.Weapon;

public class Player extends Character {
    Dagger dagger;
    Sword sword;
    Mace mace;

    public Player(int weaponID, String characterName, int hitPoints, int defence, int agility, int baseAttack) {
        super(characterName, hitPoints, defence, agility, baseAttack);

        if (weaponID == 3) {
            dagger = new Dagger();
        } else if (weaponID == 2) {
            sword = new Sword();
        } else {
            mace = new Mace();
        }
    }

    public String getWeaponStats() {
        try {
            return "Weapon: " + dagger.getWeaponName() + "\tDamage: " + dagger.getDamage() + "\tWeight: " + dagger.getWeight();
        } catch (Exception ignored) {}

        try {
            return "Weapon: " + sword.getWeaponName() + "\tDamage: " + sword.getDamage() + "\tWeight: " + sword.getWeight();
        } catch (Exception ignored) {}

        try {
            return "Weapon: " + mace.getWeaponName() + "\tDamage: " + mace.getDamage() + "\tWeight: " + mace.getWeight();
        } catch (Exception ignored) {}
        return "Error";
    }


}
