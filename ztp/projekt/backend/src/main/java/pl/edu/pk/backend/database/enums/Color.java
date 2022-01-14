package pl.edu.pk.backend.database.enums;

public enum Color {
    BLACK('b'), WHITE('w');

    private char color;
    Color(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }
}
