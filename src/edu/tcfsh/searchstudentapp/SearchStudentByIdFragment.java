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

public class SearchStudentByIdFragment extends SearchStudentFragment {
	private ArrayList<StudentRecord> studentRecordList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.search_student_by_id,
				container, false);
		initialize(rootView);
		initializeFileWriter();
		setListener();
		return rootView;
	}


}