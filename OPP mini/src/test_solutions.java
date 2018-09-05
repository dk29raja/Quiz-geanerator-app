//package sample;

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

import static sample.Main.window;
import static sample.homeScreen.noOfQ;
import static sample.homeScreen.set;
import static sample.homeScreen.sql_test_generate;


public class test_solutions {



    static ListView<String> listView;
    static Connection cn = Controller.getconn();
    static StringBuilder stringBuilder = new StringBuilder();
    static StringBuilder stringBuilder1 = new StringBuilder();

    public static Scene getscene() {
        Scene scene;
        listView = new ListView<>();
        ResultSet resultSet = null;
        Button Back = new Button("Back");
        Back.setOnAction(event -> {
            window.setScene(test.getscene());
            window.show();

        });



        stringBuilder1.append("SELECT QUESTION,ANSWER FROM oops_proj.oops_table WHERE ID IN (");
        for(int i:test.arrayList)
        {

            stringBuilder1.append(String.format("%d,",i));

        }

        stringBuilder1.deleteCharAt(stringBuilder1.length()-1);
        stringBuilder1.append(")");
        stringBuilder1.append("order by find_in_set(ID, '");
        for(int j:test.arrayList)
        {
            stringBuilder1.append(String.format("%d,",j));
        }
        stringBuilder1.deleteCharAt(stringBuilder1.length()-1);
        stringBuilder1.append("' )");

        test.arrayList.removeAll(test.arrayList);
        String sql_sol_generate = stringBuilder1.toString();
        stringBuilder1.setLength(0);



        Button Export = new Button("Export to text file");
        Export.setOnAction(event -> exporter());

        try {
            Statement gen_stmt = cn.createStatement();
            resultSet = gen_stmt.executeQuery(sql_sol_generate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int i = 1;
            while (resultSet.next()) {

                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%d. ", i));
                sb.append(resultSet.getString("Question"));
                sb.append("\n");
                sb.append("   ANSWER::");
                sb.append(resultSet.getString("ANSWER"));


                String questionString = sb.toString();
                stringBuilder.append(questionString);
                stringBuilder.append("\n");
                stringBuilder.append("\n");

                listView.getItems().add(questionString);
                i++;
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        VBox vb = new VBox();
        HBox hb = new HBox();

        Insets insets = new Insets(10,50,10,50);
        hb.setPadding(insets);

        hb.getChildren().addAll(Back,Export);
        vb.getChildren().addAll(hb,listView);


        scene = new Scene(vb, 800, 400);
        return scene;

    }


    static void exporter()
    {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("answer_list.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(stringBuilder.toString());
        writer.close();

    }
}