package models;

public enum Level {
    BEGINNER(0), INTERMEDIATE(1), ADVANCED(2);

    private int level;

    Level(int level) {
        this.level = level;
    }

    public static Level fromLevel(int level) {
        for (Level l : values()) {
            if (l.level == level) {
                return l;
            }
        }
        throw new IllegalArgumentException();
    }

    public int getLevel() {
        return level;
    }
}
