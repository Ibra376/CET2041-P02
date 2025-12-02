package restAPI;

import DTO.DeptEmpDTO;
import DTO.PromotionDTO;
import EMF.EMF;
import daos.*;
import employeesdb.Departments;
import employeesdb.Employees;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/records")
public class EmployeeRecords {

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @GET
    @Path("/allDepartments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allDepartments() {
        EntityManager em = EMF.getEntityManager();
        DeptDAO deptDAO = new DeptDAO(em);
        List<Departments> departmentsList;
        try {
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
                if (emp.getDept_emp() != null) {
                    emp.getDept_emp().size();
                }
                if (emp.getDept_manager() != null) {
                    emp.getDept_manager().size();
                }
            }

        } finally {
            em.close();
        }

        if (emp == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR: Employee " + empNo + " not found")
                    .build();
        }
        return Response.ok().entity(emp).build();
    }

    //endpoint 3

    /**
     * GET endpoint to retrieve all employees for a given department and optional page number
     *
     * @param deptNo Department Number (required)
     * @param pageNumStr Optional page number as a string. Defaults to "1" if not provided or empty. Must be positive
     *                  integer >0
     * @return Response containing JSON List of DeptEmpDTO if employees are found, or corresponding responses if
     * inputs are not valid.
     */
    @GET
    @Path("/department")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpByDept(@QueryParam("deptNo") String deptNo,
                                 @QueryParam("pageNum") @DefaultValue("1") String pageNumStr) {

        //check for invalid inputs
        //empty deptNo
        if (deptNo == null || deptNo.trim().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ERROR: deptNo is required.").build();
        }

        int pageNum;
        //if pageNum is empty (field is filledin but no input), then default back to 1
        if (pageNumStr.trim().equals("")) {
            pageNum = 1;
        }
        else{
            //pageNum numerical but not integer
            try{
                pageNum = Integer.parseInt(pageNumStr);
            }
            catch (NumberFormatException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ERROR: pageNum must be integer.").build();
            }
        }

        //pageNum <=0
        if(pageNum<1) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ERROR: pageNum must be positive integer > 0.").build();
        }

        EntityManager em = EMF.getEntityManager();
        DeptEmpDAO deptEmpDAO = new DeptEmpDAO(em);
        List<DeptEmpDTO> deptEmpDTO;

        try{
            deptEmpDTO = deptEmpDAO.getDeptEmp(deptNo.trim(), pageNum);
        }
        finally {
            em.close();
        }

        //error message if output is empty
        if(deptEmpDTO.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR: Department \""+deptNo+"\" not found").build();
        }

        return Response.ok().entity(deptEmpDTO).build();
    }

    // Endpoint 4: Promotions
    @POST
    @Path("/promote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response promoteEmployee(PromotionDTO promote) {

        if (promote == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: Promotion details are required.")
                    .build();
        }

        if (promote.getEmpNo() <= 0 ||
                promote.getDeptNo() == null || promote.getDeptNo().trim().isEmpty() ||
                promote.getNewTitle() == null || promote.getNewTitle().trim().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: empNo, deptNo, and newTitle are required.")
                    .build();
        }

        if (promote.getNewSalary() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: newSalary must be a positive integer.")
                    .build();
        }

        EntityManager em = EMF.getEntityManager();

        try {
            PromotionDAO promoDAO = new PromotionDAO(em);

            // isManager is derived inside PromotionDAO from newTitle
            promoDAO.promoteEmployee(
                    promote.getEmpNo(),
                    promote.getDeptNo(),
                    promote.getNewTitle(),
                    promote.getNewSalary()
            );

            return Response.status(Response.Status.CREATED)
                    .entity(promote)
                    .build();

        } catch (RuntimeException ex) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error: " + ex.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity("{\"error\":\"Failed to process promotion\"}")
                    .build();
        } finally {
            em.close();
        }
    }
}
