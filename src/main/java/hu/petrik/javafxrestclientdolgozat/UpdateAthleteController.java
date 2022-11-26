package hu.petrik.javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class UpdateAthleteController extends Controller{

    @FXML
    private TextField nameField;

    @FXML
    private Spinner<Integer> ageField;

    @FXML
    private TextField homeField;

    @FXML
    private CheckBox activeField;

    @FXML
    private Button updateButton;

    private Athlete athlete;


    public void  setAthlete(Athlete athlete) {
        this.athlete = athlete;
        nameField.setText(this.athlete.getName());
        homeField.setText(this.athlete.getHome());
        ageField.getValueFactory().setValue(this.athlete.getAge());
        activeField.setSelected(this.athlete.isActive());

    }

    @FXML
    private void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, 30);
        ageField.setValueFactory(valueFactory);
    }


    @FXML
    public void  updateClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        String home = homeField.getText().trim();
        int age = ageField.getValue();

        if (name.isEmpty()){
            warning("Név megadása kötelező");
        }
        if (home.isEmpty()){
            warning("Település megadása kötelező");
        }
        this.athlete.setName(name);
        this.athlete.setHome(home);
        this.athlete.setAge(age);
        this.athlete.setActive(activeField.isSelected());
        Gson converter = new Gson();
        String json = converter.toJson(this.athlete);
        try {
            String url = App.BASE_URL + "/" + this.athlete.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {

            } else {
                error("Hiba történt a módosítás során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült kapcsolódni a szerverhez");
        }
    }
}
