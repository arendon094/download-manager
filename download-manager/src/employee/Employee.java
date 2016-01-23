package employee;

public class Employee {

	private int id;
	private String lastname;
	private String hireDate;
	private String birthDate;
	private String sex;
	private String jobStatus;
	private String payType;
	private double annualSalary;
	private int yearsService;
	
	public Employee(int id, String lastname, String hireDate, String birthDate, 
			String sex, String jobStatus, String payType, double annualSalary,
			int yearsService) {
		this.id = id;
		this.lastname = lastname;
		this.hireDate = hireDate;
		this.birthDate = birthDate;
		this.sex = sex;
		this.jobStatus = jobStatus;
		this.payType = payType;
		this.annualSalary = annualSalary;
		this.yearsService = yearsService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public double getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(double annualSalary) {
		this.annualSalary = annualSalary;
	}

	public int getYearsService() {
		return yearsService;
	}

	public void setYearsService(int yearsService) {
		this.yearsService = yearsService;
	}
	
	
	
	
}
