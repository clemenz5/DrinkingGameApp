package com.example.drinkinggame.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.drinkinggame.Models.Card;
import com.example.drinkinggame.Models.CardType;
import com.example.drinkinggame.Models.FileTransfer;
import com.example.drinkinggame.Models.Package;
import com.example.drinkinggame.R;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private Button packageButton;
	private ImageView newGameButton;
	public final static String PACKAGES_DIR_PATH = "/storage/emulated/0/DrinkingGamePackages";
	private boolean readAccess;
	private boolean writeAccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
					0);
		}
		File packageDir = new File(PACKAGES_DIR_PATH);
		if (!packageDir.exists()) {
			if (!packageDir.mkdir()) {
				finish();
			}else{
				Package testPackage = new Package();
				testPackage.setName("testPackage");
				testPackage.addCard(new Card("frage 1", 5, CardType.QUESTION));
				testPackage.addCard(new Card("frage 2", 5, CardType.QUESTION));
				testPackage.addCard(new Card("frage 3", 5, CardType.QUESTION));
				testPackage.addCard(new Card("frage 4", 5, CardType.QUESTION));
				testPackage.addCard(new Card("frage 5", 5, CardType.QUESTION));
				testPackage.addCard(new Card("frage 6", 5, CardType.QUESTION));
				FileTransfer.savePackage(testPackage);
			}
		}

		newGameButton = findViewById(R.id.main_menu_new_game);
		packageButton = findViewById(R.id.main_menu_manage_packages);

		newGameButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GameSettingsActivity.class)));
		packageButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PackageManagerActivity.class)));
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if(requestCode == 0){
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				File packageDir = new File(PACKAGES_DIR_PATH);
				if (!packageDir.exists()) {
					if (!packageDir.mkdir()) {
						finish();
					}
				}

				newGameButton = findViewById(R.id.main_menu_new_game);
				packageButton = findViewById(R.id.main_menu_manage_packages);

				newGameButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GameSettingsActivity.class)));
				packageButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PackageManagerActivity.class)));
			} else {
				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
						0);
			}

		}
	}

}
