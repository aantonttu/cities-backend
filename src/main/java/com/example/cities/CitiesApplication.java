package com.example.cities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class CitiesApplication {

	public static void init() {
		String url = "jdbc:postgresql://localhost:8082/postgres";
		String user = "postgres";
		String password = "postgres";
		// read the csv
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader(
						"C:/Users/MSI/IdeaProjects/cities/src/main/java/com/example/cities/cities.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records.add(Arrays.asList(values));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// write to the database
		Connection conn;
		int insertedRows = 0;

		// dropping the schema along with the table to not insert duplicate rows
		try {
			conn = DriverManager.getConnection(url, user, password);

			Statement stCreateSchema = conn.createStatement();
			ResultSet rsCreateSchema = stCreateSchema.executeQuery("drop schema if exists cities_schema cascade");

			System.out.println(rsCreateSchema);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			conn = DriverManager.getConnection(url, user, password);

			Statement stCreateSchema = conn.createStatement();
			ResultSet rsCreateSchema = stCreateSchema.executeQuery("create schema if not exists cities_schema");

			System.out.println(rsCreateSchema);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			conn = DriverManager.getConnection(url, user, password);

			Statement stCreateTable = conn.createStatement();
			ResultSet rsCreateTable = stCreateTable.executeQuery(
					"create table if not exists cities_schema.cities " +
							"(id integer, name varchar(255), picture varchar)");

			System.out.println(rsCreateTable);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

//		try {
//			conn = DriverManager.getConnection(url, user, password);
//
//			Statement stCreateUsers = conn.createStatement();
//			ResultSet rsCreateUsers = stCreateUsers.executeQuery(
//					"create table if not exists cities_schema.users " +
//							"(id integer, username varchar(255), password varchar(255), role_allow_edit boolean)");
//
//			System.out.println(rsCreateUsers);
//
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}


		try {
			conn = DriverManager.getConnection(url, user, password);
			for (List<String> record : records) {
				if (!Objects.equals(record.get(0), "id")) {
					PreparedStatement insertStmt =
							conn.prepareStatement("insert into cities_schema.cities(id, name, picture) " +
									"values (?, ?, ?)");

					insertStmt.setInt(1, Integer.parseInt(record.get(0)));
					insertStmt.setString(2, record.get(1));
					insertStmt.setString(3, record.get(2));

					insertStmt.executeUpdate();

					insertedRows += 1;
				}
			}
			System.out.println("Rows inserted: " + insertedRows);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
		init();
	}
}