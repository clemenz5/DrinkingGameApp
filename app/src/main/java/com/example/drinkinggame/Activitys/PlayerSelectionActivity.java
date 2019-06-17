package com.example.drinkinggame.Activitys;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drinkinggame.Models.ConditionType;
import com.example.drinkinggame.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerSelectionActivity extends AppCompatActivity {
	public static final String PLAYER_LIST = "player_list";
	public static final String END_CONDITION = "end_condition";
	public static final String END_CONDITION_AMOUNT = "end_condition_amount";
	private Button nextButton;
	private FloatingActionButton addPlayerButton;
	private ArrayList<String> playerNameList;
	private AlertDialog.Builder inputDialog;
	private RecyclerView playerRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_selection);
		nextButton = findViewById(R.id.player_selection_next);
		addPlayerButton = findViewById(R.id.player_selection_add_player);
		playerRecyclerView = findViewById(R.id.player_selection_recycler);

		PlayerAdapter playerAdapter = new PlayerAdapter(new LinkedList<>());
		playerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
		playerRecyclerView.setAdapter(playerAdapter);
		playerNameList = new ArrayList<>();

		inputDialog = new AlertDialog.Builder(this);
		inputDialog.setTitle("Player Name");
		final EditText input = new EditText(this);

		inputDialog.setPositiveButton("OK", (dialog, which) -> {
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

	class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
		private List<String> playerNameList;
		private List<String> selectedPackagesList;

		public PlayerAdapter(List<String> playerNameList) {
			this.playerNameList = playerNameList;
			selectedPackagesList = new LinkedList<>();
		}

		public List<String> getSelectedPackagesList() {
			return selectedPackagesList;
		}

		public void setPlayerNames(List<String> playerNames) {
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

			public void setChecked(boolean checked) {
				packageSelection.setChecked(checked);
			}

			public void enableCheckBox(boolean bool) {
				if (bool) {
					packageSelection.setVisibility(View.VISIBLE);
				} else {
					packageSelection.setVisibility(View.GONE);
				}
			}
		}


	}
}

