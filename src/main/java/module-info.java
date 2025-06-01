module proyecto2.proyecto2anddyprendasmatarrita {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
    opens proyecto2.proyecto2anddyprendasmatarrita to javafx.fxml, org.hibernate.orm.core;
    exports proyecto2.proyecto2anddyprendasmatarrita;
}
