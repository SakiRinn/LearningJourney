module uk.qmul.learningjourney {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires atlantafx.base;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    requires poi.ooxml;
    requires poi.ooxml.schemas;
    requires fr.opensagres.xdocreport.document;
    requires fr.opensagres.xdocreport.template;
    requires fr.opensagres.xdocreport.core;

    opens uk.qmul.learningjourney to javafx.fxml;
    exports uk.qmul.learningjourney;
    exports uk.qmul.learningjourney.grade;
    opens uk.qmul.learningjourney.grade to javafx.fxml;
}