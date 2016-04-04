package com.example.android.drroster.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.util.Pair;
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
import com.squareup.timessquare.CalendarPickerView;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nir on 4/3/2016.bugalbugala
 */
public class ItemDragListAdapter extends DragItemAdapter<Pair<Long, String>, ItemDragListAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;

    boolean mPickDateOption = false;

    private CalendarPickerView mCalendarPickerView;
    private AlertDialog theDialog;
    Context mContext;
    final Calendar nextMonth;
    final Calendar lastMonth;

    //Constructor
    public ItemDragListAdapter(ArrayList<Pair<Long, String>> list, int layoutId,
                               int grabHandleId, boolean dragOnLongPress,boolean pickDateOption,Context context) {
        super(dragOnLongPress);
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mPickDateOption = pickDateOption;
        mContext = context;
        setHasStableIds(true);
        setItemList(list);

        nextMonth = Calendar.getInstance();
        nextMonth.add(Calendar.MONTH, 1);
        lastMonth = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);
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

                    //if date option available && item is marked as checked
                    if (mPickDateOption && isChecked) {

                        showCalendarInDialog(mContext.getString(R.string.dialog_calerdar_title), R.layout.dialog_date_picker);
                        mCalendarPickerView.init(lastMonth.getTime(), nextMonth.getTime())
                                .inMode(CalendarPickerView.SelectionMode.RANGE)
                                .withSelectedDate(new Date());

                    }

                }
            });
        }

        private void showCalendarInDialog(String title, int layoutResId) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            mCalendarPickerView = (CalendarPickerView) inflater.inflate(layoutResId, null, false);

            theDialog = new AlertDialog.Builder(mContext) //
                    .setTitle(title)
                    .setView(mCalendarPickerView)
                    .setNeutralButton(R.string.dialog_choose_date_button, new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
            theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    mCalendarPickerView.fixDialogDimens();
                }
            });


//            // Setting dialog view at the bottom of the window
//            Window window = theDialog.getWindow();
//            window.setGravity(Gravity.BOTTOM);
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            theDialog.show();
        }

        //List item is clicked listener
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