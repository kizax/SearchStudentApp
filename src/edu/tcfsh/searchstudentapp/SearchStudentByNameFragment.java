package edu.tcfsh.searchstudentapp;

import com.example.effectivenavigation.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchStudentByNameFragment extends SearchStudentFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.search_student_by_name,
				container, false);
		super.initialize(rootView);
		super.initializeFileWriter();
		super.setListener();
		return rootView;
	}
}
