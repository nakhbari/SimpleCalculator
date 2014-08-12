package com.nakhbari.simplecalculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int NUM_BUTTONS = 10;
	private static final double MAX_NUMBER_VALUE = 10000000;
	private static final double MIN_NUMBER_VALUE = -10000000;
	TextView tvInputEquation;
	TextView tvFormula;
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
				stringFormula = "";
			}

			// Clear values if clear was pressed or we are beginning a new
			// equation
			if (tag.toString() == "Clear") {
				previousOperand = 0;
				previousOperator = "";
				sum = 0;
				stringOperand = "0";
				stringFormula = "";

				// Reset both TextViews
				tvInputEquation.setText(stringOperand);
				tvFormula.setText(stringFormula);

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
					boolean wasOperationPerformed = false;

					// This case is for any operations
					isStringToBeEmptied = true;

					// Don't change the previous value if we get "=" more than
					// once
					if (!wasSumPressed || tag.toString() != "Sum") {
						previousOperand = Double.parseDouble(stringOperand);
					}

					if (sum == 0 && !wasSumPressed) {

						sum = previousOperand;
						stringFormula += FormatDoubleToString(sum);

					} else if (previousOperator != "" && previousOperand != 0) {
						wasOperationPerformed = true;

						stringFormula += " " + previousOperator + " ";
						stringFormula += FormatDoubleToString(previousOperand);

						// do the operation
						sum = ProcessOperation(previousOperator, sum,
								previousOperand);

					}

					// record the operation
					if (tag.toString() == "Sum") {
						wasSumPressed = true;
					} else {
						wasSumPressed = false;
						previousOperator = tag.toString();
					}

					// Temporarily show the operator chosen to the user
					if (previousOperator != "" && !wasOperationPerformed) {
						stringFormula += " " + previousOperator;
					}

					tvInputEquation.setText(FormatDoubleToString(sum));
					tvFormula.setText(stringFormula);

					// Remove operator from string, by removing the last 2 chars
					if (previousOperator != "" && !wasOperationPerformed) {
						stringFormula = stringFormula.substring(0,
								stringFormula.length() - 2);
					}
				}

			}
		}

	};

	private double ProcessOperation(String operator, double sum, double operand) {

		// Do Calculation depending on the value of the operator
		switch (operator) {
		case "+":
			sum += operand;
			break;
		case "-":
			sum -= operand;
			break;
		case "x":
			sum *= operand;
			break;
		case "/":
			sum /= operand;
			break;
		}

		return sum;

	}

	public static String FormatDoubleToString(double d) {
		if (d == (int) d) {
			return String.format("%d", (int) d);
		} else if (d > MAX_NUMBER_VALUE || d < MIN_NUMBER_VALUE) {
			NumberFormat formatter = new DecimalFormat("0.######E0");
			return formatter.format(d);

		} else {
			NumberFormat formatter = new DecimalFormat("0.######");
			return formatter.format(d);
			// return String.format("%.8f", d);
		}
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
		tvFormula = (TextView) findViewById(R.id.tvFormula);
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
		bAddition.setTag("+");

		bSubtraction.setOnClickListener(myListener);
		bSubtraction.setTag("-");

		bMultiplication.setOnClickListener(myListener);
		bMultiplication.setTag("x");

		bDivision.setOnClickListener(myListener);
		bDivision.setTag("/");

		bSum.setOnClickListener(myListener);
		bSum.setTag("Sum");

		bDecimalPoint.setOnClickListener(myListener);
		bDecimalPoint.setTag(".");

		bClear.setOnClickListener(myListener);
		bClear.setTag("Clear");

	}

}
