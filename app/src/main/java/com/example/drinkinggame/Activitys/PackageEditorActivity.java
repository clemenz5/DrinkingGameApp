package com.example.drinkinggame.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.example.drinkinggame.Models.Card;
import com.example.drinkinggame.Models.CardType;
import com.example.drinkinggame.Models.FileTransfer;
import com.example.drinkinggame.Models.Package;
import com.example.drinkinggame.R;

import java.util.LinkedList;
import java.util.List;

public class PackageEditorActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private Package currentPackage;
	private FloatingActionButton addCardButton;
	boolean change = false;
	PackageAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_editor);
		Log.d("package_name", getIntent().getExtras().getString(PackageManagerActivity.PACKAGE_NAME));
		recyclerView = findViewById(R.id.package_editor_card_list);
		addCardButton = findViewById(R.id.package_editor_add_card);
		addCardButton.setOnClickListener(view -> startActivityForResult(new Intent(PackageEditorActivity.this, NewCardActivity.class), NewCardActivity.REQUEST_CODE));
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
		currentPackage = FileTransfer.getPackage(getIntent().getExtras().getString(PackageManagerActivity.PACKAGE_NAME));
		List<String> cardStrings = new LinkedList<>();
		currentPackage.getCards().stream().forEach(card -> cardStrings.add(card.getMessage()));
		adapter = new PackageAdapter(cardStrings);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		FileTransfer.savePackage(currentPackage);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if(requestCode == NewCardActivity.REQUEST_CODE){
			change = true;
			currentPackage.addCard(new Card(data.getExtras().getString("message"), data.getExtras().getInt("sip"), CardType.valueOf(data.getExtras().getString("type"))));
			List<String> cardStrings = new LinkedList<>();
			currentPackage.getCards().stream().forEach(card -> cardStrings.add(card.getMessage()));
			adapter.setMessages(cardStrings);
			adapter.notifyDataSetChanged();
		}
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


		public void setMessages(List<String> packageNames) {
			this.packageNames = packageNames;
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