package com.example.cities;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@CrossOrigin(maxAge = 3600)
@RequestMapping("cities")
@RestController
public class Controller {
    String url = "jdbc:postgresql://localhost:8082/postgres";
    String user = "postgres";
    String password = "postgres";

    @GetMapping("{page}")
    public List<Map<String, String>> getCities(@PathVariable Long page) {
        Connection conn;
        try {
            List<Map<String, String>> r = new ArrayList<>();

            conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();

            System.out.printf("select * from cities where id between %d and %d", (page * 9) - 8, page * 9);
            ResultSet rs = st.executeQuery(String.format(
                    "select * from cities_schema.cities " +
                            "where id between %d and %d " +
                            "order by id asc", (page * 9) - 8, page * 9));

            while (rs.next()) {
                Map<String, String> map = new HashMap<>();

                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("picture", rs.getString("picture"));

                System.out.println(map);
                r.add(map);
            }
            System.out.println(r);
            return r;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/name/{param}")
    public List<Map<String, String>> getCitiesByName(@PathVariable String param) {
        Connection conn;
        try {
            List<Map<String, String>> r = new ArrayList<>();

            conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(
                    "select * from cities_schema.cities " +
                            "where lower(name) like '%" + param.toLowerCase() + "%' " +
                            "order by id asc");

            while (rs.next()) {
                Map<String, String> map = new HashMap<>();

                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("picture", rs.getString("picture"));

//                System.out.println(map);
                r.add(map);
            }
//            System.out.println(r);
            return r;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/city/{id}")
    public Map<String, String> getCity(@PathVariable Long id) {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();

//            System.out.printf("select * from cities where id = %d%n", id);
            ResultSet rs = st.executeQuery(String.format("select * from cities_schema.cities where id = %d", id));

            Map<String, String> map = new HashMap<>();

            rs.next();

            map.put("id", rs.getString("id"));
            map.put("name", rs.getString("name"));
            map.put("picture", rs.getString("picture"));

//            System.out.println(map);
            return map;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PutMapping("/city/{id}/update")
    public void updateCity(@PathVariable Long id, @RequestBody Map<String, String> data) {
        Connection conn;

        try {
            conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();

            String name = data.get("name");
            String picture = data.get("picture");

//            System.out.println(name);
//            System.out.println(picture);

            int rs = st.executeUpdate(String.format(
                    "update cities_schema.cities " +
                    "set name = '%s', picture = '%s'" +
                    "where id = %d"
                    , name, picture, id)
            );

            System.out.println("Rows updated: " + rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}