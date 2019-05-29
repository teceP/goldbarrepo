package com.example.goldbarlift.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.example.goldbarlift.R;

public class MyDrawables {

    private Resources resources;

    public MyDrawables(Resources resources){
        this.resources = resources;
    }

    public Drawable[] getDrawables(){

        Drawable[] standarts = new Drawable[8];
        standarts[0] = this.resources.getDrawable(R.drawable.standart1);
        standarts[1] = this.resources.getDrawable(R.drawable.standart2);
        standarts[2] = this.resources.getDrawable(R.drawable.standart3);
        standarts[3] = this.resources.getDrawable(R.drawable.standart4);
        standarts[4] = this.resources.getDrawable(R.drawable.standart5);
        standarts[5] = this.resources.getDrawable(R.drawable.standart6);
        standarts[6] = this.resources.getDrawable(R.drawable.standart7);
        standarts[7] = this.resources.getDrawable(R.drawable.standart8);
        return standarts;
    }
}
