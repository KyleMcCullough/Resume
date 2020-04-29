package ca.nscc.Weapons;

public abstract class Weapon {
    private String weaponName;
    private int damage, weight;

    public Weapon(int damage, int weight, String weaponName) {
    this.damage = damage;
    this.weight = weight;
    this.weaponName = weaponName;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public int getDamage() {
        return damage;
    }

    public int getWeight() {
        return weight;
    }
}
