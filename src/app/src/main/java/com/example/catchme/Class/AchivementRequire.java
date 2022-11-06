package com.example.catchme.Class;

public class AchivementRequire {

    public void checkCompleted(Achievement a){
        String b;
        b=a.getId();
        // total distance pass 10km
        if (b.equals("d1")) {
            return;
        }
        // complete the tutorial
        if (b.equals("2")){

        }

        // run at least 2km/day in a row of seven days
        if (b.equals("3")){

        }
        // The more the merrier: Become friend with a person
        if (b.equals("f1")){

        }
        // Friendly man: Become friend with 10 people
        if(b.equals("f2")){

        }

    }

}
