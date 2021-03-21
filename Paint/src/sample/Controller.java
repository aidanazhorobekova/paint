package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.io.File;


public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox eraser;

    @FXML
    private GraphicsContext g;


    @FXML
    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if (eraser.isSelected()) {
                g.clearRect(x, y, size, size);
            } else {
                g.setFill(colorPicker.getValue());
                g.fillOval(x, y, size, size);
            }
        });
    }

    public void Reset() {
        g.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }


    public void onSave() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save");
        fc.setInitialFileName("Paint");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG File", "*.png"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG File", "*.jpg"));

        try{
            File selectedFile = fc.showSaveDialog(new Stage());
            if(selectedFile != null){
                Image snapshot = canvas.snapshot(null, null);

                ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), "png", selectedFile);
            }
        } catch (Exception e){
            System.out.println("Failed to save image: " + e);
        }
    }


    public void onExit() {
        Platform.exit();
    }

}