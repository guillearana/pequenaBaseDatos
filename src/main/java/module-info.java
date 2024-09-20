module es.guillearana.pequenabasedatos {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.guillearana.pequenabasedatos to javafx.fxml;
    exports es.guillearana.pequenabasedatos;
}