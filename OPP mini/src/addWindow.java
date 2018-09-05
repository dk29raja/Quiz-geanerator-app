//package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Objects;


import static javafx.collections.FXCollections.observableArrayList;
//styling

public class addWindow {


    static String set;
    static int id = Randomize.nextValue();



    static void add(String Question,String Answer,String option1,String option2,String option3,String option4,String type,String set)
    {

        HashMap<String,Integer> hm = new HashMap<>();
        hm.put("Multiple Choice Question",0);
        hm.put("Fill In The Blanks",1);
        hm.put("True /False",2);




        try  {
            Statement stmt = test.cn.createStatement();
            String add_stmt = String.format("INSERT INTO oops_proj.oops_table(ID,QUESTION,OPTION1,OPTION2,OPTION3,OPTION4,ANSWER,TYPE,SECTION)" + "VALUES(%d,'%s','%s','%s','%s','%s','%s',%d,'%s')",id,Question,option1,option2,option3,option4,Answer,hm.get(type),set);

            stmt.executeUpdate(add_stmt);
        }
        catch (SQLException e) {
            System.out.println("Insertion error");
            e.printStackTrace();
        }

    }

    public static void adder(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        TextField Question = new TextField();
        Question.setPromptText("Enter Question Here...");
        TextField Answer = new TextField();
        Answer.setPromptText("Enter answer of the question");
        ChoiceBox<String> type = new ChoiceBox();
        type.setItems(observableArrayList( "Multiple Choice Question", "Fill In The Blanks", "True /False"));
        type.setValue("Fill In The Blanks");



        TextField option1 = new TextField();
        option1.setPromptText("Enter option1 here");


        TextField option2 = new TextField();
        option2.setPromptText("Enter option2 here");


        TextField option3 = new TextField();
        option3.setPromptText("Enter option3 here");


        TextField option4 = new TextField();
        option4.setPromptText("Enter option4 here");


        ChoiceBox<String> cb = new ChoiceBox();
        cb.setItems(observableArrayList( "Maths", "Chemistry ", "Java", "Physics"));
        cb.setValue("Maths");
        set = cb.getValue();







        Button addTheQuestion = new Button("Add the question");
        addTheQuestion.setOnAction(e ->{
            add(Question.getText(),Answer.getText(),option1.getText(),option2.getText(),option3.getText(),option4.getText(),type.getValue(),set);

            window.close();
        });




        GridPane layout = new GridPane();
        layout.setHgap(1);
        layout.setVgap(1);
        layout.add(Question,6,15);
        Label q = new Label("Question");
        layout.add(q,4,15);
        Label o1,o2,o3,o4;
        o1 = new Label("OPTION a ");
        o2 = new Label("OPTION b ");
        o3 = new Label("OPTION c ");
        o4 = new Label("OPTION d ");
        type.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {


            if(newValue.equals("Multiple Choice Question"))
            {

                layout.add(option1,6,30);
                layout.add(o1,4,30);

                layout.add(o2,4,40);
                layout.add(option2,6,40);

                layout.add(o3,4,50);
                layout.add(option3,6,50);

                layout.add(option4,6,60);
                layout.add(o4,4,60);


            }
            else if(layout.getChildren().contains(option1))
            {
                layout.getChildren().removeAll(option1,option2,option3,option4,o1,o2,o3,o4);
            }
        });


        Label a = new Label("Answer");
        layout.add(Answer,6,70);
        layout.add(a,4,70);
        layout.add(type,6,80);layout.add(cb,6,90);
        layout.add(addTheQuestion,6,150);
        layout.setAlignment(Pos.CENTER);
        window.setOnHidden(event -> {
            Main.window.setScene(all_list.getscene());
            Main.window.show();
        });

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout,400,400);
        window.setScene(scene);
        window.showAndWait();


    }
}
