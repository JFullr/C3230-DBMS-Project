package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.viewmodel.AppointmentCheckupViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AppointmentCheckupGuiCodeBehind {

    @FXML
    public TextField systolicBloodPressureTextField;

    @FXML
    public TextField diastolicBloodPressureTextField;

    @FXML
    public TextField pulseTextField;

    @FXML
    public TextField weightTextField;

    private final AppointmentCheckupViewModel viewModel = new AppointmentCheckupViewModel();

    private UnaryOperator<TextFormatter.Change> integerOnly = change -> {
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(change.getControlNewText()).matches() ? change : null;
    };

    private UnaryOperator<TextFormatter.Change> doubleOnly = change -> {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d*|)");
        return pattern.matcher(change.getControlNewText()).matches() ? change : null;
    };

    @FXML
    public void initialize() {
        this.systolicBloodPressureTextField.setTextFormatter(new TextFormatter<>(integerOnly));
        this.diastolicBloodPressureTextField.setTextFormatter(new TextFormatter<>(integerOnly));
        this.pulseTextField.setTextFormatter(new TextFormatter<>(integerOnly));
        this.weightTextField.setTextFormatter(new TextFormatter<>(doubleOnly));

        this.systolicBloodPressureTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                viewModel.systolicBloodPressureProperty().setValue(Integer.parseInt(newVal));
            }
        });
        this.diastolicBloodPressureTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                viewModel.diastolicBloodPressureProperty().setValue(Integer.parseInt(newVal));
            }
        });
        this.pulseTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                viewModel.pulseProperty().setValue(Integer.parseInt(newVal));
            }
        });
        this.weightTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                viewModel.weightProperty().setValue(Double.parseDouble(newVal));
            }
        });
    }

    public void addCheckupDetails(ActionEvent actionEvent) {
        this.viewModel.getActionPressedProperty().setValue(true);
        this.viewModel.getActionPressedProperty().setValue(false);
    }

    public AppointmentCheckupViewModel getViewModel() {
        return viewModel;
    }

    public void closeWindow() {
        Stage stage = (Stage) this.systolicBloodPressureTextField.getScene().getWindow();
        stage.close();
    }
}
