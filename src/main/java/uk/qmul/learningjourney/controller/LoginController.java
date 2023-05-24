package uk.qmul.learningjourney.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import uk.qmul.learningjourney.Context;

import java.io.IOException;


public class LoginController {

    @FXML
    public TextField t_num;
    public TextField p_pswd;

    //清除事件
    @FXML
    public void onClear() {
        t_num.setText("");
        p_pswd.setText("");
    }

    //登录
    @FXML
    public void onLogin() throws IOException {
        String num = t_num.getText();
        String pswd = p_pswd.getText();
        if (Context.login(num, pswd))
            Context.toStudentHome();
        else
            Context.showError("Login failed! Please enter again.");
    }
}



