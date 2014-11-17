package edu.tcfsh.searchstudentapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import edu.tcfsh.searchstudentapp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ArrayList<StudentRecord> studentRecordList;

	private TextView statusText;
	private EditText regexEditText;
	private ListView myList;
	private Button commitButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		initializeFileWriter();
		setListener();
	}

	View.OnClickListener commitButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			String expression = regexEditText.getText().toString();
			statusText.setText("您的查詢：" + expression);
			regexEditText.setText("");

			ArrayList<StudentRecord> searchResultList = new ArrayList<StudentRecord>();
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

			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(
					myList.getContext(), searchResultList);

			myList.setAdapter(adapter);

		}
	};

	private void setListener() {
		commitButton.setOnClickListener(commitButtonListener);
	}

	private void initialize() {
		regexEditText = (EditText) findViewById(R.id.regexEditText);
		statusText = (TextView) findViewById(R.id.statusText);
		myList = (ListView) findViewById(R.id.list);
		commitButton = (Button) findViewById(R.id.commitButton);
		studentRecordList = new ArrayList<StudentRecord>();
	}

	private String getStudentFileFileName() {
		String studentDataFileName = "studentData.xls";
		return studentDataFileName;
	}

	private void initializeFileWriter() {

		// 指定xls存檔檔名
		String studentDataFileName = getStudentFileFileName();

		File SDCardpath = Environment.getExternalStorageDirectory();
		File studentData = new File(SDCardpath.getAbsolutePath() + "/student/"
				+ studentDataFileName);
		// File copyOfAttendanceRecord = new File(SDCardpath.getAbsolutePath()
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

	private void showToast(String msg) {

		Context context = getApplicationContext();
		CharSequence text = msg;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("提示").setMessage("確定離開?")
				.setPositiveButton("確定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
