package com.example.drinkinggame.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkinggame.Models.*;

import com.example.drinkinggame.Models.Game;
import com.example.drinkinggame.Models.Package;
import com.example.drinkinggame.R;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
	private Button nextButton, prevButton;
	private TextView playerText, cardText, sipText, typeText, sipInfo;
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		List<Package> packageList = getPackages();
		List<Player> playerList = getPlayers();

		game = new Game(packageList, playerList);


		nextButton = findViewById(R.id.game_next_button);
		prevButton = findViewById(R.id.game_prev_button);
		playerText = findViewById(R.id.game_player);
		cardText = findViewById(R.id.game_question);
		sipText = findViewById(R.id.game_sips);
		typeText = findViewById(R.id.game_type);
		sipInfo = findViewById(R.id.game_sips_info);


		typeText.setOnClickListener(this);
		sipInfo.setOnClickListener(this);
		sipText.setOnClickListener(this);
		Card firstCard = game.nextCard();
		cardText.setText(firstCard.getMessage());
		sipText.setText(String.valueOf(firstCard.getSip()));
		typeText.setText(firstCard.getType().toString());
		playerText.setText(game.nextPlayer().getName());

		nextButton.setOnClickListener(this);
		prevButton.setOnClickListener(this);

	}

	private List<Package> getPackages() {
		List<Package> retrievedPackages = new LinkedList<>();
		for (String currentPackageName : getIntent().getExtras().getStringArrayList(GameSettingsActivity.PACKAGE_LIST)) {
			retrievedPackages.add(FileTransfer.getPackage(currentPackageName));
		}
		return retrievedPackages;
	}

	private List<Player> getPlayers() {
		List<Player> retrievedPlayers = new LinkedList<>();
		for (String currentPlayerName : getIntent().getExtras().getStringArrayList(PlayerSelectionActivity.PLAYER_LIST)) {
			retrievedPlayers.add(new Player(currentPlayerName));
		}
		return retrievedPlayers;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.game_next_button:
				Card nextCard = game.nextCard();
				cardText.setText(nextCard.getMessage());
				sipText.setText(String.valueOf(nextCard.getSip()));
				typeText.setText(nextCard.getType().toString());
				playerText.setText(game.nextPlayer().getName());
				break;
			case R.id.game_prev_button:
				Card prevCard = game.prevCard();
				cardText.setText(prevCard.getMessage());
				sipText.setText(String.valueOf(prevCard.getSip()));
				typeText.setText(prevCard.getType().toString());
				playerText.setText(game.prevPlayer().getName());
				break;
			case R.id.game_sips:
			case R.id.game_sips_info:
				Toast.makeText(this, "so viel wird getrunken!", Toast.LENGTH_LONG).show();
				break;
			case R.id.game_type:
				switch (typeText.getText().toString()) {
					case "QUESTION":
						Toast.makeText(this, "Wenn du die Frage beantwortest, darfst du verteilen, sonst selbst trinken", Toast.LENGTH_LONG).show();
						break;
					case "STATEMENT":
						Toast.makeText(this, "Die anderen müssen raten, ob dieses Statement auf die zutrifft. Wer richtig liegt darf verteilen, der Rest trinkt selbst", Toast.LENGTH_LONG).show();
						break;
					case "TASK":
						Toast.makeText(this, "Wenn du die Aufgabe erfüllst, darfst du verteilen, sonst selbst trinken", Toast.LENGTH_LONG).show();
						break;
				}
				break;
		}
	}
}
