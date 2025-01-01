package com.example.todo.resources;

import com.example.todo.db.TaskDAO;
import com.example.todo.models.Task;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
    private final TaskDAO taskDAO;

    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GET
    @UnitOfWork
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Task getTask(@PathParam("id") int id) {
        // return id; 
        return taskDAO.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
    }

    @POST
    @UnitOfWork
    public Task createTask(Task task) {
        return taskDAO.create(task);
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Task updateTask(@PathParam("id") int id, Task updatedTask) {
        Task existingTask = taskDAO.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setStartDate(updatedTask.getStartDate());
        existingTask.setTargetDate(updatedTask.getTargetDate());
        
        return taskDAO.create(existingTask);
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteTask(@PathParam("id") int id) {
        Task task = taskDAO.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        taskDAO.delete(task);
        return Response.ok().build();
    }
}
