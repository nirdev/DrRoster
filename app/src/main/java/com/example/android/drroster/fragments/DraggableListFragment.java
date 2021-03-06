package com.example.android.drroster.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.adapters.ItemDragListAdapter;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DraggableListFragment extends Fragment {

    //Array for people names with id's for the draggable list
    public static ArrayList<Pair<Long, String>> mPeopleArray;

    //Array for checkbox
    public static ArrayList<Boolean> mCheckedArray;

    //Array for "Leave Dates" array - each worker (first array) have it's own array of dates available
    public static ArrayList<List<Date>> mLeaveDatesArray;

    private DragListView mDragListView;
    private boolean pickDateOption;

    View view;
    public DraggableListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.wtf("here", "tag: " + getTag());
        //Sets default prefs based on the fragment tag from GenerateRosterActivity
        switch (getTag()){
            case (GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX + ""):
                pickDateOption = true;
                break;
            default:
                pickDateOption = false;
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_first_call, container, false);



        mDragListView = (DragListView) view.findViewById(R.id.drag_list_view_first_call);
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);

        //Todo:Delete when finish list
        mDragListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
            }
            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                }
            }
        });

        //TODO: take from database
        mPeopleArray = new ArrayList<>();
        mCheckedArray = new ArrayList<>();
        mLeaveDatesArray  =new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mPeopleArray.add(new Pair<>(Long.valueOf(i), "Item " + i));
            mCheckedArray.add(false);
            mLeaveDatesArray.add(null);
        }
        setupListRecyclerView();

        return view;
    }

    @Override
    public void onDestroyView() {
        //TODo: handel DB here with gettag() - check this is best practice place to handle db in fragment life cycle
        super.onDestroyView();
    }

    public void setupListRecyclerView() {
        mDragListView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemDragListAdapter listAdapter = new ItemDragListAdapter(mPeopleArray,
                R.layout.item_list_draggable, R.id.image, false,pickDateOption,getContext());
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);
        mDragListView.setCustomDragItem(new MyDragItem(getContext(), R.layout.item_list_draggable));
    }



    private static class MyDragItem extends DragItem {

        public MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindDragView(View clickedView, View dragView) {
            CharSequence text = ((TextView) clickedView.findViewById(R.id.text)).getText();
            ((TextView) dragView.findViewById(R.id.text)).setText(text);
            dragView.setBackgroundColor(dragView.getResources().getColor(R.color.colorTransparent));
        }
    }


}