package hellofx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.layout.Region;

import java.util.List;

public class LeaderboardDialog {

    public static void show(Stage parent) {
        Stage stage = new Stage();
        stage.initOwner(parent);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Leaderboard");
        

        // Root container
        VBox root = new VBox(12);
        root.setId("leaderboard-root");

        // Title label
        Label title = new Label("LEADERBOARD");
        title.getStyleClass().add("leaderboard-title");
        root.getChildren().add(title);

        // Load top scores
        List<Integer> top = Leaderboard.loadTop();

        if (top.isEmpty()) {
            Label empty = new Label("No scores yet.");
            empty.getStyleClass().add("leaderboard-empty");
            root.getChildren().add(empty);
        } else {
            int rank = 1;
            for (int score : top) {
                // Label with rank in yellow and score in green
                Label l = new Label(rank + ". " + score);
                l.getStyleClass().add("leaderboard-score");

                root.getChildren().add(l);
                rank++;
            }
        }

        // Close button
        Region spacer = new Region();
VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
root.getChildren().add(spacer);

Button close = new Button("Close");
close.getStyleClass().add("leaderboard-close");
close.setOnAction(e -> stage.close());
root.getChildren().add(close);

        // Scene setup
        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(LeaderboardDialog.class.getResource("/CSS/LeaderboardDialog.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }
}