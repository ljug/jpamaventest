package lb.edu.isae.testjpaeclipselink.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import lb.edu.isae.testjpaeclipselink.domain.Employee;
import lb.edu.isae.testjpaeclipselink.domain.Department;

public class JpaTest {

	private final EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		
		test.createEmployees();
                test.createEmployee("pascal", "Info");
                test.createEmployee("titi", "java cycle C");
		
		tx.commit();

		test.listEmployees();
                test.listDepartements();

		System.out.println(".. done");
	}


        private void createEmployee(String nom, String dep) {
            Department department;
            try {
            department = manager.createQuery("Select d From Department d where d.name='"+dep+"'", Department.class).getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                department = new Department(dep);
                manager.persist(department);
            }
            manager.persist(new Employee(nom,department));
        }
	private void createEmployees() {
		int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
		if (numOfEmployees == 0) {
			Department department = new Department("java cycle C");
			manager.persist(department);

			manager.persist(new Employee("Pascal E Fares",department));
			manager.persist(new Employee("Etudiant Cnam",department));

		}
	}
        


	private void listEmployees() {
		List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
		System.out.println("num of employess:" + resultList.size());
                resultList.forEach((next) -> {
                    System.out.println("next employee: " + next);
            });
	}
        
        private void listDepartements() {
		List<Department> resultList = manager.createQuery("Select d From  Department d", Department.class).getResultList();
		System.out.println("num of departements:" + resultList.size());
                resultList.forEach((d) -> {
                    System.out.println("next departement: " + d);
            });
	}


}
