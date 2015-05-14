package no.westerdals.student.vegeiv13.pg4600.assignment2.kimapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, mContentFrame);

        setupPlayButton();
    }

    private void setupPlayButton() {
        final Button button = (Button) findViewById(R.id.play_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        startActivity(new Intent(this, GameActivity.class));
    }
}
