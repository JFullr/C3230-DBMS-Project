package edu.westga.cs3230.healthcare_dbms.view.embed;

import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class TupleEmbed extends ListView<Node> {
	
	/*
	public TupleEmbed() {
		ObservableList<Label> labels = FXCollections.observableArrayList();
		for(int i = 0; i < 15; i++) {
			labels.add(new Label("Test Data: "+i));
		}
		this.setItems(labels);
		this.setOrientation(Orientation.HORIZONTAL);
		this.setMaxHeight(100.0);
		//System.out.println(this.setFixedCellSize(100.0));
		//this.setPadding(new Insets(0,0,0,0));
	}
	*/
	
	public TupleEmbed(SqlTuple attributes) {
		ObservableList<Node> items = FXCollections.observableArrayList();
		for(SqlAttribute attr : attributes) {
			//System.out.println(attr);
			if(attr.getAttribute() != null) {
				
				GridPane layout = new GridPane();
				
				layout.add(new Label(attr.getAttribute()), 0, 0);
				
				TextArea edit = new TextArea();
				edit.setText(""+attr.getValue());
				edit.setMaxHeight(50.0);
				edit.setMaxWidth(100.0);
				layout.add(edit, 0, 1);
				
				items.add(layout);
			}
		}
		this.setItems(items);
		this.setOrientation(Orientation.HORIZONTAL);
		this.setMaxHeight(150.0);
	}
	
}
