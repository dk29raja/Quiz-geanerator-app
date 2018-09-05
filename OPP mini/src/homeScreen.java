//package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;

import static javafx.collections.FXCollections.observableArrayList;
import static sample.Main.window;

//Styling.
public class homeScreen {

    static String choice;
    static int noOfQ;
    static String set;




    static String sql_test_generate  = "SELECT * FROM oops_proj.oops_table  WHERE SECTION = '%s' ORDER BY RAND() LIMIT %d ;";
    static String sql_all_generate = "SELECT * FROM oops_proj.oops_table  WHERE SECTION = '%s';";
    public static Scene getScene()
    {
        Scene scene ;
        Label label = new Label("Select A Subject From The List.");

        //generate test button
        Button generate_Test = new Button("Generate Test");

        //No of questions
        //Not checked for integers.
        ObservableList<Integer> options = FXCollections.observableArrayList(1,5,10,15,20);
        ComboBox comboBox = new ComboBox(options);
        comboBox.setPromptText("Enter the number of Questions you want");


        ChoiceBox cb = new ChoiceBox();
        cb.setItems(observableArrayList( "Maths", "Chemistry ", "Java", "Physics"));
        cb.setValue("Maths");
        set = cb.getValue().toString();

        generate_Test.setOnAction(event -> getChoice(cb,comboBox));


        Button allListGenerator = new Button("All Questions.");
        allListGenerator.setOnAction(event -> window.setScene(all_list.getscene()));


        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(15,20, 10,10));
        layout.getChildren().addAll(label,cb,comboBox,generate_Test,allListGenerator);
        scene = new Scene(layout,300,300);

        return scene;
    }


    public static void getChoice(ChoiceBox<String> cb,ComboBox<Integer>comboBox)
    {
        noOfQ = comboBox.getValue();

        window.setScene(test.getscene());


    }


}
