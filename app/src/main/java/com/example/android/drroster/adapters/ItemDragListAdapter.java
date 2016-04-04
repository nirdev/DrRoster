package com.example.android.drroster.adapters;

import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.drroster.R;
import com.example.android.drroster.fragments.DraggableListFragment;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

/**
 * Created by Nir on 4/3/2016.bugalbugala
 */
public class ItemDragListAdapter extends DragItemAdapter<Pair<Long, String>, ItemDragListAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    boolean mPickDateOption = false;

    //Constructor
    public ItemDragListAdapter(ArrayList<Pair<Long, String>> list, int layoutId,
                               int grabHandleId, boolean dragOnLongPress,boolean pickDateOption) {
        super(dragOnLongPress);
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mPickDateOption = pickDateOption;
        setHasStableIds(true);
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String text = mItemList.get(position).second;
        holder.mEditText.setText(text);
        holder.itemView.setTag(text);
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).first;
    }

    public class ViewHolder extends DragItemAdapter<Pair<Long, String>, ItemDragListAdapter.ViewHolder>.ViewHolder {
        public ImageButton mDeleteImageButton;

        public CheckBox mCheckBox;

        public EditText mEditText;
        public String mText;
        public String mOldText;

        public ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId);

            mEditText = (EditText) itemView.findViewById(R.id.text);
            mDeleteImageButton = (ImageButton) itemView.findViewById(R.id.delete_draggable_item);
            mText = mEditText.getText().toString();
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_draggable_list_item);

            //Delete Button was clicked listener
            mDeleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Check which index was selected by comparing to edit text
                    int i = 0;
                    for (Pair temp : DraggableListFragment.mItemArray) {
                        if (temp.second.equals("" + mOldText)) {
                            break;
                        }
                        i++;
                    }
                    DraggableListFragment.mItemArray.remove(i);
                    notifyDataSetChanged();
                }
            });

            //Change Name Listener
            mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    //User clicked on edit text
                    if (hasFocus) {
                        //Get text before edited
                        mOldText = mEditText.getText().toString();

                        //Set Cancel item visible
                        mDeleteImageButton.setVisibility(View.VISIBLE);
                    }
                    //User leaved edit text
                    else {
                        //Set new text
                        mText = mEditText.getText().toString();

                        //Set delete button invisible
                        mDeleteImageButton.setVisibility(View.GONE);

                        //Set new value in the array
                        int i = 0;
                        Long mTempLong = -1l;
                        //Check which index was selected by comparing to old edit text
                        for (Pair temp : DraggableListFragment.mItemArray) {
                            if (temp.second.equals("" + mOldText)) {
                                mTempLong = (long) temp.first;
                                break;
                            }
                            i++;
                        }

                        //If long id was found set the data in the array.
                        if (mTempLong != -1) {
                            DraggableListFragment.mItemArray.set(i, Pair.create(mTempLong, mText));
                        }
                    }
                }
            });

            //CheckBox change listener
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    //Check if no new name was signed to this person
                    if (mOldText == null || mOldText.isEmpty()) {

                        //Check name of currently checked box
                        EditText mCurrentName = (EditText) itemView.findViewById(R.id.text);
                        //set check condition in is checked array
                        int i = 0;
                        //Check which index was selected by comparing to edit text
                        for (Pair temp : DraggableListFragment.mItemArray) {
                            if (temp.second.equals("" + mCurrentName.getText())) {
                                break;
                            }
                            i++;
                        }
                        DraggableListFragment.mCheckedArray.set(i, isChecked);
                    }
                    //if name is currently being added check the array by the old name
                    else {
                        //set check condition in is checked array
                        int i = 0;
                        //Check which index was selected by comparing to edit text
                        for (Pair temp : DraggableListFragment.mItemArray) {
                            if (temp.second.equals("" + mOldText)) {
                                break;
                            }
                            i++;
                        }
                        //Sets the currently being added checkbox in final array
                        DraggableListFragment.mCheckedArray.set(i, isChecked);
                    }

                    //if date option available
                    if (mPickDateOption){
                        Log.wtf("here", "--------------------------------------------");
                    }
                }
            });
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}