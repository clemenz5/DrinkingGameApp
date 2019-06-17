package com.example.drinkinggame.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkinggame.Models.Card;
import com.example.drinkinggame.Models.FileTransfer;
import com.example.drinkinggame.Models.Package;
import com.example.drinkinggame.R;

import java.util.LinkedList;
import java.util.List;

public class PackageManagerActivity extends AppCompatActivity {
	private RecyclerView packageListAdapter;
	public static final String PACKAGE_NAME = "package_name";
	private Button mergeButton, deleteButton;
	AlertDialog.Builder inputDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_manager);

		Button fab = findViewById(R.id.package_manager_add_package);
		packageListAdapter = findViewById(R.id.package_manager_package_list);
		mergeButton = findViewById(R.id.package_manager_merge);
		deleteButton = findViewById(R.id.package_manager_delete);

		PackageAdapter packageAdapter = new PackageAdapter(FileTransfer.getAllPackageNames());
		packageListAdapter.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
		packageListAdapter.setAdapter(packageAdapter);

		inputDialog = new AlertDialog.Builder(this);
		inputDialog.setTitle("Paket Name");
		final EditText input = new EditText(this);

		inputDialog.setPositiveButton("OK", (dialog, which) -> {
			FileTransfer.savePackage(new Package(input.getText().toString().trim(), new LinkedList<>()));
			packageAdapter.setPackageNames(FileTransfer.getAllPackageNames());
			packageAdapter.notifyDataSetChanged();
		});
		inputDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

		fab.setOnClickListener(v -> {
			if (input.getParent() != null) {
				((ViewGroup) input.getParent()).removeView(input);
				input.setText("");
			}
			inputDialog.setView(input);
			inputDialog.show();
		});

		mergeButton.setOnClickListener(v -> {
			if (!packageAdapter.getSelectedPackagesList().isEmpty()) {
				Package mergedPackage = new Package();
				mergedPackage.setName("mergedPackage" + System.currentTimeMillis());
				List<Package> mergePackageList = new LinkedList<>();
				for (String packageName : packageAdapter.selectedPackagesList) {
					mergePackageList.add(FileTransfer.getPackage(packageName));
				}
				for (Package memberPackage : mergePackageList) {
					for (Card card : memberPackage.getCards()) {
						mergedPackage.addCard(card);
					}
				}
				FileTransfer.savePackage(mergedPackage);
				packageAdapter.setPackageNames(FileTransfer.getAllPackageNames());
				packageAdapter.notifyDataSetChanged();
			}

		});

		deleteButton.setOnClickListener(v -> {
			for (String packageName : packageAdapter.getSelectedPackagesList()) {
				FileTransfer.deletePackage(packageName);
			}
			packageAdapter.setPackageNames(FileTransfer.getAllPackageNames());
			packageAdapter.notifyDataSetChanged();
		});


	}

	class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
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
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.package_list_item, viewGroup, false);

			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
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
					Intent intent = new Intent(PackageManagerActivity.this, PackageEditorActivity.class);
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
