package com.visualcamp.test.tedbottompicker;

import android.Manifest;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import gun0912.tedbottompicker.TedBottomPicker;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  ImageView imageView;
  Button button;
  public RequestManager mGlideRequestManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    permission();

    mGlideRequestManager = Glide.with(this);
    imageView = (ImageView) findViewById(R.id.imageView);
    button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(MainActivity.this)
            .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
              @Override
              public void onImageSelected(final Uri uri) {
                // uri 활용
                imageView.post(new Runnable() {
                  @Override
                  public void run() {
                    mGlideRequestManager
                        .load(uri)
                        .into(imageView);
                  }
                });
              }
            })
            .setPeekHeight(1200)
            .create();

        bottomSheetDialogFragment.show(getSupportFragmentManager());

      }
    });

  }

  private void permission() {
    TedPermission.with(this)
        .setPermissionListener(permissionListener)
        .setRationaleMessage("접근 권한이 필요합니다")
        .setDeniedMessage("추후 [설정] > [권한] 에서 권한을 허용할 수 있습니다.")
        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .check();
  }

  PermissionListener permissionListener = new PermissionListener() {
    @Override
    public void onPermissionGranted() {
      Toast.makeText(MainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
      Toast
          .makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT)
          .show();
    }
  };
}
