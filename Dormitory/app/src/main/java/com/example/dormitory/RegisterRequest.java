package com.example.dormitory;

        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://";
    private Map<String,String> parameters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userNickname, String userDong, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userGender",userGender);
        parameters.put("userNickname",userNickname);
        parameters.put("userDong",userDong);
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
