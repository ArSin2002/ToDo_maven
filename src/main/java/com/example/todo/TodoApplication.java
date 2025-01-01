package com.example.todo;

// import com.example.todo.resources.TaskResource;

// import org.hibernate.SessionFactory;

// import com.example.todo.TaskConfiguration;
// import com.example.todo.db.TaskDAO;
// import com.example.todo.models.Task;

// import io.dropwizard.Application;
// import io.dropwizard.Configuration;
// import io.dropwizard.db.DataSourceFactory;
// import io.dropwizard.hibernate.HibernateBundle;
// import io.dropwizard.setup.Environment;

// public class TodoApplication extends Application<Configuration> {
//     public static void main(String[] args) throws Exception {
//         new TodoApplication().run(args);
//     }
//     @Override
//     public void run(Configuration configuration, Environment environment) {
//         final SessionFactory sessionFactory = HibernateBundle.getSessionFactory();
//         final TaskDAO taskDAO = new TaskDAO(sessionFactory);
//         environment.jersey().register(new TaskResource(taskDAO));
// }
// }

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import com.example.todo.models.Task;
import com.example.todo.db.TaskDAO;
import com.example.todo.resources.TaskResource;

public class TodoApplication extends Application<TaskConfiguration> {

    // HibernateBundle instance to configure Hibernate
    private final HibernateBundle<TaskConfiguration> hibernateBundle =
        new HibernateBundle<TaskConfiguration>(Task.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(TaskConfiguration configuration) {
                return configuration.getDataSourceFactory(); // Get the DB configuration
            }
        };

    
        @Override
        public void initialize(Bootstrap<TaskConfiguration> bootstrap) {
            // Initialize HibernateBundle
            bootstrap.addBundle(new MigrationsBundle<TaskConfiguration>() {
                @Override
                public DataSourceFactory getDataSourceFactory(TaskConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            });
            bootstrap.addBundle(hibernateBundle);
        }
    @Override
    public void run(TaskConfiguration configuration, Environment environment) {
        // Initialize Hibernate SessionFactory
        final SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
        
        // Create DAO with the SessionFactory
        final TaskDAO taskDAO = new TaskDAO(sessionFactory);
        
        // Register TaskResource to handle API requests
        environment.jersey().register(new TaskResource(taskDAO));
    }

    public static void main(String[] args) throws Exception {
        new TodoApplication().run(args);
    }
}
