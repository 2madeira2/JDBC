import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class NomenclatureDAO implements DAO<Nomenclature> {
    final Connection connection;

    private static final String get_by_id_request = "SELECT id,name, internal_code FROM nomenclature WHERE id = ?";
    private static final String get_all_request = "SELECT * FROM nomenclature";
    private static final String save_request = "INSERT INTO nomenclature(id,name,internal_code) VALUES(?,?,?);";
    private static final String update_request = "UPDATE nomenclature SET name = ?, internal_code = ? WHERE id = ?;";
    private static final String delete_request = "DELETE FROM nomenclature WHERE id = ?;";

    public NomenclatureDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Nomenclature get(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(get_by_id_request)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return new Nomenclature(rs.getInt("id"), rs.getString("name"), rs.getInt("internal_code"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @Override
    public List<Nomenclature> getAll() {
        final List<Nomenclature> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(get_all_request)) {
                while (rs.next()) {
                    result.add(new Nomenclature(rs.getInt("id"), rs.getString("name"), rs.getInt("internal_code")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Nomenclature entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(save_request)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getInternalCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(Nomenclature entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(update_request)) {
            int cnt = 1;
            preparedStatement.setString(cnt++, entity.getName());
            preparedStatement.setInt(cnt++, entity.getInternalCode());
            preparedStatement.setInt(cnt++, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Nomenclature entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(delete_request)) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
