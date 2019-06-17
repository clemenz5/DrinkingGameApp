package com.example.drinkinggame.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.drinkinggame.Models.*;

import com.example.drinkinggame.Models.Game;
import com.example.drinkinggame.Models.Package;
import com.example.drinkinggame.R;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
	private Button nextButton, prevButton;
	private TextView playerText, cardText, sipText, typeText;
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		List<Package> packageList = getPackages();
		List<Player> playerList = getPlayers();
		EndCondition endCondition = getEndCondition();

		game = new Game(packageList, playerList, endCondition);


		nextButton = findViewById(R.id.game_next_button);
		prevButton = findViewById(R.id.game_prev_button);
		playerText = findViewById(R.id.game_player);
		cardText = findViewById(R.id.game_question);
		sipText = findViewById(R.id.game_sips);
		typeText = findViewById(R.id.game_type);


		Card firstCard = game.nextCard();
		cardText.setText(firstCard.getMessage());
		sipText.setText(String.valueOf(firstCard.getSip()));
		typeText.setText(firstCard.getType().toString());
		playerText.setText(game.nextPlayer().getName());

		nextButton.setOnClickListener(this);
		prevButton.setOnClickListener(this);

	}

	private List<Package> getPackages(){
		List<Package> retrievedPackages = new LinkedList<>();
		for(String currentPackageName:getIntent().getExtras().getStringArrayList(GameSettingsActivity.PACKAGE_LIST)){
			retrievedPackages.add(FileTransfer.getPackage(currentPackageName));
		}
		return retrievedPackages;
	}

	private List<Player> getPlayers(){
		List<Player> retrievedPlayers = new LinkedList<>();
		for(String currentPlayerName:getIntent().getExtras().getStringArrayList(PlayerSelectionActivity.PLAYER_LIST)){
			retrievedPlayers.add(new Player(currentPlayerName));
		}
		return retrievedPlayers;
	}

	private EndCondition getEndCondition(){
		EndCondition retrievedCondition = new EndCondition(ConditionType.valueOf(getIntent().getExtras().getString(PlayerSelectionActivity.END_CONDITION)), getIntent().getExtras().getDouble(PlayerSelectionActivity.END_CONDITION_AMOUNT));
		return retrievedCondition;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
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

		}
	}
}
