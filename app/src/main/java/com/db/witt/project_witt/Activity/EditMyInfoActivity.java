package com.db.witt.project_witt.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.db.witt.project_witt.R;
import com.db.witt.project_witt.Request.UserInfoUpdateRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.db.witt.project_witt.Activity.MainActivity.userEmail;

public class EditMyInfoActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userGender;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_info);

        final EditText userNicknameText = (EditText) findViewById(R.id.userNicknameText);

        RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.GenderRadioGroup);
        int genderGroupID = genderRadioGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString();
            }
        });

        spinner = (Spinner)findViewById(R.id.AgeSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Age, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Button editBt = (Button) findViewById(R.id.editButton);
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정하는 Request 요청
                String userNickname = userNicknameText.getText().toString().trim();
                String userAge = spinner.getSelectedItem().toString();

                if(userNickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditMyInfoActivity.this);
                    dialog = builder.setMessage("닉네임은 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditMyInfoActivity.this);
                                dialog = builder.setMessage("회원 정보 수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditMyInfoActivity.this);
                                dialog = builder.setMessage("회원 정보 수정에 실패했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                UserInfoUpdateRequest infoupdaterequest = new UserInfoUpdateRequest(userEmail, userGender, userNickname, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditMyInfoActivity.this);
                queue.add(infoupdaterequest);
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "http://ec2-13-209-75-74.ap-northeast-2.compute.amazonaws.com/UserInfoRequest.php?userEmail=" + URLEncoder.encode(userEmail,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                JSONObject object = jsonArray.getJSONObject(0);
                String userGender = object.getString("userGender");
                String userNickname = object.getString("userNickname");
                String userAge = object.getString("userAge");

                TextView userEmailText = (TextView) findViewById(R.id.userEmailText);
                EditText userNicknameText = (EditText) findViewById(R.id.userNicknameText);

                RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.GenderRadioGroup);
                RadioButton womanBt = (RadioButton) findViewById(R.id.Woman);
                RadioButton manBt = (RadioButton) findViewById(R.id.Man);

                userEmailText.setText(userEmail);
                userNicknameText.setText(userNickname);

                switch (userGender){
                    case "여자": genderRadioGroup.check(womanBt.getId());
                        break;
                    case "남자": genderRadioGroup.check(manBt.getId());
                        break;
                }

                switch (userAge){
                    case "10대": spinner.setSelection(0);
                    break;
                    case "20대": spinner.setSelection(1);
                    break;
                    case "30대": spinner.setSelection(2);
                    break;
                    case "40대": spinner.setSelection(3);
                    break;
                    case "50대": spinner.setSelection(4);
                    break;
                    case "60대 이상": spinner.setSelection(5);
                    break;
                }

            }catch (Exception e){
                e.printStackTrace();
                Log.i("Target:",target);
            }
        }
    }
}
