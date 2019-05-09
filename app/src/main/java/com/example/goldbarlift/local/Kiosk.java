package com.example.goldbarlift.local;

import com.example.goldbarlift.local.exception.LiquorAssortmentIDnotFoundException;
import com.example.goldbarlift.local.helper.LiquorSelection;

public class Kiosk extends Local {

    private LiquorSelection liquorSelection;

    public Kiosk(int liquorSelecetion) throws LiquorAssortmentIDnotFoundException {
        this.liquorSelection = this.liquorSelection.getLiquorSelection(liquorSelecetion);
    }

    public LiquorSelection getLiquorSelection(){
        return this.liquorSelection;
    }

}
