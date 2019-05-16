package com.example.barcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String appPermission = Manifest.permission.CAMERA;
    private static final int PERMISSONS_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions(appPermission);
    }

    private void init(){
        startActivity(new Intent(MainActivity.this, CameraActivity.class));
    }

    private void checkAndRequestPermissions(String permission){
        if (checkPermissions(permission)){
            init();
        } else {
            requestPermissions(permission);
        }
    }

    private void requestPermissions(String permission){
        ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSONS_REQUEST_CODE);
    }

    private boolean checkPermissions(String permission){
        return ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ? false : true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSONS_REQUEST_CODE: {
                // если пользователь закрыл запрос на разрешение, не дав ответа, массив grantResults будет пустым
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    showMessage("Необходимо в настройках приложения разрешить доступ");
                    //Exit
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            System.exit(0);
                        }
                    }, 3000);
                }
                break;
            }
        }
    }

    private void showMessage(@NonNull String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}