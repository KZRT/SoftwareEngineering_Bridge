package model;

public enum Direction {
    UP('U'), DOWN('D'), LEFT('L'), RIGHT('R');
    private final char fileChar;

    Direction(char fileChar) {
        this.fileChar = fileChar;
    }

    public static Direction getDirectionByChar(char c) {
        for (Direction d : Direction.values()) {
            if (d.getFileChar() == c) return d;
        }
        throw new IllegalArgumentException("No enum defined with " + c);
    }

    public char getFileChar() {
        return fileChar;
    }

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
