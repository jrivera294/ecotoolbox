package com.ucab.ecotoolbox.ecotoolbox;


import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Principal extends ExpandableListActivity {

    private int ParentClickStatus=-1;
    private int ChildClickStatus=-1;
    private ArrayList<ParentItem> parents;

    @Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		initilize();
	}

	private void initilize() {

        Resources res = this.getResources();
        Drawable devider = res.getDrawable(R.drawable.line);

        getExpandableListView().setGroupIndicator(null);
        getExpandableListView().setDivider(devider);
        getExpandableListView().setChildDivider(devider);
        getExpandableListView().setDividerHeight(1);
        registerForContextMenu(getExpandableListView());

        final ArrayList<ParentItem> dummyList = buildDummyData();


        loadHosts(dummyList);
    }

    private ArrayList<ParentItem> buildDummyData()
    {
        final ArrayList<ParentItem> list = new ArrayList<ParentItem>();
        for (int i = 1; i < 3; i++)
        {

            final ParentItem parent = new ParentItem();

            if(i==1){
                parent.setName("" + i);
                parent.setText1("Nacional");
                parent.setText2("RSS Feed de noticias nacionales");
                parent.setChildren(new ArrayList<ChildItem>());

                final ChildItem child = new ChildItem();
                child.setName("" + i);
                child.setText1("Venezuela Verde");


                parent.getChildren().add(child);
            }
            else if(i==2){
                parent.setName("" + i);
                parent.setText1("Internacional");
                parent.setText2("RSS Feed de noticias internacionales");
                parent.setChildren(new ArrayList<ChildItem>());

                final ChildItem child = new ChildItem();
                child.setName("" + i);
                child.setText1("Mariano Bueno");
                parent.getChildren().add(child);
                final ChildItem child1 = new ChildItem();
                child1.setName("" + i);
                child1.setText1("Boletin Ecologico");
                parent.getChildren().add(child1);
                final ChildItem child2 = new ChildItem();
                child2.setName("" + i);
                child2.setText1("Noticias Mas Verde Digital");
                parent.getChildren().add(child2);
                final ChildItem child3 = new ChildItem();
                child3.setName("" + i);
                child3.setText1("El Blog Verde");
                parent.getChildren().add(child3);
            }

            list.add(parent);
        }
        return list;
    }


    private void loadHosts(final ArrayList<ParentItem> newParents)
    {
        if (newParents == null)
            return;

        parents = newParents;


        if (this.getExpandableListAdapter() == null){
            final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter();
            this.setListAdapter(mAdapter);
        }
        else {

            ((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
        }
    }

    private class MyExpandableListAdapter extends BaseExpandableListAdapter{
        private LayoutInflater inflater;

        public MyExpandableListAdapter()
        {
            inflater = LayoutInflater.from(Principal.this);
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parentView)
        {
            final ParentItem parent = parents.get(groupPosition);


            convertView = inflater.inflate(R.layout.grouprow, parentView, false);

            ((TextView) convertView.findViewById(R.id.text1)).setText(parent.getText1());
            ((TextView) convertView.findViewById(R.id.text)).setText(parent.getText2());
            ImageView image=(ImageView)convertView.findViewById(R.id.image);

            image.setImageResource(
                    getResources().getIdentifier(
                            "com.ucab.ecotoolbox.ecotoolbox:drawable/n"+parent.getName(),null,null));

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parentView)
        {
            final ParentItem parent = parents.get(groupPosition);
            final ChildItem child = parent.getChildren().get(childPosition);

            convertView = inflater.inflate(R.layout.childrow, parentView, false);

            ((TextView) convertView.findViewById(R.id.text1)).setText(child.getText1());
            ImageView image=(ImageView)convertView.findViewById(R.id.image);
            image.setImageResource(
                    getResources().getIdentifier(
                            "com.ucab.ecotoolbox.ecotoolbox:drawable/punto",null,null));

            return convertView;
        }


        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
            return parents.get(groupPosition).getChildren().get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {

            //Log.i("Noise", "parent == "+groupPosition+"=  child : =="+childPosition);
            if( ChildClickStatus!=childPosition)
            {
                ChildClickStatus = childPosition;
                Intent i = new Intent(getBaseContext(), Activity_Noticias.class );
                switch (groupPosition){
                    case 0:
//                        if (childPosition==0){
                            i.putExtra("in", "Nacional");
                            startActivity(i);
//                        }
                        break;
                    case 1:
                        switch (childPosition){
                            case 0:
                                i.putExtra("in", "i0");
                                startActivity(i);
                                break;
                            case 1:
                                i.putExtra("in", "i1");
                                startActivity(i);
                                break;
                            case 2:
                                i.putExtra("in", "i2");
                                startActivity(i);
                                break;
                            case 3:
                                i.putExtra("in", "i3");
                                startActivity(i);
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }

            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            int size=0;
            if(parents.get(groupPosition).getChildren()!=null)
                size = parents.get(groupPosition).getChildren().size();
            return size;
        }


        @Override
        public Object getGroup(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroup ");

            return parents.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return parents.size();
        }

        @Override
        public long getGroupId(int groupPosition)
        {
//            Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);
            ParentClickStatus=groupPosition;
            if(ParentClickStatus==0)
                ParentClickStatus=-1;

            return groupPosition;
        }


        @Override
        public void notifyDataSetChanged()
        {
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty()
        {
            return ((parents == null) || parents.isEmpty());
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

    }
}
