package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.drinkinggame.Models.ConditionType;
import com.example.drinkinggame.Models.EndCondition;
import com.example.drinkinggame.Models.Player;
import com.example.drinkinggame.R;

import java.util.ArrayList;

public class PlayerSelectionActivity extends AppCompatActivity {
	public static final String PLAYER_LIST = "player_list";
	public static final String END_CONDITION = "end_condition";
	public static final String END_CONDITION_AMOUNT = "end_condition_amount";
	private Button nextButton;
	private ArrayList<String> playerNameList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_selection);
		nextButton = findViewById(R.id.player_selection_next);
		playerNameList = new ArrayList<>();
		playerNameList.add("rainer");
		playerNameList.add("kron");
		playerNameList.add("tuuuun");
		playerNameList.add("luis");

		nextButton.setOnClickListener(v -> {
			Intent intent = new Intent(PlayerSelectionActivity.this, GameActivity.class);
			Bundle bundle = new Bundle();
			bundle.putStringArrayList(GameSettingsActivity.PACKAGE_LIST, getIntent().getExtras().getStringArrayList(GameSettingsActivity.PACKAGE_LIST));
			bundle.putStringArrayList(PLAYER_LIST, playerNameList);
			bundle.putString(END_CONDITION, ConditionType.CARDS.toString());
			bundle.putDouble(END_CONDITION_AMOUNT, 16);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		});
	}
}
