package jp.co.pnop.moris.func.sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    @FunctionName("HttpTrigger-Java")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = { HttpMethod.GET, HttpMethod.POST }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        Map<String, String> response = new HashMap<String, String>();
        try {
            Connection con = HikariCpDbSource.getConnection();
            ResultSet res = con.createStatement().executeQuery("SELECT TOP 10 [BusinessEntityId] as [Id], [FirstName], [LastName] FROM [Person].[Person] Order by [Id]");
            while (res.next()) {
                String id =  res.getString("Id");
                String firstName = res.getString("FirstName");
                String lastName =  res.getString("LastName");
                context.getLogger().info(String.format("%s : %s %s", id, firstName, lastName));
                response.put(id, String.format("%s %s", firstName, lastName));
            }
        } catch (SQLException e) {
            context.getLogger().log(Level.SEVERE, "error", e);
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid query").build();
        }

        return request.createResponseBuilder(HttpStatus.OK).body(response).build();
    }
}
