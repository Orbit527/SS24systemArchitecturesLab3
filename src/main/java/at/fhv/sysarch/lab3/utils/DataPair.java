package at.fhv.sysarch.lab3.utils;

import at.fhv.sysarch.lab3.obj.Face;

import javafx.scene.paint.Color;

public class DataPair {

    private Face face;

    private Color color;

    public DataPair(Face face, Color color) {
        this.face = face;
        this.color = color;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
