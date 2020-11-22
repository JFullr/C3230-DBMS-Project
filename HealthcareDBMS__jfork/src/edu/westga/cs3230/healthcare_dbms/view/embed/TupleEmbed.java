package edu.westga.cs3230.healthcare_dbms.view.embed;


import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * Represents a tuple in a {@link ListView}.
 *
 * @author Joseph Fuller
 */
public class TupleEmbed extends ListView<Node> {
	
	/** The operates on. */
	private Object operatesOn;
	
	/** The display. */
	private Object display;
	
	/** The attributes. */
	private SqlTuple attributes;
	
	/** The items. */
	private ObservableList<Node> items;
	
	/** The can post. */
	private BooleanProperty canPost;
	
	/** The selected object property. */
	private ObjectProperty<Object> selectedObjectProperty;
	
	/**
	 * Instantiates a new tuple embed.
	 *
	 * @param operatesOn the operates on
	 * @param display the display
	 * @param attributes the attributes
	 */
	public TupleEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		
		this.operatesOn = operatesOn;
		this.display = display;
		this.attributes = attributes;
		
		if(attributes == null && operatesOn != null) {
			SqlGetter.getFrom(operatesOn);
		}
		
		this.canPost = new SimpleBooleanProperty(true);
		this.selectedObjectProperty = new SimpleObjectProperty<Object>(null);
		this.items = FXCollections.observableArrayList();
		
		this.generateControlHeader();
		this.generateFieldForms();
		
		this.setItems(items);
		
		this.setOrientation(Orientation.HORIZONTAL);
		this.setMaxHeight(150.0);
		
		this.setMouseTransparent(true);
		
	}
	
	/**
	 * Gets the pressed property action.
	 *
	 * @return the pressed property action
	 */
	public ObjectProperty<Object> getPressedPropertyAction() {
		return this.selectedObjectProperty;
	}
	
	/**
	 * Gets the operated object.
	 *
	 * @return the operated object
	 */
	public Object getOperatedObject() {
		return this.operatesOn;
	}
	
	/**
	 * Gets the display.
	 *
	 * @return the display
	 */
	public Object getDisplay() {
		return this.display;
	}
	
	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public SqlTuple getAttributes() {
		return this.attributes;
	}
	
	/**
	 * Update attribute.
	 *
	 * @param attribute the attribute
	 * @param result the result
	 */
	public void updateAttribute(String attribute, Object result) {
		this.attributes.set(attribute, new SqlAttribute(attribute, result));
		Node header = this.items.get(0);
		this.items.clear();
		this.items.add(header);
		this.generateFieldForms();
	}
	
	/**
	 * Generate control header.
	 */
	private void generateControlHeader() {
		
		Button postEdits = new Button("Edit");
		postEdits.disableProperty().bind(this.canPost.not());
		
		postEdits.setOnAction((evt)->{
			this.postTupleObject();
		});
		
		this.items.add(postEdits);
		
	}
	
	/**
	 * Gets the copy.
	 *
	 * @return the copy
	 */
	public TupleEmbed getCopy() {
		TupleEmbed copy = new TupleEmbed(this.getOperatedObject(), this.getDisplay(), this.getAttributes());
		return copy;
	}
	
	/**
	 * Post tuple object.
	 */
	private void postTupleObject() {
		this.selectedObjectProperty.setValue(this.operatesOn);
		this.selectedObjectProperty.setValue(null);
	}

	/**
	 * Generate field forms.
	 */
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
				layout.add(edit, 0, 1);
				this.items.add(layout);
			}
		}
	}
	
}
