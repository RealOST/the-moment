package com.example.appdevelopmenttwo.adaptor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdevelopmenttwo.NoteDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.bean.Note;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;


import org.apache.commons.httpclient.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {
    private List<Note> mNotes;
    private LayoutInflater mLayoutInflater;
    private FragmentActivity mContext;
    private NoteDbOpenHelper mNoteDbOpenHelper;

    public RVAdapter(Context mContext,List<Note> mNotes) {
        this.mNotes = mNotes;
        this.mContext =(FragmentActivity) mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        mNoteDbOpenHelper = new NoteDbOpenHelper(mContext);
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_layout,parent,false);
        RVViewHolder rvViewHolder = new RVViewHolder(view);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Note note = mNotes.get(position);
        holder.mItemTitle.setText(note.getTitle());
        holder.mItemDeadLine.setText(note.getDeadLine());
        holder.mItemTime.setText(note.getCountDown());
        holder.mItemTitle.requestFocus();
        holder.mItemDeadLine.requestFocus();
        holder.mItemTime.requestFocus();
        holder.rvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, EditActivity.class);
//                intent.putExtra("note", note);
//                mContext.startActivity(intent);
            }
        });

        holder.rvContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View v = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = v.findViewById(R.id.tv_delete);
                TextView tvEdit =v.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int row = mNoteDbOpenHelper.deleteFromDbById(note.getId());
                        if (row > 0) {
                            removeData(position);
                        }
                        dialog.dismiss();
                    }
                });

                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(mContext, EditActivity.class);
//                        intent.putExtra("note", note);
//                        mContext.startActivity(intent);
                        dialog.dismiss();
                        AlertDialog dialog = new MaterialAlertDialogBuilder(view.getContext())
                                .setTitle("编辑")
                                .setIcon(R.drawable.ic_baseline_construction_24)
                                .setView(R.layout.edit_countdown_text)
                                .setPositiveButton(
                                        "确认",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                TextInputEditText etTitle = ((AlertDialog) dialog).findViewById(R.id.et_title);
                                                TextInputEditText etDeadLine = ((AlertDialog) dialog).findViewById(R.id.et_deadLine);
                                                etDeadLine.setOnClickListener(view -> {
                                                    MaterialDatePicker<Long> datePicker =MaterialDatePicker.Builder.datePicker()
                                                            .setTitleText("请选择日期")
                                                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                                            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                                                            .build();
                                                    datePicker.show(mContext.getSupportFragmentManager(),datePicker.toString());

                                                    datePicker.addOnPositiveButtonClickListener(selection -> {
                                                        Calendar calendar = Calendar.getInstance(Locale.CHINA);
                                                        calendar.setTimeInMillis(selection);
                                                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                                                        String formattedDate  = format.format(calendar.getTime());
                                                        etDeadLine.setText(formattedDate);
                                                    });
                                                });

                                                //TODO 在确认按钮之外设置内容
//                                                etTitle.setText(note.getTitle());
//                                                etDeadLine.setText(note.getDeadLine());
                                                String title = etTitle.getText().toString().trim();
                                                String deadLine = etDeadLine.getText().toString().trim();
                                                if (TextUtils.isEmpty(title)) {
                                                    ToastUtil.toastShort(view.getContext(), "目标不能为空！");
                                                    return;
                                                }
                                                if (TextUtils.isEmpty(deadLine)) {
                                                    ToastUtil.toastShort(view.getContext(), "期限不能为空！");
                                                    return;
                                                }
                                                Note temp = new Note();

                                                temp.setId(note.getId());
                                                temp.setTitle(title);
                                                temp.setDeadLine(deadLine);
                                                temp.setCreatedTime(getCurrentTimeFormat());
                                                temp.setCountDown(deadLine);
                                                long row = mNoteDbOpenHelper.updateData(temp);
                                                if (row != -1) {
                                                    ToastUtil.toastShort(view.getContext(),"修改成功！");
                                                    List<Note> notes = mNoteDbOpenHelper.queryAllFromDb();
                                                    refreshData(notes);
                                                }else {
                                                    ToastUtil.toastShort(view.getContext(),"修改失败！");
                                                }
                                            }

                                            private String getCurrentTimeFormat() {
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                                Date date = new Date();
                                                return simpleDateFormat.format(date);
                                            }
                                        })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                });
                dialog.setContentView(v);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes == null? 0: mNotes.size();
    }


    public void removeData(int pos) {
        mNotes.remove(pos);
        notifyItemRemoved(pos);
    }
    public void refreshData(List<Note> notes) {
        this.mNotes = notes;
        notifyDataSetChanged();
    }

    class RVViewHolder extends RecyclerView.ViewHolder {
        TextView mItemTitle;
        TextView mItemDeadLine;
        TextView mItemTime;
        ViewGroup rvContainer;

        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.item_target);
            mItemDeadLine = itemView.findViewById(R.id.item_deadLine);
            mItemTime = itemView.findViewById(R.id.item_countDownTime);
            rvContainer = itemView.findViewById(R.id.rv_item_container);
        }
    }
}
