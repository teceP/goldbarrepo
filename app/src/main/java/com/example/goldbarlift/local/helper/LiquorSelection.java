package com.example.goldbarlift.local.helper;

import com.example.goldbarlift.local.exception.LiquorAssortmentIDnotFoundException;

public enum LiquorSelection {

    zero(0),
    one(1),
    two(2),
    three(3),
    four(4),
    five(5);

    private int assortment;

    LiquorSelection(int assortment) {
        this.assortment = assortment;
    }

    public int getAssortment() {
        return this.assortment;
    }

    /**
     * Setzt den Grad der Getraenkeauswahl auf den uebergebenen Wert
     *
     * @param assortment
     * @return false wenn unter 0 oder ueber 5
     */
    public boolean setAssortment(int assortment) {
        if (assortment >= 0 || assortment <= 5) {
            this.assortment = assortment;
            return true;
        }
        return false;
    }

    public LiquorSelection getLiquorSelection(int assortment) throws LiquorAssortmentIDnotFoundException {
        switch (assortment) {
            case 0:
                return this.zero;
            case 1:
                return this.one;
            case 2:
                return this.two;
            case 3:
                return this.three;
            case 4:
                return this.four;
            case 5:
                return this.five;
            default:
                throw new LiquorAssortmentIDnotFoundException(assortment);

        }

    }
}
