//package restAPI;
//
//import employeesdb.Departments;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//@Path("/departments")
//public class DepartmentRecords {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @GET
//    @Path("/ping")
//    public Response ping() {
//        return Response.ok().entity("Departments service online").build();
//    }
//
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getDepartment(@PathParam("id") String deptNo) {
//        Departments dept = em.find(Departments.class, deptNo);
//        if (dept == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Department not found\"}")
//                    .build();
//        }
//        return Response.ok(dept).build();
//    }
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @Transactional
//    public Response createDepartment(Departments dept) {
//        em.persist(dept);
//        return Response.status(Response.Status.CREATED).entity(dept).build();
//    }
//}