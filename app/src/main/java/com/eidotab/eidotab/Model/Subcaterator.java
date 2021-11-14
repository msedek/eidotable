package com.eidotab.eidotab.Model;

import java.util.Comparator;


public class Subcaterator implements Comparator<Plato>{


    public int compare(Plato cmd1, Plato cmd2)
    {

        int stringResult = cmd1.getCategoria_plato().compareTo(cmd2.getCategoria_plato());
        if (stringResult == 0)
        {
            // Strings are equal, sort by subcate
            return cmd1.getSub_categoria_plato().compareTo(cmd2.getSub_categoria_plato());
        }
        else
        {

            return stringResult;
        }
    }

}
