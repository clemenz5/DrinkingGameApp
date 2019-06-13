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
	private TextView playerText, cardText;
	private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		List<Package> packageList = new LinkedList<>();
		Package testPackage = new Package();
		testPackage.addCard(new Card("penis", 1, CardType.QUESTION));
		testPackage.addCard(new Card("Ich bin noch nie nackt durch den Wald gerannt.", 2, CardType.QUESTION));
		testPackage.addCard(new Card("dreier", 3, CardType.QUESTION));
		testPackage.addCard(new Card("Ich wurde noch nie von Bullen durchsucht", 4, CardType.QUESTION));
		testPackage.addCard(new Card("Ich hab noch nie jemand vom anderen Geschlecht geküsst", 5, CardType.QUESTION));
		testPackage.addCard(new Card("sex", 6, CardType.QUESTION));
		testPackage.addCard(new Card("69", 7, CardType.QUESTION));
		testPackage.addCard(new Card("Spring vom höchsten Gegenstand in deiner Nähe", 7, CardType.TASK));

		packageList.add(testPackage);

		List<Player> playerList = new LinkedList<>();
		playerList.add(new Player("rainer"));
		playerList.add(new Player("tuunn"));
		playerList.add(new Player("jj"));
		playerList.add(new Player("kron"));
		EndCondition endCondition = new EndCondition(ConditionType.CARDS, 20);
		game = new Game(packageList, playerList, endCondition);


		nextButton = findViewById(R.id.game_next_button);
		prevButton = findViewById(R.id.game_prev_button);
		playerText = findViewById(R.id.game_player);
		cardText = findViewById(R.id.game_question);


		cardText.setText(game.nextCard().getMessage());
		playerText.setText(game.nextPlayer().getName());

		nextButton.setOnClickListener(this);
		prevButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.game_next_button:
				cardText.setText(game.nextCard().getMessage());
				playerText.setText(game.nextPlayer().getName());
				break;
			case R.id.game_prev_button:
				cardText.setText(game.prevCard().getMessage());
				playerText.setText(game.prevPlayer().getName());
				break;

		}
	}
}
