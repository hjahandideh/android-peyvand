package com.payvand.jahandideh.payvand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    public static String r = "";
    private EditText username;
    DatabaseHelper mydb;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb=new DatabaseHelper(this);
        Button btn = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        ImageView img1 = (ImageView) findViewById(R.id.imageView);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.peyvand);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
        Bitmap conv_bm = getRoundedRectBitmap(resized, 100);
        img1.setImageBitmap(conv_bm);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Cursor res=mydb.showdata();
        if(res.getCount()==0) {
            assert btn != null;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    login(username.getText().toString(), password.getText().toString());
                }
            });
        }else if(res.getCount()==1) {
            Intent viewActivity = new Intent(MainActivity.this, Peyvand.class);
            startActivity(viewActivity);
            finish();

        }else {
            Toast.makeText(MainActivity.this,"اشکال در برنامه",Toast.LENGTH_SHORT).show();
        }

    }

    private void login(String user, String password) {
        new class_login("http://bemq.ir/login.php", user, password).execute();

        final ProgressDialog c = new ProgressDialog(MainActivity.this);
        c.setMessage("لطفا شکیبا باشید...");
        c.show();
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (r.equals("ok")) {
                            c.cancel();
                            t.cancel();
                            String t = username.getText().toString();
                            boolean f = mydb.insertdata(t);
                            if (f == true) {
                                Intent viewActivity = new Intent(MainActivity.this, Peyvand.class);
                                Bundle bd = new Bundle();
                                bd.putString("username", t);
                                viewActivity.putExtras(bd);
                                startActivity(viewActivity);
                                finish();
                            }
                            else
                                Toast.makeText(MainActivity.this, "no", Toast.LENGTH_SHORT).show();

                            r = "";
                        } else if (r.equals("no user")) {
                            c.cancel();
                            t.cancel();
                            boolean f=mydb.deletdata(username.getText().toString());
                            if(f==true) {
                                Toast.makeText(MainActivity.this, "کاربری با این مشخصات موجود نیست", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(MainActivity.this,"no",Toast.LENGTH_SHORT).show();

                            r = "";

                        } else if (r.equals("wrong password")) {
                            c.cancel();
                            t.cancel();
                            boolean f=mydb.deletdata(username.getText().toString());
                            if(f==true) {
                            Toast.makeText(MainActivity.this, "نام کاربری یا رمز عبور صحیح نیست", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(MainActivity.this,"no",Toast.LENGTH_SHORT).show();
                            r = "";

                        }

                    }
                });

            }
        }, 1, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 350, 350);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(50, 50, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.payvand.jahandideh.payvand/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.payvand.jahandideh.payvand/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
