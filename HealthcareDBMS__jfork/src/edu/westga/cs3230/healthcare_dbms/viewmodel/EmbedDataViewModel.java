/*
 * 
 */
package edu.westga.cs3230.healthcare_dbms.viewmodel;

import javafx.beans.property.SimpleStringProperty;

// TODO: Auto-generated Javadoc
/**
 * ViewModel for attributes embedded in the horizontal list.
 *
 * @author 
 */
public class EmbedDataViewModel {

	/** The attribute label. */
	private SimpleStringProperty attributeLabel;
	
	/** The value label. */
	private SimpleStringProperty valueLabel;

	/**
	 * Instantiates a new healthcare embed data view model.
	 */
	public EmbedDataViewModel() {
		
		//TODO move to embed action view model
		//this.closeActionProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>();

		this.attributeLabel = new SimpleStringProperty();
		this.valueLabel = new SimpleStringProperty();
	}

	/**
	 * Sets the text.
	 *
	 * @param attribute the attribute
	 * @param value the value
	 */
	public void setText(String attribute, String value) {
		this.attributeLabel.setValue(attribute);
		this.valueLabel.setValue(value);
	}
	
	/**
	 * Gets the attribute label property.
	 *
	 * @return the attribute label property
	 */
	public SimpleStringProperty getAttributeLabelProperty() {
		return this.attributeLabel;
	}

	/**
	 * Gets the value label property.
	 *
	 * @return the value label property
	 */
	public SimpleStringProperty getValueLabelProperty() {
		return this.valueLabel;
	}
	
	//TODO move to embed action view model
	//
	// /**
	// * Gets the close action property.
	// *
	// * @return the close action property
	// */
	//public ObjectProperty<EventHandler<ActionEvent>> getCloseActionProperty() {
	//	return this.closeActionProperty;
	//}

}
