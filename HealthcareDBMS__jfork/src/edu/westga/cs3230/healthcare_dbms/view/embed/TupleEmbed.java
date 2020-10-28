package edu.westga.cs3230.healthcare_dbms.view.embed;

import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTypeConverter;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class TupleEmbed extends ListView<Node> {
	
	private Object operatesOn;
	private Object display;
	private SqlTuple attributes;
	
	private ObservableList<Node> items;
	private BooleanProperty canPost;
	private ObjectProperty<Object> selectedObjectProperty;
	
	public TupleEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		
		this.operatesOn = operatesOn;
		this.display = display;
		this.attributes = attributes;
		this.canPost = new SimpleBooleanProperty(true);
		this.selectedObjectProperty = new SimpleObjectProperty<Object>(null);
		this.items = FXCollections.observableArrayList();
		
		this.generateControlHeader();
		this.generateFieldForms();
		
		this.setItems(items);
		
		this.setOrientation(Orientation.HORIZONTAL);
		this.setMaxHeight(150.0);
	}
	
	public ObjectProperty<Object> getPressedPropertyAction() {
		return this.selectedObjectProperty;
	}
	
	public Object getOperatedObject() {
		/*
		if(this.operatesOn != null) {
			this.fillData();
		}
		*/
		return this.operatesOn;
	}
	
	private void generateControlHeader() {
		
		Button postEdits = new Button("Edit");
		postEdits.disableProperty().bind(this.canPost.not());
		
		postEdits.setOnAction((evt)->{
			this.postTupleObject();
		});
		
		this.items.add(postEdits);
		
	}
	
	private void postTupleObject() {
		this.selectedObjectProperty.setValue(this.operatesOn);
		this.selectedObjectProperty.setValue(null);
	}

	private void generateFieldForms() {
		if(this.attributes == null) {
			return;
		}
		for(SqlAttribute attr : this.attributes) {
			if(attr.getAttribute() != null) {
				
				GridPane layout = new GridPane();
				
				layout.add(new Label(attr.getAttribute()), 0, 0);
				
				TextArea edit = new TextArea();
				edit.setEditable(false);
				edit.setText(""+attr.getValue());
				edit.setMaxHeight(50.0);
				edit.setMaxWidth(100.0);
				edit.textProperty().addListener((evt)->{
					this.attachEditEvent(edit.getText(), attr.getAttribute(), attr.getValue().getClass());
				});
				layout.add(edit, 0, 1);
				
				layout.setDisable(true);
				this.items.add(layout);
			}
		}
	}
	
	private void attachEditEvent(String value, String attribute, Class<?> type) {
		
		Object mutated = SqlTypeConverter.convertStringTo(value, type);
		
		if(mutated != null) {
			
			this.canPost.setValue(this.checkIfEdited());
			
		}
		
	}
	
	private void fillData() {
		
		for(int i = 1; i < this.items.size(); i++) {
			String attribute = ((Label)((GridPane)items.get(i)).getChildren().get(0)).getText();
			String value = ((TextArea)((GridPane)items.get(i)).getChildren().get(1)).getText();
			
			
			SqlAttribute attr = this.attributes.get(attribute);
			Object mutated = SqlTypeConverter.convertStringTo(value, attr.getValue().getClass());
			
			if(attr != null) {
				SqlAttribute replace = new SqlAttribute(attribute, mutated);
				this.attributes.set(attribute, replace);
			}
		}
		
		SqlSetter.fillWith(this.display, this.attributes);
	}
	
	private boolean checkIfEdited() {
		
		for(int i = 1; i < this.items.size(); i++) {
			String attribute = ((Label)((GridPane)items.get(i)).getChildren().get(0)).getText();
			String value = ((TextArea)((GridPane)items.get(i)).getChildren().get(1)).getText();
			
			SqlAttribute attr = this.attributes.get(attribute);
			if(attr != null) {
				if(!(""+attr.getValue()).equalsIgnoreCase(value)){
					return true;
				}
			}
		}
		
		
		return false;
	}
	
}
