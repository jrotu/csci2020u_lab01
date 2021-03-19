package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.util.*;


public class Main extends Application {
    private Canvas canvas;
    private double screenWidth = 800;
    private double screenHeight = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Map<String, Double> warnings = new HashMap<>();
        File csv = new File("C:\\Users\\titan\\IdeaProjects\\csci2020u\\lab07\\src\\sample\\weatherwarnings-2015.csv");
        Scanner scanner = new Scanner(csv);

        // any non-digit characters
        while ( scanner.hasNextLine() ) {
            String nextLine = scanner.nextLine();
            String warning = nextLine.split(",")[5];
            warnings.put(
                    warning,
                    warnings.containsKey(warning) ? warnings.get(warning) + 1 : 1
            );
        }

        Group root = new Group();

        // From: http://www.java2s.com/Code/Java/JavaFX/SetScenebackgroundcolorandsize.htm
        Scene scene = new Scene(root, 800, 600, Color.web("#222222"));

        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(canvas);

        primaryStage.setTitle("Graphics - Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();

        draw(root, warnings);

    }

    private void draw(Group root, Map<String, Double> warnings) {
        // Adapted From: https://www.baeldung.com/java-stream-sum#using-streamsum-with-map
        Double totalWarnings = warnings.values()
                .stream()
                .mapToDouble(Double::valueOf)
                .sum();

        System.out.println(totalWarnings);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double startAngle = 0;
        double arcExtent = 0;
        int wh = 250;
        String color = colorize();
        String startColor = color;
        Map<String, String> warningColors = new HashMap<>();

        // Adapted From:
        // Answer: https://stackoverflow.com/a/1066607/10827766
        // User: https://stackoverflow.com/users/86463/harto
        for ( Map.Entry<String, Double> e : warnings.entrySet() ) {
            warningColors.put(e.getKey(), color);
            arcExtent = (e.getValue() / totalWarnings) * (double) 360;
            gc.setFill(Color.web(color));
            gc.fillArc(350, 130, wh, wh, startAngle, arcExtent, ArcType.ROUND);
            startAngle += arcExtent;
            color = colorize();
        }

        int currentY = 125;
        for ( Map.Entry<String, String> e : warningColors.entrySet() ) {
            currentY += 50;
            gc.setFill(Color.web(e.getValue()));
            gc.fillRect(100, currentY, 50, 25);
            gc.fillText(e.getKey(), 165, currentY + 16);
        }
        // gc.setFill(Color.web(startColor));
        // gc.fillArc(origin, origin, wh, wh, startAngle, (double) 360 - startAngle, ArcType.ROUND);
    }

    // Adapted From:
    // Answer: https://stackoverflow.com/a/35459935/10827766
    // User: https://stackoverflow.com/users/476791/slartidan
    private String colorize() {
        Random random = new Random();
        int nextInt = random.nextInt(0xffffff + 1);
        return String.format("#%06x", nextInt);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
