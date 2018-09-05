package sample;
//Missing back buttons
//Styling
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.Main.window;
import static sample.homeScreen.*;

public class all_list {

    static ListView<String> listView;
    static Connection cn = Controller.getconn();
    static ResultSet set1;

    public static Scene getscene()
    {
        Scene scene ;
        listView = new ListView<>();


        try {
            Statement gen_stmt = cn.createStatement() ;
            set1 = gen_stmt.executeQuery(String.format(sql_all_generate,set));

        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            int i= 1;
            while(set1.next())
            {


                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%d. ",i));
                sb.append(set1.getString("Question"));
                sb.append("\n");
                if(set1.getInt("TYPE") == 0){
                    sb.append("a) ");
                    sb.append(set1.getString("OPTION1"));
                    sb.append("\n");
                    sb.append("b) ");
                    sb.append(set1.getString("OPTION2"));
                    sb.append("\n");
                    sb.append("c) ");
                    sb.append(set1.getString("OPTION3"));
                    sb.append("\n");
                    sb.append("d) ");
                    sb.append(set1.getString("OPTION4"));
                    sb.append("\n");
                }
                sb.append("ANSWER :: ");
                sb.append(set1.getString("ANSWER"));

                String questionString = sb.toString();
                listView.getItems().add(questionString);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {}
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button modifyButton = new Button("Modify");
        modifyButton.setOnAction(event -> modifier());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleter());


        Button Back = new Button("Back");
        Back.setOnAction(event -> {
            window.setScene(homeScreen.getScene());
            window.show();

        });

        Button addQuestion = new Button("Add Question");
        addQuestion.setOnAction(event -> addWindow.adder("Add a question","Enter the Question you want to add "));

        VBox vb = new VBox();
        HBox hb = new HBox(50);

        Insets insets = new Insets(10,50,10,50);
        hb.setPadding(insets);

        hb.getChildren().addAll(addQuestion,modifyButton,deleteButton,Back);
        vb.getChildren().addAll(hb,listView);


        scene = new Scene(vb,800,400);
        return scene;

    }


    static void modifier()
    {
        int id;
        ObservableList<String> toBeModified =  listView.getSelectionModel().getSelectedItems();
        String question;
        for(String h:toBeModified)
        {
            question = h.split("\\r\\n|\\n|\\r")[0].substring(3);
            Statement stmte;
            try {
                stmte = cn.createStatement();
                ResultSet rs = stmte.executeQuery(String.format(sel_id,question));
                while (rs.next()) {
                    id = rs.getInt("id");
                    modifyWindow.modify(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Modify error");
            }

        }

    }

    static void deleter()
    {
        ObservableList<String> toBeDeleted =  listView.getSelectionModel().getSelectedItems();

        String question ;
        for(String h:toBeDeleted)
        {
            question = h.split("\\r\\n|\\n|\\r")[0].substring(3);
            Statement stmt ;
            try {
                stmt = cn.createStatement();
                stmt.executeUpdate(String.format(del,question));
                window.setScene(all_list.getscene());
                window.show();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Deletion error");
            }
        }




    }


    static String del = "DELETE FROM oops_proj.oops_table WHERE QUESTION = '%s';";
    static String sel_id = "SELECT * FROM oops_proj.oops_table WHERE QUESTION = '%s'";


}
