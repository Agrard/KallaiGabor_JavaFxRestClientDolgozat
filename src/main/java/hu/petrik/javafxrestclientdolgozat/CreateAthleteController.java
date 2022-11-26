package hu.petrik.javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreateAthleteController  extends Controller{
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> ageField;
    @FXML
    private TextField homeField;
    @FXML
    private CheckBox activeField;
    @FXML
    private Button submitButton;

    @FXML
    private void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(15, 100, 18);
                ageField.setValueFactory(valueFactory);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        String home = homeField.getText().trim();
        int age = ageField.getValue();

        if (name.isEmpty()) {
            warning("Név megadása kötelező!");
            return;
        }
        if (home.isEmpty()) {
            warning("Otthon megadása kötlező!");
            return;
        }
        if (age > 35 && activeField.isSelected()) {
            warning("Ez az athlete túl idős már hogy aktív legyen! (max 35)");
            return;
        }
        if (age < 18 && activeField.isSelected()) {
            warning("Ez az athlete túl fiatal legalább 18 éves kell hogy aktív legyen!");
            return;
        }
        Athlete newAthlete = new Athlete(0, name, age, home, activeField.isSelected());

        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newAthlete);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                nameField.setText("");
                homeField.setText("");
                ageField.getValueFactory().setValue(18);
            } else {
                error("Hiba történt a felvétel során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült kapcsolódni a szerverhez");
        }
    }
}
