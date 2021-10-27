module ru.masmirnov.sd.bridge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ru.masmirnov.sd.bridge to javafx.fxml;
    exports ru.masmirnov.sd.bridge;
    exports ru.masmirnov.sd.bridge.engine;
    opens ru.masmirnov.sd.bridge.engine to javafx.fxml;
}