package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.drinkinggame.Models.FileTransfer;
import com.example.drinkinggame.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {
	private Button newGameButton, packageButton;
	public final static String PACKAGES_DIR_PATH = "/storage/emulated/0/DrinkingGamePackages";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		File packageDir = new File(PACKAGES_DIR_PATH);
		if(!packageDir.exists()){
			if(!packageDir.mkdir()){
				finish();
			}
		}

		Log.d("files", new FileTransfer().getAllPackageNames().toString());

		newGameButton = findViewById(R.id.main_menu_new_game);
		packageButton = findViewById(R.id.main_menu_manage_packages);

		newGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, GameSettingsActivity.class));
			}
		});

		packageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
}
