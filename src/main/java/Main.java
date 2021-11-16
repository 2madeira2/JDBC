import org.flywaydb.core.Flyway;
import java.sql.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        final String first_request = "select w.org_sender from waybill w join waybill_position wp on w.waybill_num = wp.waybill order by wp.amount DESC LIMIT ?;";
        final String second_request = "select w.org_sender from waybill w where w.waybill_num in (select wp.waybill from waybill_position wp where wp.amount > ?);";
        final String third_request = "select sum(wp.amount)as total_amount, sum(wp.price) as total_price from waybill_position wp where wp.waybill in( select w.waybill_num from waybill w where w.waybill_date between ? and ?);";
        final String fourth_request = "select avg(wp.price) from waybill_position wp where wp.waybill in( select w.waybill_num from waybill w where w.waybill_date between ? and ?);";
        final String fifth_request = "select n.name, w.org_sender from waybill w join waybill_position wp on w.waybill_num=wp.waybill join  nomenclature n on wp.nomenclature=n.id where w.waybill_date between ? and ?;";

        final Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://127.0.0.1:5432/test", "postgres", "whoami")
                .locations("db")
                .load();
        flyway.clean();
        flyway.migrate();



        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "whoami")) {
            System.out.println("Connection OK.");

            OrganizationDAO organizationDAO = new OrganizationDAO(connection);
            organizationDAO.save(new Organization("Magnit", 34, 432432451));
            organizationDAO.save(new Organization("Patyorochka",344314,43244451));
            organizationDAO.save(new Organization("Centre",34214314,432432241));
            organizationDAO.save(new Organization("Perekrestok",342143134,43244342));
            organizationDAO.save(new Organization("Xiaomi",33144242,42243251));
            organizationDAO.save(new Organization("adidas",34314,434245142));
            organizationDAO.save(new Organization("FixPrice",7414,434245143));
            organizationDAO.save(new Organization("Nike",2177314,434242421));
            organizationDAO.save(new Organization("Puma",344344314,422254241));
            organizationDAO.save(new Organization("Billa",34214,42411));
            organizationDAO.save(new Organization("Mail",34217744,434222));
            organizationDAO.save(new Organization("Yandex",342175314,434224341));


            WaybillDAO waybillDAO = new WaybillDAO(connection);
            waybillDAO.save(new Waybill(1, LocalDate.of(2010, 4, 1),"Magnit"));
            waybillDAO.save(new Waybill(2, LocalDate.of(2010, 5, 1),"Patyorochka"));
            waybillDAO.save(new Waybill(3, LocalDate.of(2010, 6, 1),"Perekrestok"));
            waybillDAO.save(new Waybill(4, LocalDate.of(2010, 7, 1),"Centre"));
            waybillDAO.save(new Waybill(5, LocalDate.of(2010, 8, 1),"Xiaomi"));
            waybillDAO.save(new Waybill(6, LocalDate.of(2010, 9, 1),"adidas"));
            waybillDAO.save(new Waybill(7, LocalDate.of(2010, 10, 1),"FixPrice"));
            waybillDAO.save(new Waybill(8, LocalDate.of(2010, 11, 1),"Nike"));
            waybillDAO.save(new Waybill(9, LocalDate.of(2010, 12, 1),"Puma"));
            waybillDAO.save(new Waybill(10, LocalDate.of(2011, 1, 1),"Billa"));
            waybillDAO.save(new Waybill(11, LocalDate.of(2011, 2, 1),"Mail"));
            waybillDAO.save(new Waybill(12, LocalDate.of(2011, 3, 1),"Yandex"));

            //Insert into Nomenclature.
            NomenclatureDAO nomenclatureDAO = new NomenclatureDAO(connection);
            nomenclatureDAO.save(new Nomenclature(1, "Milk", 2242));
            nomenclatureDAO.save(new Nomenclature(2, "Sneaker", 24432));
            nomenclatureDAO.save(new Nomenclature(3, "Smart_Station", 3224));


            //Into WayBill_position.

            WaybillPositionDAO waybillPositionDAO = new WaybillPositionDAO(connection);
            waybillPositionDAO.save(new WaybillPosition(1,540000,1,40000,1));
            waybillPositionDAO.save(new WaybillPosition(2,550000,1,41000,2));
            waybillPositionDAO.save(new WaybillPosition(3,530000,1,44000,3));
            waybillPositionDAO.save(new WaybillPosition(4,540000,1,42000,4));
            waybillPositionDAO.save(new WaybillPosition(5,520000,1,44000,7));
            waybillPositionDAO.save(new WaybillPosition(6,560000,1,40000,10));
            waybillPositionDAO.save(new WaybillPosition(7,1000000,2,5200,6));
            waybillPositionDAO.save(new WaybillPosition(8,1200000,2,5100,8));
            waybillPositionDAO.save(new WaybillPosition(9,1400000,2,5400,9));
            waybillPositionDAO.save(new WaybillPosition(10,500000,3,11000,5));
            waybillPositionDAO.save(new WaybillPosition(11,510000,3,13200,12));
            waybillPositionDAO.save(new WaybillPosition(12,490000,3,13100,11));


            System.out.println("Report 1: Top 10 suppliers of the number of delivered goods ");
            try (PreparedStatement stmt = connection.prepareStatement(first_request)) {
                stmt.setInt(1, 5);
                try (ResultSet rs = stmt.executeQuery())
                {
                    while (rs.next()) {
                        System.out.println(rs.getString("org_sender"));
                    }
                }
            }
            System.out.println("Report 2: suppliers of the number of delivered goods greater than 42000");
            try (PreparedStatement stmt = connection.prepareStatement(second_request)) {
                stmt.setInt(1,42000);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("org_sender"));
                    }
                }
            }
            System.out.println("Report 3: quantity and amount of goods received in the specified period ");
            try (PreparedStatement stmt = connection.prepareStatement(third_request)) {
                stmt.setDate(1, java.sql.Date.valueOf("2010-04-01"));
                stmt.setDate(2, java.sql.Date.valueOf("2010-07-01"));
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("total_price"));
                        System.out.println(rs.getString("total_amount"));
                    }
                }
            }
            System.out.println("Report 4: average price of goods for the received period ");
            try (PreparedStatement stmt = connection.prepareStatement(fourth_request)) {
                stmt.setDate(1, java.sql.Date.valueOf("2010-04-01"));
                stmt.setDate(2, java.sql.Date.valueOf("2010-07-01"));
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("avg"));
                    }
                }
            }
            System.out.println("Report 5: list of goods supplied by organizations for the period ");
            try (PreparedStatement stmt = connection.prepareStatement(fifth_request)) {
                stmt.setDate(1, java.sql.Date.valueOf("2010-04-01"));
                stmt.setDate(2, java.sql.Date.valueOf("2011-03-01"));
                try (ResultSet rs = stmt.executeQuery()) {
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
