package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		JDBCConnection connection = new JDBCConnection();
		try (BufferedReader sc = new BufferedReader(new InputStreamReader(
				System.in))) {
			curdoperation(sc, connection);

		} catch (Exception e) {
		}
	}

	private static void curdoperation(BufferedReader sc,
			JDBCConnection connection) {
		System.out.println("--------------------------------------------");
		System.out.println(" Welcome to Curd Operation using JDBC API");
		System.out.println("--------------------------------------------");

		System.out.println("Choose the below Operation ");
		System.out.println("Press 1 for Select operation");
		System.out.println("Press 2 for Insert operation");
		System.out.println("Press 3 for Update operation");
		System.out.println("Press 4 for Delete operation");
		System.out.println("Press 0 for Exit operation");
		try {
			int operation = Integer.parseInt(sc.readLine());

			switch (operation) {
			case 1:
				connection.getCounryAllCountryDetails();
				break;
			case 2:
				System.out
						.println("Please enter the values which one you are going to deleted from DB");
				String cc = gettingInputValues(sc, "New");
				connection.insertCountry(cc.toUpperCase());
				break;
			case 3:
				System.out
						.println("Please enter the values which one you are going to updated");
				String oldcountryCode = gettingInputValues(sc, "Old");
				String newcountryCode = gettingInputValues(sc, "New");
				connection.updateCountry(oldcountryCode.toUpperCase(),
						newcountryCode.toUpperCase());
				break;
			case 4:
				System.out
						.println("Please enter the values which one you are going to deleted from DB");
				String countryCode = gettingInputValues(sc, "");
				connection.deleteCountry(countryCode.toUpperCase());
				break;
			case 0:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid operation");
				break;
			}
			System.out.println("Do you want countine Operation !!! Y/N : ");
			char check = sc.readLine().charAt(0);
			if (check == 'Y' | check == 'y') {
				curdoperation(sc, connection);
			} else if (check == 'N' | check == 'n') {
				System.out.println("Thank you");
				System.exit(0);
			} else {
				System.out.println("Invalid");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String gettingInputValues(BufferedReader sc, String type) {
		String cn = null;
		try {
			System.out.println("Enter the " + type + " Country Code");
			cn = sc.readLine();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cn;
	}

}
