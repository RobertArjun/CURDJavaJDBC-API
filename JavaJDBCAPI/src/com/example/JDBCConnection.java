package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
	final String URL;
	final String USER;
	final String PASSWORD;
	String query = "select * from country";
	String selectQuery = "select * from country where country_code = ?";
	String insertQuery = "INSERT into country (country_code,status) VALUES(?,?)";
	String updateQuery = "UPDATE country set country_code = ? where country_code = ? and status = ?";
	String deleteQuery = "DELETE from country where country_code = ?";

	JDBCConnection() {
		URL = "jdbc:mysql://localhost:3306/userDB?useSSL=false";
		USER = "root";
		PASSWORD = "root$";

	}

	public void getCounryAllCountryDetails() {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {

			while (rs.next()) {
				String countryCode = rs.getString("country_code");
				String status = rs.getString("status");
				int country_id = rs.getInt("country_id");
				System.out.println("Country ID is: " + country_id
						+ ", Country Code: " + countryCode
						+ " And the Status = " + status);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertCountry(String cc) {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				ResultSet rs = null;
				PreparedStatement pStmt = createPreaparedStatement(con,
						insertQuery, cc, rs);) {

			System.out.println("Sucessfully Inserted..");
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	private PreparedStatement createPreaparedStatement(Connection con,
			String insert, String cc, ResultSet rs) throws SQLException {
		PreparedStatement ps;
		PreparedStatement ps1 = null;
		ps = con.prepareStatement(selectQuery);
		ps.setString(1, cc);
		rs = ps.executeQuery();

		int count = 0;
		while (rs.next()) {
			count++;
		}
		if (count  == 0) {
			ps1 = con.prepareStatement(insert);
			ps1.setString(1, cc);
			ps1.setString(2, "Active");
			ps1.executeUpdate();
		}
		ps.close();
		return ps1;
	}

	public void updateCountry(String countryCode, String newcountryCode) {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pStmt = createUpdatePreparedStatement(con,
						countryCode, newcountryCode)) {
			System.out.println("Updated Successfully from " + countryCode
					+ " to " + newcountryCode);
		} catch (Exception e) {
			System.out.println();
		}
	}

	private PreparedStatement createUpdatePreparedStatement(Connection con,
			String oldCountryCode, String newcountryCode) throws SQLException {
		PreparedStatement ps = con.prepareStatement(updateQuery);
		ps.setString(1, newcountryCode);
		ps.setString(2, oldCountryCode);
		ps.setString(3, "Active");
		ps.executeUpdate();
		return ps;
	}

	public void deleteCountry(String countryCode) {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pStmt = createDeleteQuery(con, countryCode)) {
			System.out.println("Sucessfully deleted country code: "
					+ countryCode);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private PreparedStatement createDeleteQuery(Connection con,
			String countryCode) throws SQLException {
		PreparedStatement ps = con.prepareStatement(deleteQuery);
		ps.setString(1, countryCode);
		ps.executeUpdate();
		return ps;
	}
}
