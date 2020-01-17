package com.example.dormitory;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MachineHolder {
    public TextView machine_number;
    public TextView machine_working;
    public Button turnOnBtn;

    public MachineHolder(View root){
        machine_number = root.findViewById(R.id.machine_num);
        machine_working = root.findViewById(R.id.machine_working);
        turnOnBtn = root.findViewById(R.id.machine_turnOn);
    }
}
