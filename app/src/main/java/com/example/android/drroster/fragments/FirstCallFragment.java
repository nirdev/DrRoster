package com.example.android.drroster.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.drroster.R;
import com.example.android.drroster.adapters.ItemAdapter;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

public class FirstCallFragment extends Fragment {

    public static ArrayList<Pair<Long, String>> mItemArray;
    public static ArrayList<Boolean> mCheckedArray;
    private DragListView mDragListView;

    View view;
    public FirstCallFragment() {
        // Required empty public constructor
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
                Toast.makeText(mDragListView.getContext(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                    Toast.makeText(mDragListView.getContext(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TODO: take from database
        mItemArray = new ArrayList<>();
        mCheckedArray = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mItemArray.add(new Pair<>(Long.valueOf(i), "Item " + i));
            mCheckedArray.add(false);
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
        ItemAdapter listAdapter = new ItemAdapter(mItemArray, R.layout.list_item_draggable, R.id.image, false);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);
        mDragListView.setCustomDragItem(new MyDragItem(getContext(), R.layout.list_item_draggable));
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