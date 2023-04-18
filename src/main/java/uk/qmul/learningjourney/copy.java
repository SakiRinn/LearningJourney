package uk.qmul.learningjourney;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class copy extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Log_inApp.class.getResource("Log_in_view.fxml"));

        TextField t_name = new TextField();
        t_name.setUserData("a");//给一个测试数据
        PasswordField p_pswd = new PasswordField();
        p_pswd.setUserData("aa");//给一个测试数据


        Label l_name = new Label("stunumber：");
        l_name.setFont(new Font(20));//字体大小
        l_name.setTooltip(new Tooltip("please enter your student number"));
        Label l_pswd = new Label("password：");
        l_pswd.setTooltip(new Tooltip("please enter your password"));
        l_pswd.setFont(new Font(20));


        Button login = new Button("Login");
        Button clear = new Button("Clear");

        Image img = new Image("D:\\Code_field\\LearningJourney\\src\\main\\resources\\image\\log_in_bg.jpg");
        BackgroundImage bimg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bg = new Background(bimg);

        GridPane gr = new GridPane();
        Scene scene = new Scene(fxmlLoader.load(), 720, 360);
        gr.add(l_name, 0, 0);
        gr.add(t_name, 1, 0);
        gr.add(l_pswd, 0, 1);
        gr.add(p_pswd, 1, 1);
        gr.add(clear, 0, 2);
        gr.add(login, 1, 2);

        gr.setAlignment(Pos.CENTER);
        gr.setHgap(10);//设置水平间距
        gr.setVgap(17);//设置垂直间距
        gr.setBackground(bg);
        GridPane.setMargin(login, new Insets(0, 0, 0, 120));


        //清除事件
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                t_name.setText("");
                p_pswd.setText("");//变空或者p.clear
            }
        });

        //登录事件
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String name = t_name.getText();

                String pswd = p_pswd.getText();
                if (t_name.getUserData().equals(name) && p_pswd.getUserData().equals(pswd)) {
                    System.out.println("登陆成功！");
                    primaryStage.close();
                } else {
                    System.out.println("登录失败！");
                    l_name.setTextFill(Color.CORAL);
                    FadeTransition tst = new FadeTransition();
                    tst.setDuration(Duration.seconds(0.2));
                    tst.setNode(gr);
                    tst.setFromValue(0);
                    tst.setToValue(1);
                    tst.play();
                }

            }

        });


        primaryStage.setScene(scene);
        primaryStage.setTitle("QM+ Log in ");
        primaryStage.setResizable(false); //登录窗口的大小不允许改变
        primaryStage.show();
    }


}


