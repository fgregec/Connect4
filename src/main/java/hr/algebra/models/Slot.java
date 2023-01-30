package hr.algebra.models;


public class Slot {
    private boolean isUsed;
    private PlayerDetails owner;


    public Slot(boolean isUsed, PlayerDetails owner) {
        this.isUsed = isUsed;
        this.owner = owner;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public PlayerDetails getOwner() {
        return owner;
    }

    public void setOwner(PlayerDetails owner) {
        this.owner = owner;
    }


}
