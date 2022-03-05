package edu.asu.mars.admin;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.text.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class FontList {
    private JFXPanel jfxPanel = new JFXPanel();

    //Print everything
    void showFonts() {
        JFrame baseFrame = new JFrame("Font Example");
        baseFrame.setSize(455, 910);
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseFrame.setResizable(false);
        baseFrame.add(jfxPanel);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(jfxPanel);
            }
        });
        baseFrame.setVisible(true);
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
        Group root = new Group();
        Scene scene = new Scene(root);
        Group scrollGroup = new Group();
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Arrays.sort(fonts);
        javafx.scene.control.ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(450, 880);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        int offset = 40;
        for (String font : fonts) {
            Text t = new Text(font);
            t.setFont(javafx.scene.text.Font.font(font, FontWeight.NORMAL, 20));//TODO: Add option for font size
            t.setX(45);
            t.setY(offset);
            offset += 35;
            Tooltip.install(t,new Tooltip(font));
            scrollGroup.getChildren().add(t);
        }
        scrollPane.setContent(scrollGroup);
        root.getChildren().add(scrollPane);
        return (scene);
    }

}
