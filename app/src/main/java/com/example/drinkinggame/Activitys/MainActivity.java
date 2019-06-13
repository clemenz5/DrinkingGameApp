package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.drinkinggame.R;

public class MainActivity extends AppCompatActivity {
	private Button newGameButton, packageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		newGameButton = findViewById(R.id.main_menu_new_game);
		packageButton = findViewById(R.id.main_menu_manage_packages);

		newGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, GameSettingsActivity.class));
				Toast.makeText(MainActivity.this, "new Game", Toast.LENGTH_LONG).show();
			}
		});

		packageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "packages", Toast.LENGTH_LONG).show();
			}
		});
	}
}
