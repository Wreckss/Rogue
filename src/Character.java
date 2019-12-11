public class Character extends Abilities {

    private String name;
    private int level = 1;
    public int healthPoints = 100;
    public boolean dead = false;
    public double blockTimer = 0.0;
    private double blockChance = .1;
    private double experiencePoints = 0.0;

    public Character(String name) {
        this.name = name;
    }

    public double getBlockTimer() {
        return blockTimer;
    }

    public String getName() {
        return name;
    }

    public double getBlockChance() {
        return blockChance;
    }

    public void setBlockChance(double blockChance) {
        this.blockChance = blockChance + (.01 * level);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        if (experiencePoints <= 100.0) {
            level++;
        }
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
}
