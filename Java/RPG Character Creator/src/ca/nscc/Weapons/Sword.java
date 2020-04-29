package ca.nscc.Weapons;

public class Sword extends Weapon {
    private static final int damage = 10;
    private static final int weight = 5;

    public Sword() {
        super(damage, weight, Sword.class.getSimpleName());
    }
}
