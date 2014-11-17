package edu.tcfsh.searchstudentapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.tcfsh.searchstudentapp.R;

public class MySimpleArrayAdapter extends ArrayAdapter<StudentRecord> {
	private final Context context;
	private final ArrayList<StudentRecord> studentRecordList;

	public MySimpleArrayAdapter(Context context,
			ArrayList<StudentRecord> studentRecordList) {
		super(context, R.layout.num_item_listview, studentRecordList);
		this.context = context;
		this.studentRecordList = studentRecordList;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.num_item_listview, parent,
					false);

			holder = new ViewHolder();

			holder.gradeText = (TextView) convertView
					.findViewById(R.id.gradeNum);
			holder.classText = (TextView) convertView
					.findViewById(R.id.classNum);
			holder.numText = (TextView) convertView.findViewById(R.id.num);
			holder.studentIdText = (TextView) convertView
					.findViewById(R.id.studentIdText);
			holder.studentNameText = (TextView) convertView
					.findViewById(R.id.studentNameText);
			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.gradeText.setText(String.valueOf(studentRecordList.get(position)
				.getGradeNum()));
		holder.classText.setText(String.valueOf(studentRecordList.get(position)
				.getClassNum()));
		holder.numText.setText(String.valueOf(studentRecordList.get(position)
				.getNum()));
		holder.studentIdText.setText(String.valueOf(studentRecordList.get(
				position).getStudentId()));
		holder.studentNameText.setText(String.valueOf(studentRecordList.get(
				position).getStudentName()));

		return convertView;
	}
}
