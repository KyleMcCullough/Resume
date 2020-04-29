package ca.nscc.Shapes;

import java.awt.*;
import java.awt.Rectangle;

public class Portal extends Shape {

    private int height = 30;

    public Portal(int xPosition, int yPosition, int xSpeed, int ySpeed) {
        super(xPosition, yPosition, xSpeed, ySpeed);

        this.height = (int) (this.height * this.getSizeModifier());
    }

    @Override
    //Draws the Shape
    public void drawShape(Graphics g) {
        g.setColor(this.getPrimaryColor());
        g.fillOval(this.getxPosition(),this.getyPosition(),this.height / 2, this.height);
    }

    @Override
    //Checks for collisions with the Window Borders
    public void checkCollision(int height, int width) {
        if (this.getyPosition() <= 0 || this.getyPosition() >= height - this.height) {
            this.setYSpeed(this.getYSpeed() * -1);
        }

        if (this.getxPosition() + this.height / 2 >= width || this.getxPosition() <= 0) {
            this.setXSpeed(this.getXSpeed() * -1);
        }
    }

    @Override
    //Creates a Rectangle around the object for Object collision checking
    public java.awt.Rectangle createRectangle() {
        return new java.awt.Rectangle(this.getxPosition(), this.getyPosition(),
                this.height / 2, this.height);
    }
}
