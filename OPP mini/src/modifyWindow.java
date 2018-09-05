//package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import static javafx.collections.FXCollections.observableArrayList;

//Styling.


public class modifyWindow {

    static String set;

    static String getById = "SELECT * FROM oops_proj.oops_table WHERE ID = %d";
    static String changeById = "UPDATE oops_proj.oops_table SET QUESTION = '%s', OPTION1 = '%s',OPTION2='%s',OPTION3='%s',OPTION4='%s',ANSWER='%s',TYPE=%d,SECTION='%s' WHERE ID = %d;";
    static ResultSet resultSet;
    static String question;
    static String answer;
    static String optioN1,optioN2,optioN3,optioN4;




    static  Connection cn = Controller.getconn();





    static void modify(int id) {

        Stage window = new Stage();
        int typee = 1;

        try
        {
            Statement stmt = cn.createStatement();
            resultSet = stmt.executeQuery(String.format(getById,id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


        try {
            while (resultSet.next())
            {
                question = resultSet.getString("QUESTION");
                answer = resultSet.getString("ANSWER");
                optioN1 = resultSet.getString("OPTION1");
                optioN2 = resultSet.getString("OPTION2");
                optioN3 = resultSet.getString("OPTION3");
                optioN4 = resultSet.getString("OPTION4");
                typee = resultSet.getInt("TYPE");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }



        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modify the Question");
        window.setMinWidth(250);

        Label label = new Label();
        TextField Question = new TextField();
        Question.setPromptText("Enter Question Here...");
        Question.setText(question);
        TextField Answer = new TextField();
        Answer.setPromptText("Enter answer of the question");
        Answer.setText(answer);
        ChoiceBox<String> type = new ChoiceBox();
        type.setItems(observableArrayList( "Multiple Choice Question", "Fill In The Blanks", "True /False"));

        if(typee == 0)
            type.setValue("Multiple Choice Question.");



        TextField option1 = new TextField();
        option1.setPromptText("Enter option1 here");
        option1.setText(optioN1);

        TextField option2 = new TextField();
        option2.setPromptText("Enter option2 here");
        option2.setText(optioN2);

        TextField option3 = new TextField();
        option3.setPromptText("Enter option3 here");
        option3.setText(optioN3);

        TextField option4 = new TextField();
        option4.setPromptText("Enter option4 here");
        option4.setText(optioN4);

        ChoiceBox<String> cb = new ChoiceBox();
        cb.setItems(observableArrayList( "Maths", "Chemistry ", "Java", "Physics"));
        cb.setValue("Maths");
        set = cb.getValue();


        window.setOnHidden(event -> {
            Main.window.setScene(all_list.getscene());
            Main.window.show();
        });




        Button addTheQuestion = new Button("Update the Question");
        addTheQuestion.setOnAction(e ->{
            modifier(Question.getText(), Answer.getText(),option1.getText(),option2.getText(),option3.getText(),option4.getText(),type.getValue(),cb.getValue(),id);

            window.close();
        });




        GridPane layout = new GridPane();
        layout.setHgap(1);
        layout.setVgap(1);

        layout.add(Question,6,15);
        Label q = new Label("Question");
        layout.add(q,4,15);
        Label o1,o2,o3,o4;
        o1 = new Label("OPTION a)");
        o2 = new Label("OPTION b)");
        o3 = new Label("OPTION c)");
        o4 = new Label("OPTION d)");
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

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout,400,400);
        window.setScene(scene);
        window.showAndWait();


    }

    public static void modifier(String Question, String Answer, String option1, String option2, String option3, String option4, String type, String set,int id) {


        HashMap<String,Integer> hm = new HashMap<>();
        hm.put("Multiple Choice Question",0);
        hm.put("Fill In The Blanks",1);
        hm.put("True /False",2);




        try {

            Statement stmt = test.cn.createStatement();
            String add_stmt = String.format(changeById, Question, option1, option2, option3, option4, Answer, hm.get(type), set,id);

            stmt.executeUpdate(add_stmt);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Adding error");
        }


    }
}
