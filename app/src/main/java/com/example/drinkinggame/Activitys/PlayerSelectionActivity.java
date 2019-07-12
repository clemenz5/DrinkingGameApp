package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkinggame.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerSelectionActivity extends AppCompatActivity {
	public static final String PLAYER_LIST = "player_list";
	private ArrayList<String> playerNameList;
	private AlertDialog.Builder inputDialog;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_selection);
		final Button nextButton = findViewById(R.id.player_selection_next);
		final Button addPlayerButton = findViewById(R.id.player_selection_add_player);
		final RecyclerView playerRecyclerView = findViewById(R.id.player_selection_recycler);
		sharedPreferences = getApplicationContext().getSharedPreferences("PlayerPreferences", 0);

		PlayerAdapter playerAdapter = new PlayerAdapter(new LinkedList<>());
		playerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
		playerRecyclerView.setAdapter(playerAdapter);
		playerNameList = new ArrayList<>();

		inputDialog = new AlertDialog.Builder(this);
		inputDialog.setTitle("Player Name");
		List<String> retrievedPlayerNames = new LinkedList<>();
		int i = 0;
		while(i!=-1){
			String playerName = sharedPreferences.getString(String.valueOf(i), null);
			if(playerName != null){
				retrievedPlayerNames.add(playerName);
				i++;
			}else{
				i=-1;
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item, retrievedPlayerNames);
		final AutoCompleteTextView input = new AutoCompleteTextView(this);
		input.setAdapter(adapter);
		input.setThreshold(1);

		inputDialog.setPositiveButton("OK", (dialog, which) -> {
			if(input.getText().toString().trim().isEmpty()){
				Toast.makeText(this, "kein valider Spielername", Toast.LENGTH_LONG).show();
				return;
			}
			playerNameList.add(input.getText().toString().trim());
			playerAdapter.setPlayerNames(playerNameList);
			playerAdapter.notifyDataSetChanged();
		});
		inputDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

		addPlayerButton.setOnClickListener(v -> {
			if (input.getParent() != null) {
				((ViewGroup) input.getParent()).removeView(input);
				input.setText("");
			}
			inputDialog.setView(input);
			inputDialog.show();
		});
		nextButton.setOnClickListener(v -> {
			if(playerNameList.isEmpty()) {
				Toast.makeText(this, "füge mindestens einen Spieler hinzu", Toast.LENGTH_LONG).show();
				return;
			}
			SharedPreferences.Editor editor = sharedPreferences.edit();
			List<String> mergedList = mergeLists(retrievedPlayerNames, playerNameList);
			for(int r = 0; r<mergedList.size(); r++){
				editor.putString(String.valueOf(r), mergedList.get(r));
			}
			editor.apply();


			Intent intent = new Intent(PlayerSelectionActivity.this, GameActivity.class);
			Bundle bundle = new Bundle();
			bundle.putStringArrayList(GameSettingsActivity.PACKAGE_LIST, getIntent().getExtras().getStringArrayList(GameSettingsActivity.PACKAGE_LIST));
			bundle.putStringArrayList(PLAYER_LIST, playerNameList);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		});
	}

	/**
	 * this method is designed to merge 2 lists and eliminate doubles
	 * there will be doubles if one of the lists comes with doubles
	 * @param list1 the first List
	 * @param list2 the second List
	 * @return the merged List
	 */
	private List<String> mergeLists(List<String> list1, List<String> list2){
		List<String> mergedList = new LinkedList<>();
		for(String s:list1){
			if(!list2.contains(s)){
				mergedList.add(s);
			}
		}
		mergedList.addAll(list2);
		return mergedList;
	}

	class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
		private List<String> playerNameList;
		private List<String> selectedPackagesList;

		PlayerAdapter(List<String> playerNameList) {
			this.playerNameList = playerNameList;
			selectedPackagesList = new LinkedList<>();
		}

		//public List<String> getSelectedPackagesList() { return selectedPackagesList;		}

		void setPlayerNames(List<String> playerNames) {
			this.playerNameList = playerNames;
			selectedPackagesList.clear();
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.package_list_item, viewGroup, false);

			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
			viewHolder.setName(playerNameList.get(i));
			viewHolder.enableCheckBox(false);
			viewHolder.setChecked(false);
		}

		@Override
		public int getItemCount() {
			return playerNameList.size();
		}

		class ViewHolder extends RecyclerView.ViewHolder {
			private TextView nameView;
			private CheckBox packageSelection;

			ViewHolder(@NonNull View itemView) {
				super(itemView);
				nameView = itemView.findViewById(R.id.package_list_name);
				packageSelection = itemView.findViewById(R.id.package_list_check_box);

				packageSelection.setOnCheckedChangeListener((buttonView, isChecked) -> {
					if (isChecked) {
						selectedPackagesList.add(nameView.getText().toString());
					} else {
						selectedPackagesList.remove(nameView.getText().toString());
					}
				});

				nameView.setOnClickListener(v -> {

				});
			}

			public void setName(String name) {
				nameView.setText(name);
			}

			void setChecked(boolean checked) {
				packageSelection.setChecked(checked);
			}

			void enableCheckBox(boolean bool) {
				if (bool) {
					packageSelection.setVisibility(View.VISIBLE);
				} else {
					packageSelection.setVisibility(View.GONE);
				}
			}
		}
	}
}