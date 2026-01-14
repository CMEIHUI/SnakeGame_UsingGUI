package hellofx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class About {

    public static Scene create(Stage stage) {

        Label title = new Label("About SnakeFX Deluxe");
        title.setId("title");

        Label info = new Label(
                "SnakeFX Deluxe is a classic snake game.\n" +
                "The goal is to eat food and avoid collisions.\n" +
                "This game is developed using JavaFX."
        );
        info.setId("info");
        

        Button back = new Button("BACK");
        back.setPrefWidth(200);
        back.setId("back");
        back.setOnAction(e -> stage.setScene(MenuScreen.create(stage)));

        VBox root = new VBox(50, title, info, back);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60));
        root.setId("root");
        

        Scene scene = new Scene(root, 600, 480);
        scene.getStylesheets().add(About.class.getResource("/CSS/About.css").toExternalForm());
        return scene;
    }
}
