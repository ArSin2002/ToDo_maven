package com.example.todo.db;

import com.example.todo.models.Task;
import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.SessionFactory;
import javax.persistence.criteria.CriteriaQuery;


import java.util.List;
import java.util.Optional;

public class TaskDAO extends AbstractDAO<Task> {
    public TaskDAO(SessionFactory factory) {
        super(factory);
    }

    public List<Task> findAll() {
        final CriteriaQuery<Task> criteria = criteriaQuery();
        return list(criteria.select(criteria.from(getEntityClass())));
    }

    public Optional<Task> findById(int id) {
        return Optional.ofNullable(get(id));
    }

    public Task create(Task task) {
        return persist(task);
    }

    public void delete(Task task) {
        currentSession().delete(task);
    }
}
