package uk.qmul.learningjourney.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import kotlin.NotImplementedError;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;

import java.io.IOException;

/**
 * Controller of login-view
 *
 * @author Shuocheng Guo
 */
public class LoginController {
    /**
     * ID
     */
    @FXML
    private TextField t_num;
    /**
     * Password
     */
    @FXML
    private TextField p_pswd;

    /**
     * Event controller of clear button
     */
    @FXML
    public void onClear() {
        t_num.setText("");
        p_pswd.setText("");
    }

    /**
     * Event controller of login button
     * @throws IOException IOException
     */
    @FXML
    public void onLogin() throws IOException {
        String num = t_num.getText();
        String pswd = p_pswd.getText();
        if (Context.login(num, pswd)) {
            if (Context.user instanceof Student)
                Context.toStudentHome();
            else if (Context.user instanceof Teacher)
                Context.toTeacherHome();
            else
                throw new NotImplementedError();
        }
        else
            Context.showError("Login failed! Please enter again.");
    }
}



