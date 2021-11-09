import org.jetbrains.annotations.NotNull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class OrganizationDAO implements DAO<Organization> {

    final Connection connection;

    public OrganizationDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Organization get(int id) {
        return null;
    }

    public Organization get(@NotNull String name) {
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT name, \"INN\",checking_account  FROM organization WHERE name = " +"'" + name+"'")) {
                while (rs.next()) {
                    return new Organization(rs.getString("name"), rs.getInt("INN"), rs.getInt("checking_account"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalStateException("Record with name " + name + " not found");
    }

    @Override
    public List<Organization> getAll() {
        final List<Organization> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT name, \"INN\",checking_account FROM organization")) {
                while (rs.next()) {
                    result.add(new Organization(rs.getString("name"), rs.getInt("INN"), rs.getInt("checking_account")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO organization(name,\"INN\",checking_account) VALUES(?,?,?)")) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getINN());
            preparedStatement.setInt(3, entity.getCheckingAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE organization SET \"INN\" = ?,checking_account= ?  WHERE name = ?")) {
            int cnt = 1;
            preparedStatement.setInt(cnt++, entity.getINN());
            preparedStatement.setInt(cnt++, entity.getCheckingAccount());
            preparedStatement.setString(cnt++, entity.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM organization WHERE name = ?")) {
            preparedStatement.setString(1, entity.getName());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with name = " + entity.getName() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
