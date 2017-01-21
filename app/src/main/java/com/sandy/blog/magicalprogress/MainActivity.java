package com.sandy.blog.magicalprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private MagicalProgressBar magicalProgressBar;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        magicalProgressBar = (MagicalProgressBar) findViewById(R.id.magical_progress);
        magicalProgressBar.setFinishedColor(getResources().getColor(R.color.colorAccent));
        magicalProgressBar.setUnfinishedColor(getResources().getColor(R.color.colorPrimaryDark));
        magicalProgressBar.setCenterColor(getResources().getColor(R.color.colorPrimary));

        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        magicalProgressBar.setProgress(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
