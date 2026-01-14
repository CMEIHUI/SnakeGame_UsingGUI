package hellofx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuScreen {

    public static Scene create(Stage stage) {

        // Main VBox that centers everything
        VBox root = new VBox(10); // Increase spacing between logo + buttons
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setId("menu-root");

        // --- Title Text Above Logo ---
        Text title = new Text("SNAKE GAME");
        title.setId("menu-title");

        // --- Your Snake Logo ---
        Snakelogo logo = new Snakelogo();

        // --- Buttons grouped in their own VBox ---
        VBox menuButtons = new VBox(20);
        menuButtons.setAlignment(Pos.CENTER);

        Button start = new Button("START GAME");
        start.getStyleClass().add("menu-button");

        start.setOnAction(e -> {
    //Audio.playEat(); // ✅ play sound on click
    stage.setScene(GameScreen.create(stage));
});

        Button settings = new Button("SETTINGS");
        settings.getStyleClass().add("menu-button");
        settings.setOnAction(e -> stage.setScene(SettingsScreen.create(stage)));

        Button leaderboard = new Button("LEADERBOARD");
        leaderboard.getStyleClass().add("menu-button");
        leaderboard.setOnAction(e -> LeaderboardDialog.show(stage));

        Button about = new Button("ABOUT");
        about.getStyleClass().add("menu-button");
        about.setOnAction(e -> stage.setScene(About.create(stage)));
    
        Button contact = new Button("CONTACT");
        contact.getStyleClass().add("menu-button");
        contact.setOnAction(e -> stage.setScene(Contact.create(stage)));

        Button exit = new Button("EXIT");
        exit.getStyleClass().add("menu-button");

        exit.setOnAction(e -> {
    stage.close();
});

        menuButtons.getChildren().addAll(start, settings, leaderboard, about, contact, exit);

        // Add everything in the correct vertical order
        root.getChildren().addAll(title, logo, menuButtons);

        Scene sc = new Scene(root, 600, 520);
        var css = Main.class.getResource("/CSS/MenuScreen.css");
        if (css != null) sc.getStylesheets().add(css.toExternalForm());
        return sc;
    }
}

