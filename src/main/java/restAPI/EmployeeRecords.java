package restAPI;

import EMF.EMF;
import daos.DeptDAO;
import daos.EmployeeDAO;
import employeesdb.Departments;
import employeesdb.Employees;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/records")
public class EmployeeRecords {

//    @PersistenceContext
//    private EntityManager em;



    @GET
    @Path("/ping")
    public Response ping() { return Response.ok().entity("Service online").build(); }

    @GET
    @Path("/allDepartments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allDepartments() {
        EntityManager em = EMF.getEntityManager();
        DeptDAO deptDAO = new DeptDAO(em);
        List<Departments> departmentsList;
        try{
            departmentsList = deptDAO.getAllDepartments();
        } finally {
            em.close();
        }

        return Response.ok().entity(departmentsList).build();
    }

    // Endpoint 2: return full employee record
    @GET
    @Path("/{empNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("empNo") int empNo) {
        EntityManager em = EMF.getEntityManager();
        Employees emp;

        try {
            EmployeeDAO dao = new EmployeeDAO(em);
            emp = dao.findEmployee(empNo);
        } finally {
            em.close();
        }

        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Employee not found\"}")
                    .build();
        }

        EmployeeDTO dto = new EmployeeDTO(
                emp.getEmpNo(),
                emp.getBirthDate().toString(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getGender().toString(),
                emp.getHireDate().toString()
        );

        return Response.ok(dto).build();
    }

//    @GET
//    @Path ("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getEmployee(@PathParam("id") String empNo) {
//        Employees emp = em.find(Employees.class, empNo);
//        if (emp == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Department not found\"}")
//                    .build();
//        }
//        return Response.ok(emp).build();
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createDepartment(Employees emp) {
        EntityManager em = EMF.getEntityManager();
        em.persist(emp);
        return Response.status(Response.Status.CREATED).entity(emp).build();
    }

}
