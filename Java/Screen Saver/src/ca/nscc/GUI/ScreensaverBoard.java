package ca.nscc.GUI;

import ca.nscc.Shapes.*;
import ca.nscc.Shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class ScreensaverBoard extends JPanel {

    private Timer updateTimer = new Timer(100, new TimerAction());
    private Timer colorChangeTimer = new Timer(8000, new changeShapeColor());
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Shape newShape;
    private Random rand = new Random();

    public ScreensaverBoard() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addShape();
            }
        });

        //Set background color and start timers
        this.setBackground(Color.darkGray);

        this.updateTimer.start();
        this.colorChangeTimer.start();
    }

    //Function called when the mouse is clicked
    private void addShape() {

        //Get a random xSpeed, ySpeed and ShapeID
        int xSpeed = rand.nextInt(10) - 5;
        int ySpeed = rand.nextInt(10) - 5;
        int shapeID = rand.nextInt(5);

        if (xSpeed == 0) {
            xSpeed ++;
        }

        if (ySpeed == 0) {
            ySpeed ++;
        }

        //Create approiate shape using shapeID
        if (shapeID == 0) {
            this.newShape = new Circle(rand.nextInt(this.getWidth() - 150), rand.nextInt(this.getHeight() - 150),
                    xSpeed, ySpeed);
        } else if (shapeID == 1) {
            this.newShape = new Square(rand.nextInt(this.getWidth() - 150), rand.nextInt(this.getHeight() - 150),
                    xSpeed, ySpeed);
        } else if (shapeID == 2) {
            this.newShape = new ca.nscc.Shapes.Rectangle(rand.nextInt(this.getWidth() - 150), rand.nextInt(this.getHeight() - 150),
                    xSpeed, ySpeed);
        } else if (shapeID == 3) {
            this.newShape = new Portal(rand.nextInt(this.getWidth() - 150), rand.nextInt(this.getHeight() - 150),
                    xSpeed, ySpeed);
        } else {
            this.newShape = new Human(rand.nextInt(this.getWidth() - 200), rand.nextInt(this.getHeight() - 150),
                    xSpeed, ySpeed);
        }
    }

    private void moveShapes() {

        //For every shape in the Shapes ArrayList, move it then check the collision.
        for(Shape i : shapes) {
            i.moveShape();
            this.checkCollision(i);
        }
        this.repaint();
    }

    private void checkCollision(Shape shape) {

        //Check the border collision then create a hitbox.
        shape.checkCollision(this.getHeight(), this.getWidth());
        Rectangle box = shape.createRectangle();

        for (Shape i : shapes) {

            if (i == shape) {
                continue;
            }

            //Create another hitbox and check if they intersect.
            Rectangle box2 =  i.createRectangle();

            if (box.intersects(box2)) {

                //Change Color and X & Y Speed of both colliding objects
                shape.switchColor();
                i.switchColor();
                shape.setYSpeed(shape.getYSpeed() * -1);
                shape.setXSpeed(shape.getXSpeed() * -1);
                i.setYSpeed(i.getYSpeed() * -1);
                i.setXSpeed(i.getXSpeed() * -1);
            }
        }
    }

    //Every 8 seconds switch the secondary color to the primary color and set the primary to secondary
    private void changeColor() {
        for (Shape i : shapes) {
            i.switchColor();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //If the newShape is not in the Shapes ArrayList and it is not null, add it.
        if (!shapes.contains(newShape) && newShape != null){
            shapes.add(newShape);
        }

        //Draw all shapes in the Shapes ArrayList
        for (Shape i : shapes) {
            i.drawShape(g);
        }
    }

    //Call this class every 1/10th of a second
    private class TimerAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            moveShapes();
        }
    }

    //Call this class every 8 seconds
    private class changeShapeColor implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            changeColor();
        }
    }

}
