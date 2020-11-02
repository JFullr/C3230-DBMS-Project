package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import javafx.beans.property.*;

public class AppointmentCheckupViewModel {
    private IntegerProperty systolicBloodPressureProperty;
    private IntegerProperty diastolicBloodPressureProperty;
    private IntegerProperty pulseProperty;
    private DoubleProperty weightProperty;
    private Appointment appointment;
    private BooleanProperty actionPressedProperty;

    public AppointmentCheckupViewModel() {
        this.systolicBloodPressureProperty = new SimpleIntegerProperty();
        this.diastolicBloodPressureProperty = new SimpleIntegerProperty();
        this.pulseProperty = new SimpleIntegerProperty();
        this.weightProperty = new SimpleDoubleProperty();
        this.actionPressedProperty = new SimpleBooleanProperty();
    }

    public int getSystolicBloodPressureProperty() {
        return systolicBloodPressureProperty.get();
    }

    public IntegerProperty systolicBloodPressureProperty() {
        return systolicBloodPressureProperty;
    }

    public int getDiastolicBloodPressureProperty() {
        return diastolicBloodPressureProperty.get();
    }

    public IntegerProperty diastolicBloodPressureProperty() {
        return diastolicBloodPressureProperty;
    }

    public int getPulseProperty() {
        return pulseProperty.get();
    }

    public IntegerProperty pulseProperty() {
        return pulseProperty;
    }

    public double getWeightProperty() {
        return weightProperty.get();
    }

    public DoubleProperty weightProperty() {
        return weightProperty;
    }

    public BooleanProperty getActionPressedProperty() {
        return actionPressedProperty;
    }

    public AppointmentCheckup getObject() {
        if (appointment == null) throw new IllegalStateException();
        AppointmentCheckup checkup = new AppointmentCheckup();
        checkup.setAppointment_id(this.appointment.getAppointment_id());
        checkup.setSystolic_pressure(this.systolicBloodPressureProperty.get());
        checkup.setDiastolic_pressure(this.diastolicBloodPressureProperty.get());
        checkup.setPulse(this.pulseProperty.get());
        checkup.setWeight(this.weightProperty.get());
        return checkup;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
