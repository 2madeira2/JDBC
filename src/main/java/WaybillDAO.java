import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WaybillDAO implements DAO<Waybill> {
    final Connection connection;

    public WaybillDAO(Connection connection) {
        this.connection = connection;
    }

    public Waybill get(int waybill_num) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT waybill_num,waybill_date,org_sender FROM waybill WHERE waybill_num = " + waybill_num)) {
                while (rs.next()) {
                    return new Waybill(rs.getInt("waybill_num"), rs.getDate("waybill_date").toLocalDate(), rs.getString("org_sender"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with number " + waybill_num + "not found");
    }

    @Override
    public List<Waybill> getAll() {
        final List<Waybill> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT waybill_num,waybill_date,org_sender FROM waybill")) {
                while (rs.next()) {
                    result.add(new Waybill(rs.getInt("waybill_num"), rs.getDate("waybill_date").toLocalDate(), rs.getString("org_sender")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Waybill entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO waybill(waybill_num,waybill_date,org_sender) VALUES(?,?,?)")) {
            preparedStatement.setInt(1, entity.getWaybillNum());
            Date date = Date.valueOf(entity.getWaybillDate());
            preparedStatement.setDate(2, date);
            preparedStatement.setString(3, entity.getOrgSender());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(Waybill entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE waybill SET  waybill_date= ?,org_sender= ?  WHERE waybill_num = ?")) {
            int cnt = 1;
            preparedStatement.setDate(cnt++, Date.valueOf(entity.getWaybillDate()));
            preparedStatement.setString(cnt++, entity.getOrgSender());
            preparedStatement.setInt(cnt++, entity.getWaybillNum());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Waybill entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM waybill WHERE waybill_num = ?")) {
            preparedStatement.setInt(1, entity.getWaybillNum());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with number = " + entity.getWaybillNum() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
