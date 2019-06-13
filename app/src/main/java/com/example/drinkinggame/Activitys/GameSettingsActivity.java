package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.drinkinggame.Models.Game;
import com.example.drinkinggame.R;

public class GameSettingsActivity extends AppCompatActivity {
	private Button next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_settings);

		next = findViewById(R.id.settings_next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GameSettingsActivity.this, PlayerSelectionActivity.class));
			}
		});
	}
}
