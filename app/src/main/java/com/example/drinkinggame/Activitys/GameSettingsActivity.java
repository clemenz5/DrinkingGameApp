package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkinggame.Models.FileTransfer;
import com.example.drinkinggame.Models.Game;
import com.example.drinkinggame.R;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.drinkinggame.Activitys.PackageManagerActivity.PACKAGE_NAME;

public class GameSettingsActivity extends AppCompatActivity {
	public static final String PACKAGE_LIST = "package_list";
	private Button next;
	private RecyclerView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_settings);

		next = findViewById(R.id.settings_next);
		listView = findViewById(R.id.game_settings_packages_list);

		listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		PackageAdapter packageAdapter = new PackageAdapter(FileTransfer.getAllPackageNames());
		listView.setAdapter(packageAdapter);
		next.setOnClickListener(v -> {
			if(packageAdapter.getSelectedPackagesList().isEmpty()){
				Toast.makeText(this, "wähle mindestens ein Paket aus", Toast.LENGTH_LONG).show();
				return;
			}
			Intent intent = new Intent(GameSettingsActivity.this, PlayerSelectionActivity.class);
			Bundle bundle = new Bundle();
			bundle.putStringArrayList(PACKAGE_LIST, new ArrayList<>(packageAdapter.getSelectedPackagesList()));

			intent.putExtras(bundle);
			startActivity(intent);
		});
	}


	class PackageAdapter extends RecyclerView.Adapter<GameSettingsActivity.PackageAdapter.ViewHolder> {
		private List<String> packageNames;
		private List<String> selectedPackagesList;

		public PackageAdapter(List<String> packageNames) {
			this.packageNames = packageNames;
			selectedPackagesList = new LinkedList<>();
		}

		public List<String> getSelectedPackagesList() {
			return selectedPackagesList;
		}

		public void setPackageNames(List<String> packageNames) {
			this.packageNames = packageNames;
			selectedPackagesList.clear();
		}

		@NonNull
		@Override
		public PackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.package_list_item, viewGroup, false);

			return new PackageAdapter.ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(@NonNull PackageAdapter.ViewHolder viewHolder, int i) {
			viewHolder.setName(packageNames.get(i));
			viewHolder.setChecked(false);
		}

		@Override
		public int getItemCount() {
			return packageNames.size();
		}

		class ViewHolder extends RecyclerView.ViewHolder {
			private TextView nameView;
			private CheckBox packageSelection;

			public ViewHolder(@NonNull View itemView) {
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
					Bundle bundle = new Bundle();
					bundle.putString(PACKAGE_NAME, nameView.getText().toString());
					Intent intent = new Intent(GameSettingsActivity.this, PackageEditorActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
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
