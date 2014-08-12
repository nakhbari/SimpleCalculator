package com.nakhbari.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private static final int NUM_BUTTONS = 10;
	TextView tvInputEquation;
	Button bZero;
	Button bNumArray[] = new Button[NUM_BUTTONS];
	Button bAddition;
	Button bSubtraction;
	Button bMultiplication;
	Button bDivision;
	Button bSum;
	Button bClear;
	Button bDecimalPoint;

	String stringOperand = "0";
	double sum = 0;
	String previousOperator = "";

	String stringFormula = "";
	double previousOperand = 0;
	boolean isStringToBeEmptied = false;
	boolean wasSumPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InitializeLayoutItems();

	}

	private OnClickListener myListener = new OnClickListener() {
		public void onClick(View v) {
			Object tag = v.getTag();

			boolean isInputInteger = IsInteger(tag.toString());

			if (isInputInteger && wasSumPressed) {
				previousOperand = 0;
				previousOperator = "";
				sum = 0;
				stringOperand = "0";
				wasSumPressed = false;
			}

			// Clear values if clear was pressed or we are beginning a new
			// equation
			if (tag.toString() == "Clear") {
				previousOperand = 0;
				previousOperator = "";
				sum = 0;
				stringOperand = "0";
				tvInputEquation.setText(stringOperand);
			} else {

				if (isStringToBeEmptied) {
					stringOperand = "0";
					isStringToBeEmptied = false;
				}

				if (tag.toString() == "0") {
					if (stringOperand != "0") {
						stringOperand += tag.toString();
					}

					tvInputEquation.setText(stringOperand);
				} else if (isInputInteger || tag.toString() == ".") {

					// This gets rid of numbers like "012" or "09..."
					if (stringOperand == "0") {
						stringOperand = "";
					}

					stringOperand += tag.toString();
					tvInputEquation.setText(stringOperand);
				} else {
					// This case is for any operations

					isStringToBeEmptied = true;

					// Don't change the previous value if we get "=" more than
					// once
					if (!wasSumPressed) {
						previousOperand = Double.parseDouble(stringOperand);
					}

					if (sum == 0) {

						sum = previousOperand;
					} else if (previousOperator != "") {

						// do the operation
						sum = ProcessOperation(previousOperator, sum,
								previousOperand);
					}

					// record the operation
					if (tag.toString() == "Sum") {
						wasSumPressed = true;
					} else {
						previousOperator = tag.toString();
					}

					tvInputEquation.setText(FormatDoubleToString(sum));
				}

			}
		}

	};

	private double ProcessOperation(String operator, double sum, double operand) {

		// Do Calculation depending on the value of the operator
		switch (operator) {
		case "Addition":
			sum += operand;
			break;
		case "Subtraction":
			sum -= operand;
			break;
		case "Multiplication":
			sum *= operand;
			break;
		case "Division":
			sum /= operand;
			break;
		}

		return sum;

	}

	public static String FormatDoubleToString(double d) {
		if (d == (int) d)
			return String.format("%d", (int) d);
		else
			return String.format("%.5f", d);
	}

	public static boolean IsInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	// To initialize the connection between the layout and the variables in code
	private void InitializeLayoutItems() {
		tvInputEquation = (TextView) findViewById(R.id.tvInputEquation);
		bNumArray[0] = (Button) findViewById(R.id.bZero);
		bNumArray[1] = (Button) findViewById(R.id.bOne);
		bNumArray[2] = (Button) findViewById(R.id.bTwo);
		bNumArray[3] = (Button) findViewById(R.id.bThree);
		bNumArray[4] = (Button) findViewById(R.id.bFour);
		bNumArray[5] = (Button) findViewById(R.id.bFive);
		bNumArray[6] = (Button) findViewById(R.id.bSix);
		bNumArray[7] = (Button) findViewById(R.id.bSeven);
		bNumArray[8] = (Button) findViewById(R.id.bEight);
		bNumArray[9] = (Button) findViewById(R.id.bNine);
		bAddition = (Button) findViewById(R.id.bAddition);
		bSubtraction = (Button) findViewById(R.id.bSubtraction);
		bMultiplication = (Button) findViewById(R.id.bMultiplication);
		bDivision = (Button) findViewById(R.id.bDivision);
		bSum = (Button) findViewById(R.id.bSum);
		bClear = (Button) findViewById(R.id.bClear);
		bDecimalPoint = (Button) findViewById(R.id.bDecimalPoint);

		// Attach the onClickListener and Tag
		for (int i = 0; i < bNumArray.length; i++) {
			bNumArray[i].setOnClickListener(myListener);
			bNumArray[i].setTag(String.valueOf(i));
		}

		bAddition.setOnClickListener(myListener);
		bAddition.setTag("Addition");

		bSubtraction.setOnClickListener(myListener);
		bSubtraction.setTag("Subtraction");

		bMultiplication.setOnClickListener(myListener);
		bMultiplication.setTag("Multiplication");

		bDivision.setOnClickListener(myListener);
		bDivision.setTag("Division");

		bSum.setOnClickListener(myListener);
		bSum.setTag("Sum");

		bDecimalPoint.setOnClickListener(myListener);
		bDecimalPoint.setTag(".");

		bClear.setOnClickListener(myListener);
		bClear.setTag("Clear");

	}

}
