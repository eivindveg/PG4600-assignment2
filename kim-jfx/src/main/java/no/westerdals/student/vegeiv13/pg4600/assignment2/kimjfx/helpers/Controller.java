package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.helpers;

import javafx.application.Application;
import javafx.scene.Node;

public abstract class Controller<T extends Application> {

    private T application;

    public void setApplication(T application) {
        this.application = application;
    }

    protected T getApplication() {
        return application;
    }

    public abstract Node getRoot();

}
