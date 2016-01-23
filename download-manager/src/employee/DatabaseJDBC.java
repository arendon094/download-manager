package employee;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseJDBC {

	Connection connection = null;

	public DatabaseJDBC() {
		uploadTable();
	}


	public Connection createConnection(){

		if(connection == null){
			try {

				String url = "jdbc:mysql://localhost:3306/payroll";
				String username = "root";
				String password = "admin";

				System.out.println("Connecting database...");
				this.connection = DriverManager.getConnection(url, username, password);
				System.out.println("Database connected!");
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return connection;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	}

	public void closeConnection(){
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) { 
			//do nothing
		}
	}

	public void uploadTable() {


		try {


			String url = "jdbc:mysql://localhost:3306/payroll";
			String username = "root";
			String password = "admin";
			Connection connection = null;


			System.out.println("Connecting database...");
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");


			Statement stmt = connection.createStatement();

			try{

				String testForTable = "DROP TABLE IF EXISTS Employee11";
				stmt.executeUpdate(testForTable);
				System.out.println("Table Dropped");

				String table = 
						"CREATE TABLE Employee11(Emp_id int AUTO_INCREMENT , lastname varchar(20), hireDate date, birthdate date,sex varchar(6), "
								+ "job_status varchar(10), pay_type varchar(10), annual_salary double, years_service int, PRIMARY KEY(Emp_id));";
				stmt.executeUpdate(table);
				System.out.println("Table creation process successfully!");

				try{
					String addData = "load data infile 'C:/Users/Mariah/Documents/CS245/FinalProjectWinter2015/Employees.csv' "
							+ "into table employee11 fields terminated by ',' optionally enclosed by '\"' lines terminated "
							+ "by '\n' ignore 1 lines;";

					stmt.executeUpdate(addData);

					System.out.println("data added");
				} catch (Exception e ){
					System.out.println("Cant add data");
				}



			}
			catch(SQLException s){
				System.out.println("Table already exists!");
			}
			connection.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


	public void delete(String string) {

		try{
			System.out.println("trying delete emp in db");
			Statement stmt = connection.createStatement();
			String insertQuery = "DELETE FROM employee11 WHERE emp_id = " + string + ";";
			stmt.executeUpdate(insertQuery);
		}
		catch(Exception e){
			System.out.println("cannot delete");
		}

	}

	public ArrayList<Employee> findAll(ArrayList<String> selectedData) {

		ArrayList<Employee> employee = new ArrayList<Employee>();
		String query = "SELECT * FROM employee11 WHERE ";


		for(int i = 0; i < selectedData.size(); i++){
			
			if(i == selectedData.size() - 1) {
				query = query + selectedData.get(i);

			}
			else {
				query = query + selectedData.get(i) + " AND ";
			}
		}
		
		query = query + ";";
		System.out.println(query);
		
		try{
			Statement stmt = connection.createStatement();
			stmt.executeQuery(query);
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				int empId = rs.getInt("emp_id");
				String lastname = rs.getString("lastname");
				String hiredate = rs.getString("hiredate");
				String birthdate = rs.getString("birthdate");
				String sex = rs.getString("sex");
				String jobstatus = rs.getString("job_status");
				String paytype = rs.getString("pay_type");
				double annualSalary = Double.parseDouble(rs.getString("annual_salary"));
				int yearsService = Integer.parseInt(rs.getString("years_service"));
				employee.add(new Employee(empId, lastname, hiredate, birthdate, sex, 
						jobstatus, paytype, annualSalary, yearsService));
				i++;
			}
			

		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Cant Find Error");
		}
			
		return employee;
	}

	public Employee findByKey(String string) {

		Employee employee = null;

		try{
			System.out.println("trying look for emp in db");
			Statement stmt = connection.createStatement();
			String insertQuery = "SELECT * FROM employee11 WHERE emp_id = " + string + ";";
			ResultSet rs = stmt.executeQuery(insertQuery);

			rs.next();
			int emp = rs.getInt("emp_id");
			String lastname = rs.getString("lastname");
			String hiredate = rs.getString("hiredate");
			String birthdate = rs.getString("birthdate");
			String sex = rs.getString("sex");
			String jobstatus = rs.getString("job_status");
			String paytype = rs.getString("pay_type");
			double annualSalary = Double.parseDouble(rs.getString("annual_salary"));
			int yearsService = Integer.parseInt(rs.getString("years_service"));

			employee = new Employee(emp, lastname, hiredate, birthdate, sex, 
					jobstatus, paytype, annualSalary, yearsService);

			System.out.println(lastname);

		}
		catch(SQLException e){
			System.out.println("Cannot find emp in db");
			e.printStackTrace();
		}

		return employee;
	}

	public int getNextEmpId(){

		int id = 0;

		try{
			Statement stmt = connection.createStatement();
			String getQuery = "SELECT max(emp_id);";
			id = stmt.executeUpdate(getQuery);
			System.out.println(id);
		}
		catch(Exception e) {

		}
		return id;
	}

	public void insert(Employee employee) {

		int id = employee.getId();
		String lastname = employee.getLastname();
		String hireDate = employee.getHireDate();
		String birthDate = employee.getBirthDate();
		String sex = employee.getSex();
		String jobStatus = employee.getJobStatus();
		String payType = employee.getPayType();
		double annualSalary = employee.getAnnualSalary();
		int yearsService = employee.getYearsService();

		try{
			System.out.println("trying to input into db");
			Statement stmt = connection.createStatement();
			String insertQuery = "INSERT INTO employee11 VALUES(" + id + ",'" + lastname + "','" + hireDate + "','" + birthDate
					+ "','" + sex + "','" + jobStatus + "','" + payType + "'," + annualSalary + "," + yearsService + ");";
			stmt.executeUpdate(insertQuery);
			System.out.println("Inserted " + employee.getId());


		}
		catch(SQLException e){
			System.out.println("Cannot add to db");
			e.printStackTrace();
		}

	}

	public void update(Employee employee) {


		int id = employee.getId();
		String lastname = employee.getLastname();
		String hireDate = employee.getHireDate();
		String birthDate = employee.getBirthDate();
		String sex = employee.getSex();
		String jobStatus = employee.getJobStatus();
		String payType = employee.getPayType();
		double annualSalary = employee.getAnnualSalary();
		int yearsService = employee.getYearsService();
		System.out.println("Should be " + lastname);

		try{
			System.out.println("trying to update into db");
			Statement stmt = connection.createStatement();
			String insertQuery = "UPDATE employee11 SET lastname= '" + lastname + "', hireDate='" + hireDate + 
					"' ,birthdate = '" + birthDate + "' ,sex = '" + sex + "' ,job_status = '" + jobStatus + 
					"' ,pay_type = '" + payType + "' ,annual_salary = " + annualSalary + ",years_service = " +
					yearsService + " WHERE emp_id=" + id + ";";
			System.out.println(insertQuery);

			stmt.executeUpdate(insertQuery);
			System.out.println("Inserted " + lastname);


		}
		catch(SQLException e){
			System.out.println("Cannot add to db");
			e.printStackTrace();
		}

	}   




}
