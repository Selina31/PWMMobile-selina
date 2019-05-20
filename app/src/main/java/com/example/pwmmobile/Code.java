package com.example.pwmmobile;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

public class Code {

    public static String getNextCode(DataSnapshot d) {
        String thStr;
        String huStr;
        String teStr;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    thStr = Integer.toString(i);
                    huStr = Integer.toString(j);
                    teStr = Integer.toString(k);

                    boolean free = d.child(thStr).child(huStr).child(teStr).getValue(Boolean.class);
                    if (free) {
                        return thStr + huStr + teStr;
                    }
                }
            }
        }
        return null;
    }
}
