package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import objects.Client;
import objects.ClientContact;
import objects.ClientContactPerson;
import objects.Employee;
import objects.EmployeeContact;
import objects.Service;
import objects.ServiceType;
import objects.Task;

public class HibernateSessionFactory {

  public static SessionFactory factory;

  private HibernateSessionFactory() {
  }

  public static SessionFactory getSessionFactory() {

    if (factory == null) {
      try {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(ClientContact.class);
        configuration.addAnnotatedClass(ClientContactPerson.class);
        configuration.addAnnotatedClass(Service.class);
        configuration.addAnnotatedClass(ServiceType.class);
        configuration.addAnnotatedClass(Task.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(EmployeeContact.class);
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties());
        factory = configuration.buildSessionFactory(registryBuilder.build());
      } catch (Exception e) {
        System.err.println(e);
      }
    }
    return factory;
  }
}
