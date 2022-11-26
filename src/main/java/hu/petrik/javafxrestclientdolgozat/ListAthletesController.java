package hu.petrik.javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ListAthletesController extends Controller {
@FXML
private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Athlete> athleteTable;
    @FXML
    private TableColumn<Athlete, Integer> idCol;
    @FXML
    private TableColumn<Athlete, String> nameCol;
    @FXML
    private TableColumn<Athlete, Integer> ageCol;
    @FXML
    private TableColumn<Athlete, String> homeCol;
    @FXML
    private TableColumn<Athlete, Boolean> activeCol;

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        homeCol.setCellValueFactory(new PropertyValueFactory<>("home"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        Platform.runLater(() -> {
            try {
                loadAthleteFromServer();
            } catch (IOException e) {
                error("Hiba történt az adatok lekérése során", e.getMessage());
                Platform.exit();
            }
        });
    }

    private void loadAthleteFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Athlete[] people = converter.fromJson(content, Athlete[].class);
        athleteTable.getItems().clear();
        for (Athlete person : people) {
            athleteTable.getItems().add(person);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-Athlete-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Create Athlete");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                try {
                    loadAthleteFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        Athlete selected = athleteTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            warning("Módosításhoz előbb válasszon ki egy elemet!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-athlete-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 480);
            UpdateAthleteController controller = fxmlLoader.getController();
            controller.setAthlete(selected);
            Stage stage = new Stage();
            stage.setTitle("Update "+ selected.getName());
            stage.setScene(scene);
            stage.setOnHidden(event -> {
                try {
                    loadAthleteFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
        }
    }

    @FXML public void deleteClick(ActionEvent actionEvent) {
        Athlete selected = athleteTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            warning("Törléshez előbb válasszon ki egy elemet");
            return;
        }


    Optional<ButtonType> optionalButtonType =
            alert(Alert.AlertType.CONFIRMATION, "Biztos?",
                    "Biztos, hogy törölni szeretné az alábbi rekordot: "
            + selected.getName(),
                    "");
            if(optionalButtonType.isPresent() &&
                optionalButtonType.get().equals(ButtonType.OK)
            ) {
                String url = App.BASE_URL + "/" + selected.getId();
                try {
                    RequestHandler.delete(url);
                    loadAthleteFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            }
    }
}