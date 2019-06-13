package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.drinkinggame.Models.Player;
import com.example.drinkinggame.R;

public class PlayerSelectionActivity extends AppCompatActivity {
	private Button nextButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_selection);
		nextButton = findViewById(R.id.player_selection_next);

		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PlayerSelectionActivity.this, GameActivity.class));
				finish();
			}
		});
	}
}
