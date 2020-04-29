package ca.nscc.Shapes;

import java.awt.*;

public class Circle extends Shape {

    private int size = 40;

    public Circle(int xPosition, int yPosition, int xSpeed, int ySpeed) {
        super(xPosition, yPosition, xSpeed, ySpeed);
        this.size = (int) ( this.size * this.getSizeModifier());
    }

    @Override
    //Draws the Shape
    public void drawShape(Graphics g) {
        g.setColor(this.getPrimaryColor());
        g.fillOval(this.getxPosition(),this.getyPosition(),size , size);
    }

    @Override
    //Checks for collisions with the Window Borders
    public void checkCollision(int height, int width) {

        if (this.getyPosition() <= 0 || this.getyPosition() >= height - this.size) {
            this.setYSpeed(this.getYSpeed() * -1);
        }

        if (this.getxPosition() + this.size >= width || this.getxPosition() <= 0) {
            this.setXSpeed(this.getXSpeed() * -1);
        }
    }

    @Override
    //Creates a Rectangle around the object for Object collision checking
    public java.awt.Rectangle createRectangle() {
        return new java.awt.Rectangle(this.getxPosition(), this.getyPosition(),
                this.size, this.size);
    }
}
