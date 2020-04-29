package ca.nscc.Weapons;

public class Mace extends Weapon {
    private static final int damage = 20;
    private static final int weight = 10;

    public Mace() {
        super(damage, weight, Mace.class.getSimpleName());
    }
}
