package uk.qmul.learningjourney;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        t_num.setUserData("a");
        p_pswd.setUserData("aa");
        String num = t_num.getText();
        String pswd = p_pswd.getText();
        if (t_num.getUserData().equals(num) && p_pswd.getUserData().equals(pswd)) {
            System.out.println("登陆成功！");
            myWindow MWD = new myWindow(num, pswd);
        } else {
            System.out.println("登录失败！");
        }
    }
}


class myWindow {

    public myWindow(String number, String password) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("home-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 720, 449);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("QM+ Home ");
        stage.setResizable(false); //登录窗口的大小不允许改变
        stage.show();
    }
}



