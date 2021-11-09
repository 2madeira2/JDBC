import org.flywaydb.core.Flyway;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        final Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://127.0.0.1:5432/test", "postgres", "whoami")
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();


        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "whoami")) {
            System.out.println("Connection OK.");

            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Magnit',34,432432451)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Patyorochka',344314,43244451)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Centre',34214314,43243224451)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Perekrestok',342143134,4324434232451)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Xiaomi',3314424242,4342224322451)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('adidas',34314,43422243245142)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('FixPrice',7414,434243245143)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Nike',2177314,43422243245421)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Puma',3443217744314,43422254241)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Billa',34214,4241)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Mail',3421774314,434222)");
                stmt.executeUpdate("INSERT INTO organization(name,\"INN\",checking_account) VALUES('Yandex',342177225314,434224341)");
            }


            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  1,'2010-04-01','Magnit')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  2,'2010-05-01','Patyorochka')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  3,'2010-06-01','Perekrestok')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  4,'2010-07-01','Centre')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  5,'2010-08-01','Xiaomi')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  6,'2010-09-01','adidas')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  7,'2010-10-01','FixPrice')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  8,'2010-11-01','Nike')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  9,'2010-12-01','Puma')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  10,'2011-01-01','Billa')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  11,'2011-02-01','Mail')");
                stmt.executeUpdate("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(  12,'2011-03-01','Yandex')");


            }
            //Insert into Nomenclature.
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("INSERT INTO nomenclature(id,name,internal_code) VALUES(1,'Milk',2242) ");
                stmt.executeUpdate("INSERT INTO nomenclature(id,name,internal_code) VALUES(2,'Sneaker',24432) ");
                stmt.executeUpdate("INSERT INTO nomenclature(id,name,internal_code) VALUES(3,'Smart_Station',3224) ");
            }
            //Into WayBill_position.
            try (Statement stmt = connection.createStatement()) {

                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (1,540000,1,40000,1)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (2,550000,1,41000,2)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (3,530000,1,44000,3)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (4,540000,1,42000,4)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (5,520000,1,44000,7)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (6,560000,1,40000,10)");

                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (7,1000000,2,5200,6)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (8,1200000,2,5100,8)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (9,1400000,2,5400,9)");

                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (10,500000,3,11000,5)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (11,510000,3,13200,12)");
                stmt.executeUpdate("INSERT INTO waybill_position(position ,price,nomenclature,amount,waybill) VALUES (12,490000,3,13100,11)");
            }


            System.out.println("Report 1: Top 10 suppliers of the number of delivered goods ");
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select w.org_sender\n" +
                        "from waybill w \n" +
                        "where w.waybill_num in\n" +
                        "(select  wp.waybill\n" +
                        "from  waybill_position wp \n" +
                        "order by wp.amount DESC\n" +
                        "limit 10);")) {
                    while (rs.next()) {
                        System.out.println(rs.getString("org_sender"));
                    }
                }
            }
            System.out.println("Report 2: suppliers of the number of delivered goods greater than 42000");
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select w.org_sender \n" +
                        "from waybill w  \n" +
                        "where w.waybill_num in (select wp.waybill\n" +
                        "from  waybill_position wp \n" +
                        "where wp.amount >42000\n" +
                        ");")) {
                    while (rs.next()) {
                        System.out.println(rs.getString("org_sender"));
                    }
                }
            }
            System.out.println("Report 3: quantity and amount of goods received in the specified period ");
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select sum(wp.amount)as total_amount,\n" +
                        "\t\tsum(wp.price) as total_price\n" +
                        "from waybill_position wp\n" +
                        "where wp.waybill in(\n" +
                        "select w.waybill_num\n" +
                        "from waybill w\n" +
                        "where w.waybill_date between '2010-04-01'\n" +
                        "and '2010-07-01')\n")) {
                    while (rs.next()) {
                        System.out.println(rs.getString("total_price"));
                        System.out.println(rs.getString("total_amount"));
                    }
                }
            }
            System.out.println("Report 4: average price of goods for the received period ");
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select avg(wp.price) \n" +
                        "from waybill_position wp\n" +
                        "where wp.waybill in(\n" +
                        "select w.waybill_num\n" +
                        "from waybill w\n" +
                        "where w.waybill_date between '2010-04-01'\n" +
                        "and '2010-07-01')")) {
                    while (rs.next()) {
                        System.out.println(rs.getString("avg"));
                    }
                }
            }
            System.out.println("Report 5: list of goods supplied by organizations for the period ");
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select n.name, w.org_sender\n" +
                        "from waybill w \n" +
                        "join waybill_position wp on w.waybill_num=wp.waybill\n" +
                        "join  nomenclature n on wp.nomenclature=n.id\n" +
                        "where w.waybill_date between '2010-04-01'\n" +
                        "and '2011-03-01' ;")) {
                    while (rs.next()) {
                        System.out.print(rs.getString("name") + " ");
                        System.out.println(rs.getString("org_sender"));
                    }
                }
            }


        } catch (SQLException throwables) {
            System.out.println("Connection failure.");
            throwables.printStackTrace();
        }
    }
}
