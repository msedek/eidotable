package com.eidotab.eidotab;

import android.app.Application;



public class MyApplication extends Application
{
    private Boolean animatemainmenu = true;
    private Boolean animatesubmainmenu = true;
    private Boolean animatesubmenu = true;
    private Boolean animateplato = true;


    public Boolean getAnimatemainmenu() {
        return animatemainmenu;
    }

    public void setAnimatemainmenu(Boolean animatemainmenu) {
        this.animatemainmenu = animatemainmenu;
    }

    public Boolean getAnimatesubmainmenu() {
        return animatesubmainmenu;
    }

    public void setAnimatesubmainmenu(Boolean animatesubmainmenu) {
        this.animatesubmainmenu = animatesubmainmenu;
    }

    public Boolean getAnimatesubmenu() {
        return animatesubmenu;
    }

    public void setAnimatesubmenu(Boolean animatesubmenu) {
        this.animatesubmenu = animatesubmenu;
    }

    public Boolean getAnimateplato() {
        return animateplato;
    }

    public void setAnimateplato(Boolean animateplato) {
        this.animateplato = animateplato;
    }

}
