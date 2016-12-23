/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw4_nt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Nathen
 */
public class HW4_NT extends Application {
    
    BorderPane layout;
    GridPane grid;
    AnchorPane canHolder;
    Stage stage;
    Scene scene;
    MenuBar menu;
    Menu app;
    MenuItem itmReset, itmExit;
    Label lbl1, lbl2, lbl3, lbl4;
    TextField textBox;
    double x_s, y_s, x_e, y_e, p1x, p1y, p2x, p2y, p3x, p3y;
    Color setFiller, setStrokeC;
    Rectangle newRec;
    Ellipse newElli;
    Polygon newTri;
    float setStrokeSize;
    String shapeIs;
    Boolean leave;
    
    @Override
    public void start(Stage stage) throws Exception {

        //Creates Menu Bar
        menu = new MenuBar();
        
        //Creates Menu
        app = new Menu("Menu");
        
        //Creates Menu Items
        itmReset = new MenuItem("Reset, Ctrl+X");
        itmExit = new MenuItem("Exit, Esc");
        
        //Adds Menu Items to Menu and Menu to Menu Bar
        app.getItems().addAll(itmReset, itmExit);
        menu.getMenus().add(app);
        
        //Creates new grid
        grid = new GridPane();
        grid.setPrefWidth(210);
        grid.setVgap(5);
        grid.setHgap(10);
         
        //Creates Shapes for Shape Combobox
        Rectangle clearR = new Rectangle(20, 10, Color.TRANSPARENT);
        clearR.setStroke(Color.BLACK);
        Ellipse clearE = new Ellipse(10, 5);
        clearE.setStroke(Color.BLACK);
        clearE.setFill(Color.TRANSPARENT);
        Polygon clearT = new Polygon();
        clearT.getPoints().addAll(new Double[]{
            7.5,0.0,
            15.0,15.0,
            0.0,15.0
        });
        clearT.setFill(Color.TRANSPARENT);
        clearT.setStroke(Color.BLACK);
        
        
        
        //Creates Shape Combobox
        ComboBox <Shape> shape = new ComboBox <>();
        shape.getItems().addAll(clearR, clearE, clearT);
        
        //Cell Factory that displays how each cell should look
        //This Cell Factory checks the object stored in each cell and displays
        //its according graphics to represent the shape.
        shape.setCellFactory(new Callback<ListView<Shape>, ListCell<Shape>>(){
            @Override
            public ListCell<Shape> call (ListView<Shape> param){
                return new ListCell<Shape>(){
                    
                    //Sets Default
                    private Shape s;
                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        s = new Rectangle (20, 10, Color.TRANSPARENT);
                        s.setStroke(Color.BLACK);
                    }
                    
                    @Override 
                    public void updateItem(Shape itm, boolean empty){
                        super.updateItem(itm, empty);
                        
                        if(itm == null || empty){
                            setGraphic(null);
                            
                        //Default Square if object is a rectangle
                        }else if(itm.equals(clearR)){
                            setGraphic(s);
                            
                        //Ellipse graphics displayed if object is ellipse
                        }else if(itm.equals(clearE)){
                            s = new Ellipse (10,5);
                            s.setStroke(Color.BLACK); 
                            s.setFill(Color.TRANSPARENT);
                            setGraphic(s);
                            
                        //Displays Triangle grapics    
                        }else{
                            Polygon s = new Polygon();
                            s.getPoints().addAll(new Double[]{
                                7.5,0.0,
                                15.0,15.0,
                                0.0,15.0
                            });
                            s.setFill(Color.TRANSPARENT);
                            s.setStroke(Color.BLACK);
                            setGraphic(s);
                        }
                    }
                };
            }
        });
        shape.getSelectionModel().selectFirst();
        
        
        
        //Creates all color blocks with Rectangle objects
        Rectangle red = new Rectangle(20, 10, Color.RED);
        Rectangle white = new Rectangle(20, 10, Color.WHITE);
        Rectangle gray = new Rectangle(20, 10, Color.GRAY);
        Rectangle black= new Rectangle(20, 10, Color.BLACK);
        Rectangle yellow = new Rectangle(20, 10, Color.YELLOW);
        Rectangle orange = new Rectangle(20, 10, Color.ORANGE);
        Rectangle green = new Rectangle(20, 10, Color.GREEN);
        Rectangle blue = new Rectangle(20, 10, Color.BLUE);
        Rectangle violet = new Rectangle(20, 10, Color.VIOLET);
        Rectangle pink = new Rectangle(20, 10, Color.PINK);
        
        //Call back for Cell Factory of filler and stroke color
        //Displays rectangle with according colors in a specific cell
        Callback cbc = new Callback<ListView<Rectangle>, ListCell<Rectangle>>(){
            @Override
            public ListCell<Rectangle> call(ListView<Rectangle> param) {
                return new ListCell<Rectangle>(){
                    
                    //Default Rectangle
                    private final Rectangle rec;
                    {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        rec = new Rectangle (20, 10);
                    }
                    
                    @Override
                    public void updateItem(Rectangle itm, boolean empty){
                        super.updateItem(itm, empty);
                        
                        if(itm == null || empty){
                            setGraphic(null);
                            
                        //Sets color of rectangle and displays graphics
                        }else{
                            rec.setFill(itm.getFill());
                            setGraphic(rec);
                        }
                    }
                };
            }
        };
        
        //Creates Fill ComboBox and sets Cell Factory
        ComboBox <Rectangle> fill = new ComboBox<>();
        fill.getItems().addAll(red, white, gray, black, yellow, orange, green, blue, violet, pink);
        fill.getSelectionModel().selectFirst();
        fill.setCellFactory(cbc);
        
        //Creates OutLine ComboBox for the stroke color and sets Cell Factory
        ComboBox <Rectangle> outLine = new ComboBox <>();
        outLine.getItems().addAll(black, red, white, gray, yellow, orange, green, blue, violet, pink);
        outLine.getSelectionModel().selectFirst();
        outLine.setCellFactory(cbc);
        
        
        
        //Creates textfield for stroke size
        textBox = new TextField("1");
        textBox.setPrefWidth(4);
        
        //Creates labels
        lbl1 = new Label("Shape");
        lbl2 = new Label("Fill Color");
        lbl3 = new Label("Stroke Thickness");
        lbl4 = new Label("Stroke Color");
        
        //Adds labels to the grid
        grid.add(lbl1, 1, 0);
        grid.add(lbl2, 1, 1);
        grid.add(lbl3, 1, 2);
        grid.add(lbl4, 1, 3);
        
        //Adds ComboBoxs and TextField to grid
        grid.add(shape, 2, 0);
        grid.add(fill, 2, 1);
        grid.add(textBox, 2, 2);
        grid.add(outLine, 2, 3);
        
        //Creates AnchorPane to act as a canvas      
        canHolder = new AnchorPane();
        canHolder.setStyle("-fx-background-color: White; -fx-border-color: Black;");
        
        //Adds Creates BorderPane and adds MenuBar, Grid and Anchor to the pane
        layout = new BorderPane();
        layout.setTop(menu);
        layout.setLeft(grid);
        layout.setCenter(canHolder);
        
        scene = new Scene(layout, 650, 400);
        
        stage.setTitle("Simple Drawing App");
        stage.setScene(scene);
        stage.show();
        
        
        //Action when mouse is pressed
        canHolder.setOnMousePressed(new EventHandler <MouseEvent>(){
            public void handle(MouseEvent me){
                
                //Checks to make sure input value is positive
                if(Integer.parseInt(textBox.getText()) < 0){
                    textBox.setText("0");
                }
                
                //Initializes starting point and gets values selected from user.
                x_s = me.getX();
                y_s = me.getY();
                setFiller = (Color) fill.getValue().getFill();
                setStrokeC = (Color) outLine.getValue().getFill();
                setStrokeSize = Integer.parseInt(textBox.getText());
                
                //Executes if shape is a rectangle
                if(shape.getValue().equals(clearR)){
                    shapeIs = "rec";
                    
                    //Creates and sets new rectangle. Adds rectangle to AnchorPane
                    newRec = new Rectangle();
                    newRec.setFill(setFiller);
                    newRec.setStroke(setStrokeC);
                    newRec.setStrokeWidth(setStrokeSize);
                    canHolder.getChildren().add(newRec);
                    
                //Executes if shape is an ellipse
                }else if(shape.getValue().equals(clearE)){
                    shapeIs = "elli";
                    
                    //Creates and sets new Ellipse and adds it to AnchorPane
                    newElli = new Ellipse();
                    newElli.setFill(setFiller);
                    newElli.setStroke(setStrokeC);
                    newElli.setStrokeWidth(setStrokeSize);
                    canHolder.getChildren().add(newElli);
                    
                //Executes if shape is a triangle
                }else{
                    shapeIs = "tri";
                    
                    //Creates and sets new Triangle and adds it to AnchorPane
                    newTri = new Polygon();
                    newTri.setFill(setFiller);
                    newTri.setStroke(setStrokeC);
                    newTri.setStrokeWidth(setStrokeSize);
                    canHolder.getChildren().add(newTri);
                }
            }
        });
        
        
        
        //Action when mouse is dragged
        canHolder.setOnMouseDragged(new EventHandler <MouseEvent> (){
            public void handle(MouseEvent me){
                
                //Checks to make sure when mouse is moved, it is still
                //within AnchorPane
                if(leave == false){
                    
                    //Execute if shape is a rectangle
                    if(shapeIs.equals("rec")){
                        
                        //Sets X and Y
                        newRec.setX(x_s);
                        newRec.setY(y_s);
                        
                        x_e = me.getX();
                        y_e = me.getY();
                       
                        //Sets width and height
                        newRec.setWidth(x_e - x_s);
                        newRec.setHeight(y_e - y_s);
                        
                        //Compensates if mouse moves left of start postion
                        if (newRec.getWidth() < 0 ){
                            newRec.setWidth( - newRec.getWidth() ) ;
                            newRec.setX(newRec.getX() - newRec.getWidth() ) ;
                        }
                        
                        //Compensates if mouse moves below start position
                        if (newRec.getHeight() < 0 ){
                            newRec.setHeight( - newRec.getHeight() ) ;
                            newRec.setY(newRec.getY() - newRec.getHeight() ) ;
                        }

                    //Executes if shape is ellipse
                    }else if(shapeIs.equals("elli")){
                        
                        //Sets X and Y 
                        x_e = me.getX();
                        y_e = me.getY();
                        
                        //Sets center and radius of Ellipse
                        newElli.setCenterX((x_e - x_s)/2 + x_s);
                        newElli.setCenterY((y_e - y_s)/2 + y_s);
                        newElli.setRadiusX((x_e - x_s)/2);
                        newElli.setRadiusY((y_e - y_s)/2);
                        
                        //Compensates if mouse moves left of start position
                        if(newElli.getRadiusX() < 0){
                           newElli.setCenterX((x_s - x_e)/2 + x_e);
                           newElli.setRadiusX((x_s - x_e)/2);
                        }
                        
                        //Compensates if mouse moves below start position
                        if(newElli.getRadiusY() < 0){
                            newElli.setCenterY((y_s - y_e)/2 + y_e);
                            newElli.setRadiusY((y_s - y_e)/2);
                        }
                        
                    //If shape is a triangle
                    }else{
                        
                        //Clears all points of previous triangle
                        newTri.getPoints().clear();
                        
                        //Sets values
                        x_e = me.getX();
                        y_e = me.getY();
                        
                        //Sets values of first point
                        p1x = ((x_e - x_s)/2) + x_s;
                        p1y = y_e;
                        
                        //Sets values of second point
                        p2x = x_s;
                        p2y = y_s;
                        
                        //Sets values of third point
                        p3x = x_e;
                        p3y = y_s;
                        
                        //Sets new points of the triangle
                        newTri.getPoints().addAll(new Double[]{
                           p1x, p1y,
                           p2x, p2y,
                           p3x, p3y
                        });
                    }
                
                //Else, do nothing    
                }else{
                    
                }
            }
        });
        
        
        
        //Action when mouse enters AnchorPane
        canHolder.setOnMouseEntered(new EventHandler <MouseEvent>(){
            public void handle(MouseEvent me){
                leave = false;
            }
        });
        
        //Action when mouse leaves AnchorPane
        canHolder.setOnMouseExited(new EventHandler <MouseEvent>(){
            public void handle(MouseEvent me){
                leave = true;
            }
        });
     
        
        
        //Menu item resets pannel when clicked   
        itmReset.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                shape.getSelectionModel().selectFirst();
                fill.getSelectionModel().selectFirst();
                outLine.getSelectionModel().selectFirst();
                textBox.setText("1");
                canHolder.getChildren().clear();
            }
        });
        
        //Menu item closes window when clicked
        itmExit.setOnAction(new EventHandler(){
            public void handle(Event event){
                Platform.exit();
            }
        });        
        
        //Hot Key commands to reset and exit program with keyboard
        KeyCodeCombination key = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            
            //Exits program when Esc is pressed
            public void handle(KeyEvent e){
                if(e.getCode() == KeyCode.ESCAPE){
                    Platform.exit();
                    
                //Matches combination and resets program to deault settings if
                //Ctrl+X and pressed together
                }else if(key.match(e)){
                    shape.getSelectionModel().selectFirst();
                    fill.getSelectionModel().selectFirst();
                    outLine.getSelectionModel().selectFirst();
                    textBox.setText("1");
                    canHolder.getChildren().clear();
                    
                //Else, do nothing    
                }else{
                    
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
