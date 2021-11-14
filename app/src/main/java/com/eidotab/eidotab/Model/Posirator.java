package com.eidotab.eidotab.Model;


import java.util.Comparator;

public class Posirator implements Comparator<Itemorder>
{
    public int compare(Itemorder one, Itemorder another){
        return one.getPosi().compareTo(another.getPosi());
    }


}
