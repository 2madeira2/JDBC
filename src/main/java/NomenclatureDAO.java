import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class NomenclatureDAO implements DAO<Nomenclature> {
    final Connection connection;

    public NomenclatureDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Nomenclature get(int id) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id,name, internal_code FROM nomenclature WHERE id = " + id)) {
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
            try (ResultSet rs = stmt.executeQuery("SELECT id,name, internal_code FROM nomenclature")) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO nomenclature(id,name,internal_code) VALUES(?,?,?)")) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE nomenclature SET name = ?,internal_code = ? WHERE id = ?")) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM nomenclature WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
