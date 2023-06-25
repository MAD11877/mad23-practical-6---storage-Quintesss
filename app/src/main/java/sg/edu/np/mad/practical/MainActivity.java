package sg.edu.np.mad.practical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("", "App started!");
        Button followButton = findViewById(R.id.button_fllw);
        TextView userHeader = findViewById(R.id.textView);
        TextView userDesc = findViewById(R.id.textView2);
        MyDBHandler myDB = new MyDBHandler(MainActivity.this, null, null, 1);

        //Get username from ListActivity
        User newUser = getIntent().getParcelableExtra("User");

        userHeader.setText(newUser.getName());
        userDesc.setText(newUser.getDescription());
        if (newUser.getFollowed()==true){
            followButton.setText("UNFOLLOW");
        }else{
            followButton.setText("FOLLOW");
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("","Follow button clicked");
                if (newUser.getFollowed() == Boolean.FALSE ){
                    newUser.setFollowed(Boolean.TRUE);
                    followButton.setText("UNFOLLOW");
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                }
                else{
                    newUser.setFollowed(Boolean.FALSE);
                    followButton.setText("FOLLOW");
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                }
                myDB.updateUser(newUser.getName());
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();


    }
}