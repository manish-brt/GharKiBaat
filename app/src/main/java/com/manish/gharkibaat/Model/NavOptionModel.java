package com.manish.gharkibaat.Model;

/**********************************
 * Created by Manish on 17-Oct-18
 ***********************************/
public class NavOptionModel {
    Class activotyName;
    String title;

    public NavOptionModel(Class activotyName, String title) {
        this.activotyName = activotyName;
        this.title = title;
    }

    public Class getActivotyName() {
        return activotyName;
    }

    public void setActivotyName(Class activotyName) {
        this.activotyName = activotyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
