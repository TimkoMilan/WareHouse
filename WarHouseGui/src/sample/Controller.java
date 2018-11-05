package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button confirmInsert;
    public TextField amountInsert;
    public TextField goodsIdInsert;
    public ChoiceBox countryInsert;

    public Button confirmRemove;
    public TextField amountRemove;
    public TextField goodsIdRemove;
    public TextField userIdRemove;

    public TableView<Good> table1;
    public TableView<Good> table2;
    public ObservableList<Good> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Good, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(80);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Good, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(80);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Good, String> pieceColumn = new TableColumn<>("Amount");
        pieceColumn.setMinWidth(80);
        pieceColumn.setCellValueFactory(new PropertyValueFactory<>("piece"));

        TableColumn<Good, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setMinWidth(80);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        VBox vBox = new VBox();
        vBox.getChildren().addAll();

        table1.getColumns().addAll(idColumn,nameColumn,pieceColumn,countryColumn);
    }

    public void insertGoods(ActionEvent event) throws JSONException, UnirestException {
        JSONObject list =new JSONObject();
        list.put("country", countryInsert.getValue());
        list.put("name", goodsIdInsert.getText());
        list.put("piece", amountInsert.getText());
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:8080/api/v1/good")
                    .header("Content-Type", "application/json")
                    .header("cache-control", "no-cache")
                    .body(list.toString())
                    .asString();
//            countryInsert.setValue(null);

        } catch (UnirestException e) {
            e.printStackTrace();
//            countryInsert.setValue(null);
        }
        System.out.println(list);
    }
    public void removeGoods(ActionEvent event) throws UnirestException, IOException, JSONException {
        HttpResponse<String> response = Unirest.put("http://localhost:8080/api/v1/good/"+goodsIdRemove.getText()+"/good/{pieces}/pieces/{name}name?pieces="+amountRemove.getText()+"&name="+userIdRemove.getText()+"")
                .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .header("cache-control", "no-cache")
                .body("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"Authorization\"\r\n\r\neyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImVtYWlsIjoiYWRtaW5AdGVzdC5jb20iLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifSx7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImlhdCI6MTUzODM4Mzg4NSwiZXhwIjoxNTM4NjQzMDg1fQ.u6cXtO6lOO8Sj_AAT1Xy1ZPMEDHqX3mCBnjhwvNk1EE\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--")
                .asString();
        System.out.println(response);
    }

    public void getDatas(ActionEvent event) throws Exception {
        try {
            HttpResponse<String> response = Unirest.get("http://localhost:8080/api/v1/good")
                    .header("cache-control", "no-cache")
                    .asString();
            System.out.println(response.getBody());
            JSONArray jsonarray = new JSONArray(response.getBody());
            for(int i=0; i<jsonarray.length(); i++){
                JSONObject obj = jsonarray.getJSONObject(i);

                int id = obj.getInt("id");
                String name = obj.getString("name");
                int piece = obj.getInt("piece");
                String country = obj.getString("country");

                list.add(new Good(id,name,piece,country));
            }
            table1.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}