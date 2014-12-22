package edu.tcfsh.searchstudentapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.example.effectivenavigation.R;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchStudentFragment extends Fragment {
	private ArrayList<StudentRecord> studentRecordList;

	private TextView statusText;
	private EditText regexEditText;
	private ListView myList;
	private Button commitButton;
	private ArrayList<StudentRecord> searchResultList;
	private MySimpleArrayAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	View.OnClickListener commitButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			String expression = regexEditText.getText().toString();
			statusText.setText("您的查詢：" + expression);
			regexEditText.setText("");

			searchResultList.clear();
			switch (expression.length()) {
			case 2:
			case 3:
			case 4:
				for (StudentRecord rec : studentRecordList) {
					if (rec.matchStudentName(expression)) {
						searchResultList.add(rec);
					}
				}
				break;
			case 5:
				for (StudentRecord rec : studentRecordList) {
					if (rec.matchStudentNum(expression)) {
						searchResultList.add(rec);
					}
				}
				break;
			case 6:
				for (StudentRecord rec : studentRecordList) {
					if (rec.matchStudentID(expression)) {
						searchResultList.add(rec);
					}
				}
				break;
			}

			adapter.notifyDataSetChanged();

		}
	};

	protected void setListener() {
		commitButton.setOnClickListener(commitButtonListener);
	}

	protected void initialize(View rootView) {
		regexEditText = (EditText) rootView.findViewById(R.id.regexEditText);
		statusText = (TextView) rootView.findViewById(R.id.statusText);
		myList = (ListView) rootView.findViewById(R.id.list);
		commitButton = (Button) rootView.findViewById(R.id.commitButton);
		studentRecordList = new ArrayList<StudentRecord>();

		searchResultList = new ArrayList<StudentRecord>();
		adapter = new MySimpleArrayAdapter(myList.getContext(),
				searchResultList);
		myList.setAdapter(adapter);

	}

	protected String getStudentFileFileName() {
		String studentDataFileName = "studentData.xls";
		return studentDataFileName;
	}

	protected void initializeFileWriter() {

		// 指定xls存檔檔名
		String studentDataFileName = getStudentFileFileName();

		File SDCardpath = Environment.getExternalStorageDirectory();
		File studentData = new File(SDCardpath.getAbsolutePath() + "/student/"
				+ studentDataFileName);
		// File copyOfAttendanceRecord = new
		// File(SDCardpath.getAbsolutePath()
		// + "/attendance Record/" + attendanceRecordFileName + "_copy");

		Log.d("kizax", SDCardpath.getAbsolutePath() + "/student/"
				+ studentDataFileName);

		// 讀取學生名條
		try {
			if (studentData.exists()) {
				Workbook workbook = Workbook.getWorkbook(studentData);
				Sheet sheet = workbook.getSheet(0);

				int index = 1;

				while (index < sheet.getRows()) {

					int gradeNum = 0;
					int classNum = 0;
					int studentId = 0;
					String studentName = "";
					int num = 0;

					Cell studentIdCell = sheet.getCell(0, index);
					if (studentIdCell.getType() == CellType.NUMBER) {
						NumberCell nc = (NumberCell) studentIdCell;
						studentId = (int) nc.getValue();

					}

					Cell gradeAndClassCell = sheet.getCell(1, index);
					if (gradeAndClassCell.getType() == CellType.NUMBER) {
						NumberCell nc = (NumberCell) gradeAndClassCell;
						int gradeAndClass = (int) nc.getValue();
						gradeNum = gradeAndClass / 100;
						classNum = gradeAndClass % 100;
					}

					Cell numCell = sheet.getCell(2, index);
					if (numCell.getType() == CellType.NUMBER) {
						NumberCell nc = (NumberCell) numCell;
						num = (int) nc.getValue();
					}

					Cell studentNameCell = sheet.getCell(3, index);
					if (studentNameCell.getType() == CellType.LABEL) {
						LabelCell lc = (LabelCell) studentNameCell;
						studentName = lc.getString();
					}

					StudentRecord studentRecord = new StudentRecord(gradeNum,
							classNum, num, studentId, studentName);
					studentRecordList.add(studentRecord);

					index++;
				}

			} else {
				statusText.setText("學生名條檔案不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}

	}

}