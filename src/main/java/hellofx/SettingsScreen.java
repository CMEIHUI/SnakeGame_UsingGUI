package hellofx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SettingsScreen {

    public static Skin selectedSkin = Skin.CLASSIC;
    public static GameRules.Difficulty selectedDifficulty = GameRules.Difficulty.NORMAL;

    public static Scene create(Stage stage) {

        // Root container
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.getStyleClass().add("settings-root");

        // --- Volume ---
        Label volLabel = createLabel("Volume");
        Slider volume = new Slider(0, 1, Audio.getVolume());
        volume.setPrefWidth(160);
        volume.setMinWidth(160);
        volume.setMaxWidth(160);
        volume.setPrefWidth(200);
        volume.valueProperty().addListener((o, ov, nv) ->
                Audio.setVolume(nv.doubleValue())
        );

        VBox volumeBox = createSettingBox(volLabel, volume);

        // --- Difficulty ---
        Label diffLabel = createLabel("Difficulty");
        ComboBox<String> difficulty = new ComboBox<>();
        difficulty.getItems().addAll("Easy", "Normal", "Hard");
        difficulty.setValue("Normal");
        difficulty.setPrefWidth(200);

        difficulty.setOnAction(e -> {
            switch (difficulty.getValue()) {
                case "Easy" -> GameRules.difficulty = GameRules.Difficulty.EASY;
                case "Hard" -> GameRules.difficulty = GameRules.Difficulty.HARD;
                default -> GameRules.difficulty = GameRules.Difficulty.NORMAL;
            }
        });

        VBox diffBox = createSettingBox(diffLabel, difficulty);

        // --- Skin ---
        Label skinLabel = createLabel("Skin");
        ComboBox<Skin> skins = new ComboBox<>();
        skins.getItems().addAll(Skin.values());
        skins.setValue(selectedSkin);
        skins.setPrefWidth(200);
        skins.setOnAction(e -> selectedSkin = skins.getValue());

        VBox skinBox = createSettingBox(skinLabel, skins);

        VBox backBox = new VBox(60);
        backBox.setAlignment(Pos.CENTER);
        backBox.setPadding(new Insets(30, 0, 0, 0)); 

        Region spacer = new Region();
        spacer.setPrefHeight(40); // adjust distance here

        // --- Back Button ---
        Button back = new Button("BACK");
        back.setPrefWidth(200);
        back.getStyleClass().add("back-button");
        back.setOnAction(e -> stage.setScene(MenuScreen.create(stage)));

        // Add everything
        root.getChildren().addAll(volumeBox, diffBox, skinBox, spacer, back);

        Scene sc = new Scene(root, 600, 400);
        sc.getStylesheets().add(SettingsScreen.class.getResource("/CSS/SettingScreen.css").toExternalForm());
        return sc;
    }

    // ---------- Helpers ----------

    private static Label createLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(200);
        label.getStyleClass().add("settings-label");
        return label;
    }

    private static VBox createSettingBox(Label label, Control control) {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(label, control);
        return box;
    }
}