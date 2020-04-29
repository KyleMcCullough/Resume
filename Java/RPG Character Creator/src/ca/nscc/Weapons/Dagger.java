package ca.nscc.Weapons;

public class Dagger extends Weapon {
    private static final int damage = 6;
    private static final int weight = 2;

    public Dagger() {
        super(damage, weight, Dagger.class.getSimpleName());
    }
}
