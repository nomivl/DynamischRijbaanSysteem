package com.dynamischrijbaansysteem.services;

import com.dynamischrijbaansysteem.LaneManager;
import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.MainApp;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class NavigationService {
    private static NavigationService instance;

    public <T> void goToLanes(T context) {
        loadContent("/lane-overview.fxml", context);
    }
    public <T> void goToLaneDetails(Integer id, T context) {
        loadContent("/lane-details.fxml", context);
    }

    private NavigationService(){

    }

    public static NavigationService getInstance(){
        if (instance == null) {
            instance = new NavigationService();
        }
        return instance;
    }

    private <T> void loadContent(String fxmlPath, T context) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            BorderPane content = loader.load();
            Object controller = loader.getController();

            if(controller instanceof ServiceInjectable) {
                ((ServiceInjectable<T>) controller).setContext(context);
            }
            MainApp.setCenterContent(content);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
