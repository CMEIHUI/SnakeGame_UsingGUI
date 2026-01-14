package hellofx;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Snakelogo extends Pane {

    public Snakelogo() {

        Rectangle body1 = new Rectangle();
        body1.setX(170); 
        body1.setY(105);
        body1.setWidth(135);
        body1.setHeight(20);
        body1.setFill(Color.TURQUOISE);

        Rectangle body2 = new Rectangle();
        body2.setX(285);
        body2.setY(32);
        body2.setWidth(20);
        body2.setHeight(75);
        body2.setFill(Color.TURQUOISE);

        Rectangle head1 = new Rectangle();
        head1.setX(285);
        head1.setY(17);
        head1.setWidth(80);
        head1.setHeight(20);
        head1.setFill(Color.TURQUOISE);


        getChildren().addAll(body1,body2,head1);
    }

    
        {;

    }
}