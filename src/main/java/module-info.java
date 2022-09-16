module awale.awalegraphique {
    requires javafx.controls;
    requires javafx.fxml;


    opens awale.awalegraphique to javafx.fxml;
    exports awale.awalegraphique;
}