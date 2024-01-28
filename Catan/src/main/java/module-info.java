module hr.tvz.catan.catan {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.xml;


    opens hr.tvz.catan.catan to javafx.fxml;
    exports hr.tvz.catan.catan;
}