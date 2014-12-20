package com.ucab.ecotoolbox.ecotoolbox;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Principal extends Activity {

	Button boton1;
    String in = "Internacional";
    ListView list;
    CustomAdapter adapter;
    public  Principal CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();

    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		initilize();
	}

	private void initilize() {
//		boton1 = (Button) findViewById(R.id.button_1);
//		boton1.setOnClickListener(this);

        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        Resources res =getResources();
        list= ( ListView )findViewById( R.id.list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapter( CustomListView, CustomListViewValuesArr,res );
        list.setAdapter( adapter );

    }

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.button_1:
//			startActivity(new Intent(this, Activity_Noticias.class));
//			break;
//		}
//	}

    public void setListData()
    {

        for (int i = 0; i < 2; i++) {

            final ListModel sched = new ListModel();

            switch (i) {
                case 0:
                    sched.setCompanyName("Nacional");
                    sched.setImage("rss");
                    CustomListViewValuesArr.add( sched );
                    break;

                case 1:
                    sched.setCompanyName(in);
                    sched.setImage("cal");
                    CustomListViewValuesArr.add( sched );
                    break;
                default:
                    break;
            }

        }

    }


    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);

        if(tempValues.getCompanyName()=="Internacional"){
            Intent i = new Intent(this, Activity_Noticias.class );
            i.putExtra("in", in);
            startActivity(i);

        }
        else{
            Intent i = new Intent(this, Activity_Noticias.class );
            i.putExtra("in", "Nacional");
            startActivity(i);
        }

    }
}
