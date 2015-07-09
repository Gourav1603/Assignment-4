
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c0617418
 */
@Path("/products")
public class serveletdemo {
       @GET
    @Produces("application/json")
    public Response getAll() {

        return Response.ok(getResult("SELECT * FROM product")).build();
        // return Response.entity(getResult("SELECT * FROM product")).build();

    }
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") String id) {

        return Response.ok(getResult("SELECT * FROM product WHERE productID=?", String.valueOf(id))).build();

        // return Response.entity(getResult("SELECT * FROM product")).build();
    }

    private Object getResult(String select__from_product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object getResult(String select__from_product_WHERE_productID, String valueOf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(JsonObject json) {

        String name = json.getString("name");
        String description = json.getString("description");
        String quantity = String.valueOf(json.getInt("quantity"));

        System.out.println(name + '\t' + description + '\t' + quantity);

        int result = doUpdate("INSERT INTO product (name,description,quantity) VALUES (?,?,?)", name, description, quantity);
        if (result <= 0) {
            return Response.status(500).build();
        } else {
            return Response.ok(json).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateData(@PathParam("id") String id, JsonObject json) {

        String name = json.getString("name");
        String description = json.getString("description");
        String quantity = String.valueOf(json.getInt("quantity"));

        System.out.println(name + '\t' + description + '\t' + quantity +'\t'+ id);

        int result = doUpdate("UPDATE product SET name=?,description=?,quantity=? where productID=?", name, description, quantity, String.valueOf(id));
        if (result <= 0) {
            return Response.status(500).build();
        } else {
            return Response.ok(json).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")

    public Response deleteById(@PathParam("id") String id) {

        int result = doUpdate("DELETE FROM product where productID=? ", String.valueOf(id));

        if (result <= 0) {
            return Response.status(500).build();
        } else {
            return Response.ok().build();
        }
        // return Response.entity(getResult("SELECT * FROM product")).build();

    }

    public String getResult(String query, String... parameter) {
        StringBuilder sb = new StringBuilder();
        JSONObject obj = new JSONObject();

        try (Connection conn = connect.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(query);
            for (int i = 1; i <= parameter.length; i++) {
                pst.setString(i, parameter[i - 1]);
            }
            System.out.println(parameter.length);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                obj.put("productID", rs.getInt("productID"));
                obj.put("name", rs.getString("name"));
                obj.put("description", rs.getString("description"));
                obj.put("quantity", rs.getInt("quantity"));
                sb.append(obj.toJSONString());
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return sb.toString();
    }

    private int doUpdate(String query, String... parameter) {
        int change = 0;
        try (Connection conn = connect.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= parameter.length; i++) {
                pstmt.setString(i, parameter[i - 1]);
            }
            change = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(serveletdemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return change;
    } // update coding
    
}
