/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableView;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import models.Student;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddStudentController implements Initializable {

    @FXML
    private JFXTextField nameFld;
    @FXML
    private JFXDatePicker birthFld;
    @FXML
    private JFXTextField adressFld;
    @FXML
    private JFXTextField emailFld;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Student student = null;
    private boolean update;
    int studentId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(MouseEvent event) {

        connection = DbConnect.getConnect();
        String name = nameFld.getText();
        String birth = String.valueOf(birthFld.getValue());
        String adress = adressFld.getText();
        String email = emailFld.getText();

        if (name.isEmpty() || birth.isEmpty() || adress.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }

    }

    @FXML
    private void clean() {
        nameFld.setText(null);
        birthFld.setValue(null);
        adressFld.setText(null);
        emailFld.setText(null);
        
    }

    private void getQuery() {

        if (update == false) {
            
            query = "INSERT INTO `student`( `name`, `birth`, `adress`, `email`) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE `student` SET "
                    + "`name`=?,"
                    + "`birth`=?,"
                    + "`adress`=?,"
                    + "`email`= ? WHERE id = '"+studentId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameFld.getText());
            preparedStatement.setString(2, String.valueOf(birthFld.getValue()));
            preparedStatement.setString(3, adressFld.getText());
            preparedStatement.setString(4, emailFld.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(int id, String name, LocalDate toLocalDate, String adress, String email) {

        studentId = id;
        nameFld.setText(name);
        birthFld.setValue(toLocalDate);
        adressFld.setText(adress);
        emailFld.setText(email);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

}
