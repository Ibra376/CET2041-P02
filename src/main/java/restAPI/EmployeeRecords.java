package restAPI;

import DTO.DeptEmpDTO;
import DTO.PromotionDTO;
import EMF.EMF;
import daos.*;
import employeesdb.Departments;
import employeesdb.Employees;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * REST API resource class that exposes endpoints for managing and retrieving employee records.
 */
@Path("/records")
public class EmployeeRecords {

    /**
     * Simple health check endpoint to verify that the service is online.
     *
     * @return HTTP 200 response with a plain text message "Service online"
     */
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    /**
     * Retrieves all departments from the database.
     *
     * @return HTTP 200 response containing a JSON list of {@link Departments}
     * if found, or HTTP 404 if the employee does not exist.
     */
    @GET
    @Path("/allDepartments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allDepartments() {
        EntityManagerFactory emf = EMF.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        DeptDAO deptDAO = new DeptDAO(em);
        List<Departments> departmentsList;
        try {
            departmentsList = deptDAO.getAllDepartments();
        } finally {
            em.close();
        }

        return Response.ok().entity(departmentsList).build();
    }

    /**
     * Retrieves the full employee record for a given employee number.
     *
     * @param empNo Employee number (primary key in the database)
     * @return HTTP 200 response with {@link Employees} JSON if found,
     *         or HTTP 404 if the employee does not exist.
     */
    @GET
    @Path("/{empNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("empNo") int empNo) {
        EntityManagerFactory emf = EMF.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Employees emp;
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO(em);
            emp = employeeDAO.findEmployee(empNo);
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

        EntityManagerFactory emf = EMF.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

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

    /**
     * Promotes an employee by updating their title and salary.
     * <p>
     * The {@link PromotionDTO} must contain:
     * <ul>
     *     <li>empNo - Employee number (positive integer)</li>
     *     <li>deptNo - Department number (non-empty string)</li>
     *     <li>newTitle - New job title (non-empty string)</li>
     *     <li>newSalary - New salary (positive integer)</li>
     * </ul>
     *
     * @param promote Promotion details encapsulated in {@link PromotionDTO}
     * @return HTTP 201 response with the promotion details if successful,
     *         HTTP 400 for invalid inputs or runtime errors,
     *         HTTP 500 if an unexpected error occurs.
     */
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

        EntityManagerFactory emf = EMF.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

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
                    .entity("promote")
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
