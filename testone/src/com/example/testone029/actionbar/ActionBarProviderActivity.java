package com.example.testone029.actionbar;

import com.example.testone001.loning.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class ActionBarProviderActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater flater = getMenuInflater();
		flater.inflate(R.menu.menu_actionbar_action_provider_activity, menu);
		
		
		
		//�Զ����һ����ťActionProvider
		MenuItem addItemCustom = menu.findItem(R.id.menu_actionbar_shareprovider_ziji);
//		ShareActionProvider providerCustom = (ShareActionProvider) addItemCustom.getActionProvider();
		
		
		//�ҵ����ǵ��Ǹ���ť
		MenuItem addItem = menu.findItem(R.id.menu_actionbar_shareprovider_add);
		ShareActionProvider provider = (ShareActionProvider) addItem.getActionProvider();
	
		Log.i("11","provider>>>"+menu);
		Log.i("11","provider>>>"+addItem);
		Log.i("11","provider>>>"+provider);
		
		provider.setShareIntent(getMenuItem());
		
		
		return super.onCreateOptionsMenu(menu);
	}
	public Intent getMenuItem(){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
		
	}
	
}
