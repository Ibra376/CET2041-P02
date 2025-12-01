package restAPI;

import EMF.EMF;
import daos.DeptDAO;
import daos.EmployeeDAO;
import employeesdb.Departments;
import employeesdb.Dept_emp;
import employeesdb.Employees;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/records")
public class EmployeeRecords {

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
            EmployeeDAO employeeDAO = new EmployeeDAO(em);
            emp = employeeDAO.findEmployee(empNo);

            if (emp != null) {
                if (emp.getSalary() != null) {
                    emp.getSalary().size();
                }
                if (emp.getTitles() != null) {
                    emp.getTitles().size();
                }
            }

        } finally {
            em.close();
        }

        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Employee not found\"}")
                    .build();
        }

        return Response.ok().entity(emp).build();
    }

//    public static class PromotionRequest {
//        public int empNo;
//        public String newTitle;
//        public int newSalary;
//        public String effectiveDate;
//    }
//    @POST
//    @Path("/promote")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response promoteEmployee (PromotionRequest promotionReq) {
//        EntityManager em = EMF.getEntityManager();
//        em.getTransaction().begin();
//        Employees emp =  em.find(Employees.class, promotionReq.empNo);
//
//        if (emp == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("{\"error\":\"Employee not found\"}")
//                    .build();
//        }
//
//        LocalDate effectiveDate;
//        try{
//            effectiveDate = LocalDate.parse(promotionReq.effectiveDate);
//        }catch(Exception e){
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("{\"error\":\"Invalid effectiveDate, expected yyyy-MM-dd\"}")
//                    .build();
//        }
//
//    }


    @GET
    @Path("/department/{deptNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpByDept(@PathParam("deptNo") String deptNo) {
        EntityManager em = EMF.getEntityManager();
        DeptDAO deptDAO = new DeptDAO(em);
        List<Dept_emp> deptEmp;
        try{
            deptEmp = deptDAO.getDeptEmp(deptNo);
        }
        finally {
            em.close();
        }

        return Response.ok().entity(deptEmp).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createDepartment(Employees emp) {
        EntityManager em = EMF.getEntityManager();
        em.persist(emp);
        em.close();
        return Response.status(Response.Status.CREATED).entity(emp).build();
    }

}
