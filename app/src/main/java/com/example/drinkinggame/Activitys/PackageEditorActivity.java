package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drinkinggame.Models.FileTransfer;
import com.example.drinkinggame.Models.Package;
import com.example.drinkinggame.R;

import java.util.LinkedList;
import java.util.List;

public class PackageEditorActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private Package currentPackage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_editor);
		Log.d("package_name", getIntent().getExtras().getString(PackageManagerActivity.PACKAGE_NAME));
		recyclerView = findViewById(R.id.package_editor_card_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
		currentPackage = FileTransfer.getPackage(getIntent().getExtras().getString(PackageManagerActivity.PACKAGE_NAME));
		List<String> cardStrings = new LinkedList<>();
		currentPackage.getCards().stream().forEach(card -> cardStrings.add(card.getMessage()));
		recyclerView.setAdapter(new PackageAdapter(cardStrings));
	}



	class PackageAdapter extends RecyclerView.Adapter<PackageEditorActivity.ViewHolder> {
		private List<String> packageNames;

		public PackageAdapter(List<String> packageNames) {
			this.packageNames = packageNames;
		}

		@NonNull
		@Override
		public PackageEditorActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.package_list_item, viewGroup, false);

			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(@NonNull PackageEditorActivity.ViewHolder viewHolder, int i) {
			viewHolder.setName(packageNames.get(i));
		}

		@Override
		public int getItemCount() {
			return packageNames.size();
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		private TextView nameView;
		private CheckBox packageSelection;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			nameView = itemView.findViewById(R.id.package_list_name);
			packageSelection = itemView.findViewById(R.id.package_list_check_box);

			nameView.setOnClickListener(v -> {

			});
		}

		public void setName(String name){
			nameView.setText(name);
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