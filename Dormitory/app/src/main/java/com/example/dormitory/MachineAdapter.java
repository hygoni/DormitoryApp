package com.example.dormitory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MachineAdapter extends ArrayAdapter<MachineVO>{
    Context context;
    int resId;
    ArrayList<MachineVO> datas;
    LayoutInflater inflater;
    RequestQueue queue;
    String token;
    public MachineAdapter(Context context, int resId, ArrayList<MachineVO> datas){
        super(context,resId);
        this.context = context;
        this.resId = resId;
        this.datas = datas;
    }

    @Override
    public int getCount(){
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resId, null);
            MachineHolder holder=new MachineHolder(convertView);
            convertView.setTag(holder);
        }
        MachineHolder holder = (MachineHolder)convertView.getTag();

        final TextView machineNumberView = holder.machine_number;
        final TextView machineWorkingView = holder.machine_working;
        final Button turnOnButton = holder.turnOnBtn;

        final MachineVO vo = datas.get(position);
        token = vo.token;

        machineNumberView.setText(Integer.toString(position));
        try {
            if(vo.jsonObject.getBoolean("is_working")){
                machineWorkingView.setText("현재 작동중 입니다.");
                turnOnButton.setText("작동 불가");
                turnOnButton.setEnabled(false);
            }else{
                machineWorkingView.setText("사용할 수 있습니다.");
                turnOnButton.setText("작동 가능");
                turnOnButton.setEnabled(true);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        turnOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                try {
                    if (vo.jsonObject.getString("display_name").equals("dryer")) {
                        calendar.add(Calendar.MINUTE,2);
                        //calendar.add(Calendar.MINUTE,50);
                    } else {
                        calendar.add(Calendar.MINUTE,1);
                        //calendar.add(Calendar.MINUTE,43);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 a EE요일 hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(inflater.getContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
                diaryNotification(calendar);

                queue = Volley.newRequestQueue(inflater.getContext());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("building_number", vo.jsonObject.get("building_number"));
                    jsonObject.put("sub_id", vo.jsonObject.get("sub_id"));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://cnuant.iptime.org:8000/startWasher", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onListenResponse: "+"보내져요.");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "onErrorResponse: "+"안보내져요."+machineNumberView.getText().toString()+" "+machineWorkingView.getText().toString());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("X-AUTH-TOKEN", token);
                        return headers;
                    }
                };
                queue.add(jsonObjectRequest);
            }
        });
        return convertView;
    }

    void diaryNotification(Calendar calendar)
    {
        Intent alarmIntent = new Intent(inflater.getContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(inflater.getContext(), 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) inflater.getContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }
}
