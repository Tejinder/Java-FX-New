package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.util.Callback;
import sample.dao.JdbcDerbyConnection;
import sample.model.Employee;
import sample.util.AlertHelper;

public class EmployeeController implements Initializable {
    @FXML private TableView<Employee> tableView;
    @FXML private TextField name;
    @FXML private TextField department;
    @FXML private TextField position;
    
	@FXML
	private TableColumn nameColumn;
	
	@FXML
	private TableColumn departmentColumn;
	
	@FXML
	private TableColumn positionColumn;
	
    private ObservableList<Employee> data;

 

    @SuppressWarnings("unchecked")
	@FXML
    public void initialize(URL location, ResourceBundle resources) {
    
    
    	tableView.setEditable(true);
    	
    	Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                       return new EditingCell();
                    }
                };
                
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("name"));
        nameColumn.setCellFactory(cellFactory);
        
        //Handling the edit event for Name column
        nameColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Employee, String>>() {
                @Override
                public void handle(CellEditEvent<Employee, String> t) {
                    ((Employee) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    JdbcDerbyConnection.updateEmployeeName(t.getNewValue(), t.getRowValue().getId());
                    System.out.println("Id -->"+ t.getRowValue().getId());
                }
            }
        );
        tableView.setEditable(true);
    	
    
       departmentColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("department"));
        departmentColumn.setCellFactory(cellFactory);
        
        //Handling the edit event for Department column
        departmentColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Employee, String>>() {
                @Override
                public void handle(CellEditEvent<Employee, String> t) {
                    ((Employee) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    JdbcDerbyConnection.updateEmployeeDepartment(t.getNewValue(), t.getRowValue().getId());
                    System.out.println("Id -->"+ t.getRowValue().getId());
                }
            }
        );
   
        positionColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("position"));
        positionColumn.setCellFactory(cellFactory);
        
        //Handling the edit event for Position column
        positionColumn.setOnEditCommit(
            new EventHandler<CellEditEvent<Employee, String>>() {
                @Override
                public void handle(CellEditEvent<Employee, String> t) {
                    ((Employee) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    JdbcDerbyConnection.updateEmployeePosition(t.getNewValue(), t.getRowValue().getId());
                    System.out.println("Id -->"+ t.getRowValue().getId());
                }
            }
        );
    
    	data = FXCollections.observableArrayList(JdbcDerbyConnection.getAllEmployees());
     
        tableView.setItems(data); 
    }
    @FXML
    protected void addEmployee(ActionEvent event) {
    	 Window owner = tableView.getScene().getWindow();
         
    	 // Adding the validation for the fields
    	 if(name.getText().isEmpty()) {
             AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                     "Please enter your name");
             return;
         }
         if(department.getText().isEmpty()) {
             AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                     "Please enter your department");
             return;
         }
         if(position.getText().isEmpty()) {
             AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", 
                     "Please enter your position");
             return;
         }
         
    	ObservableList<Employee> data = tableView.getItems();
    
    	// Inserting the employee data in database
        JdbcDerbyConnection.insertEmployee(name.getText(), department.getText(), position.getText());
    	data.clear();
    	data.addAll(FXCollections.observableArrayList(JdbcDerbyConnection.getAllEmployees()));
       
        name.setText("");
        department.setText("");
        department.setText("");   
    }
    
    class EditingCell extends TableCell<Employee, String> {
    	 
        private TextField textField;
 
        public EditingCell() {
        }
 
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
 
            setText((String) getItem());
            setGraphic(null);
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
                public void changed(ObservableValue<? extends Boolean> arg0, 
                    Boolean arg1, Boolean arg2) {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}