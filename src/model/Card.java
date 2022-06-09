package model;

public enum Card {
    BRIDGE(0), PHILIPS_DRIVER(1), HAMMER(2), SAW(3);
    private final int value;

    Card(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
