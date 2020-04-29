package ca.nscc.Shapes;

import java.awt.*;
import java.awt.Rectangle;
import java.util.Random;

public abstract class Shape {
    private Color primaryColor, secondaryColor;
    private float sizeModifier;
    private int xSpeed, ySpeed, xPosition, yPosition;

    public Shape(int xPosition, int yPosition, int xSpeed, int ySpeed) {
        Random rand = new Random();
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.primaryColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        this.secondaryColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        this.sizeModifier = rand.nextFloat() + 1;
    }

    //Abstract methods to force all subclasses to implement these
    public abstract void drawShape(Graphics g);
    public abstract void checkCollision(int height, int width);
    public abstract java.awt.Rectangle createRectangle();

    //Changes the X and Y position based on the speed
    public void moveShape() {
        this.setxPosition(this.getxPosition() + this.getXSpeed());
        this.setyPosition(this.getyPosition() + this.getYSpeed());
    }

    //Alternates the Primary and Secondary Colors
    public void switchColor() {
        Color temp = this.primaryColor;
        this.primaryColor = this.secondaryColor;
        this.secondaryColor = temp;
    }

    //Getters

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public float getSizeModifier() {
        return sizeModifier;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
}
