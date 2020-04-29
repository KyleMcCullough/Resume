package ca.nscc.Shapes;

import java.awt.*;

public class Square extends Shape {

    private int sideLength = 40;

    public Square(int xPosition, int yPosition, int xSpeed, int ySpeed) {
        super(xPosition, yPosition, xSpeed, ySpeed);
        this.sideLength = (int) ( this.sideLength * this.getSizeModifier());
    }

    @Override
    //Draws the Shape
    public void drawShape(Graphics g) {
        g.setColor(this.getPrimaryColor());
        g.fillRect(this.getxPosition(), this.getyPosition(), this.sideLength, this.sideLength);
    }

    @Override
    //Checks for collisions with the Window Borders
    public void checkCollision(int height, int width) {

        if (this.getyPosition() <= 0 || this.getyPosition() >= height - this.sideLength) {
            this.setYSpeed(this.getYSpeed() * -1);
        }

        if (this.getxPosition() + this.sideLength >= width || this.getxPosition() <= 0) {
            this.setXSpeed(this.getXSpeed() * -1);
        }
    }

    @Override
    //Creates a Rectangle around the object for Object collision checking
    public java.awt.Rectangle createRectangle() {
        return new java.awt.Rectangle(this.getxPosition(), this.getyPosition(),
                this.sideLength, this.sideLength);
    }
}
