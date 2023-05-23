package uk.qmul.learningjourney.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.util.CourseUtil;

import java.io.IOException;


public class LoginController {

    @FXML
    public TextField t_num;
    public TextField p_pswd;
    public Pane gr;

    //清除事件
    @FXML
    public void onclear() {
            t_num.setText("");
            p_pswd.setText("");
    }


    //登录
    @FXML
    public void onlogin() throws IOException {

//        t_num.setUserData("a");
//        p_pswd.setUserData("aa");
        String num = t_num.getText();
        String pswd = p_pswd.getText();
        if (CourseUtil.login(num, pswd)) {
            Context.stage.close();
            Context.setHomeScene();
            Context.toNextScene(Context.homeScene);
            Context.stage.show();
            System.out.println("Login success!");
        } else {
            System.out.println("Login fail!");
        }
    }
}



