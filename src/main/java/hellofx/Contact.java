package hellofx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Contact {

    public static Scene create(Stage stage) {

        Text title = new Text("Contact Us");
        title.setId("contact-title");

        Text info = new Text(
                "Email: snakefx@gmail.com\n" +
                "Phone: +60 12-394 4681\n" +
                "Developer: SnakeFX Team"
        );
        info.setId("contact-info");

        Button back = new Button("BACK");
        back.setId("contact-back");
        back.setOnAction(e -> stage.setScene(MenuScreen.create(stage)));


        VBox root = new VBox(50, title, info, back);
        root.setAlignment(Pos.CENTER);
        root.setId("contact-root");

        Scene scene = new Scene(root, 600, 420);
        scene.getStylesheets().add(Contact.class.getResource("/CSS/Contact.css").toExternalForm());

        return scene;
    }
}
