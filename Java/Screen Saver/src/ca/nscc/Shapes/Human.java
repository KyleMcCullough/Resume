package ca.nscc.Shapes;

import java.awt.*;
import java.awt.Rectangle;

public class Human extends Shape {

    private int longline = 20, shortline = 10, size = 10;

    public Human(int xPosition, int yPosition, int xSpeed, int ySpeed) {
        super(xPosition, yPosition, xSpeed, ySpeed);
        this.longline = (int) (this.longline * this.getSizeModifier());
        this.shortline = (int) (this.shortline * this.getSizeModifier());
        this.size = (int) (this.size * this.getSizeModifier());
    }


    @Override
    //Draws the Shape
    public void drawShape(Graphics g) {
        g.setColor(this.getPrimaryColor());
        g.fillRect(this.getxPosition(), this.getyPosition(), this.shortline, this.longline);


        g.setColor(this.getSecondaryColor());
        g.fillOval(this.getxPosition(),this.getyPosition() - this.size,this.size , this.size);
        g.fillRect(this.getxPosition() - 5, this.getyPosition(), 5, this.longline);
        g.fillRect(this.getxPosition() + this.shortline + 5, this.getyPosition(), -5, this.longline);
        g.fillRect(this.getxPosition(), this.getyPosition() + this.longline, 5, this.longline);
        g.fillRect(this.getxPosition() + this.shortline - 5, this.getyPosition() + this.longline, 5, this.longline);
    }

    @Override
    //Checks for collisions with the Window Borders
    public void checkCollision(int height, int width) {
        if (this.getyPosition() - this.size <= 0 || this.getyPosition() + this.size >= height - (this.longline + 10)) {
            this.setYSpeed(this.getYSpeed() * -1);
        }

        if (this.getxPosition() + this.longline + 5 >= width || this.getxPosition() <= 0) {
            this.setXSpeed(this.getXSpeed() * -1);
        }
    }

    @Override
    //Creates a Rectangle around the object for Object collision checking
    public Rectangle createRectangle() {
        return new java.awt.Rectangle(this.getxPosition(), this.getyPosition(),
                this.shortline + 10, this.longline + this.size + 10);
    }
}
