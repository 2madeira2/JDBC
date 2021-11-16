import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WaybillPositionDAO implements DAO<WaybillPosition> {
    final Connection connection;

    private static final String get_by_position_request = "SELECT position,price,nomenclature,amount,waybill FROM waybill_position WHERE position = ?;";
    private static final String get_all_request = "SELECT position,price,nomenclature,amount,waybill FROM waybill_position;";
    private static final String save_request = "INSERT INTO waybill_position(position,price,nomenclature,amount,waybill) VALUES(?,?,?,?,?);";
    private static final String update_request = "UPDATE waybill_position SET price = ?,nomenclature= ?,amount=?,waybill = ?  WHERE position = ?;";
    private static final String delete_request = "DELETE FROM waybill_position WHERE position = ?;";

    public WaybillPositionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public WaybillPosition get(int position) {
        try (PreparedStatement stmt = connection.prepareStatement(get_by_position_request)) {
            stmt.setInt(1, position);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return new WaybillPosition(rs.getInt("position"), rs.getInt("price"), rs.getInt("nomenclature"), rs.getInt("amount"), rs.getInt("waybill"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with position " + position + "not found");
    }


    @Override
    public List<WaybillPosition> getAll() {
        final List<WaybillPosition> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(get_all_request)) {
                while (rs.next()) {
                    result.add(new WaybillPosition(rs.getInt("position"), rs.getInt("price"), rs.getInt("nomenclature"), rs.getInt("amount"), rs.getInt("waybill")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(WaybillPosition entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(save_request)) {
            preparedStatement.setInt(1, entity.getPosition());
            preparedStatement.setInt(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getNomenclature());
            preparedStatement.setInt(4, entity.getAmount());
            preparedStatement.setInt(5, entity.getWaybill());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(WaybillPosition entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(update_request)) {
            int cnt = 1;
            preparedStatement.setInt(cnt++, entity.getPrice());
            preparedStatement.setInt(cnt++, entity.getNomenclature());
            preparedStatement.setInt(cnt++, entity.getAmount());
            preparedStatement.setInt(cnt++, entity.getWaybill());
            preparedStatement.setInt(cnt++, entity.getPosition());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(WaybillPosition entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete_request)) {
            preparedStatement.setInt(1, entity.getPosition());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with position = " + entity.getPosition() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
