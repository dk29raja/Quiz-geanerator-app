package sample;
//Missing back buttons
//Styling
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static sample.Main.window;
import static sample.homeScreen.*;





public class test {

    static ListView<String> listView;
    static Connection cn = Controller.getconn();
    static StringBuilder stringBuilder = new StringBuilder();
    static ArrayList<Integer> arrayList= new ArrayList<>();


    public static Scene getscene()
    {
        Scene scene ;
        listView = new ListView<>();
        ResultSet resultSet =  null;

        try {
            Statement gen_stmt = cn.createStatement() ;
            resultSet = gen_stmt.executeQuery(String.format(sql_test_generate,set,noOfQ));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int i = 1;

            while(resultSet.next())
            {

                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%d. ",i));
                sb.append(resultSet.getString("Question"));
                sb.append("\n");
                if(resultSet.getInt("TYPE") == 0){
                    sb.append("a. ");
                    sb.append(resultSet.getString("OPTION1"));
                    sb.append("\n");
                    sb.append("b. ");
                    sb.append(resultSet.getString("OPTION2"));
                    sb.append("\n");
                    sb.append("c. ");
                    sb.append(resultSet.getString("OPTION3"));
                    sb.append("\n");
                    sb.append("d. ");
                    sb.append(resultSet.getString("OPTION4"));
                    sb.append("\n");
                }
                arrayList.add(resultSet.getInt("ID"));


                String questionString = sb.toString();
                stringBuilder.append(questionString);
                stringBuilder.append("\n");

                listView.getItems().add(questionString);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {}
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Button Export = new Button("Export to text file");
        Export.setOnAction(event -> exporter());
        Button Back = new Button("Back");
        Back.setOnAction(event -> {
            window.setScene(homeScreen.getScene());
            window.show();

        });

        Button Solution_List = new Button("Solution List");
        Solution_List.setOnAction(event -> window.setScene(test_solutions.getscene()));

        VBox vb = new VBox();
        HBox hb = new HBox(50);

        Insets insets = new Insets(10,50,10,50);
        hb.setPadding(insets);
        hb.getChildren().addAll(Export,Solution_List,Back);
        vb.getChildren().addAll(hb,listView);


        scene = new Scene(vb,800,400);
        return scene;

    }





    static void exporter()
    {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("question_list.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(stringBuilder.toString());
        writer.close();

    }


}





