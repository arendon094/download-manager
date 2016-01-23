package gui;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import employee.DatabaseJDBC;
import employee.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoNode extends VBox{

	private Label idLbl = new Label("ID: ");
	private Label lastnameLbl = new Label("Last Name: ");
	private Label hireDateLbl = new Label("Hire Date: ");
	private Label birthDateLbl = new Label("Birthdate: ");
	private Label sexLbl= new Label("Sex: ");
	private Label jobStatusLbl = new Label("Job Status: ");
	private Label payTypeLbl = new Label("Pay Type: ");
	private Label annualSalaryLbl = new Label("Annual Salary: ");
	private Label yearsServiceLbl = new Label("Years Service: ");
	private TextField idTxt = new TextField();
	private TextField lastnameTxt = new TextField();
	private TextField hireDateTxt = new TextField();
	private TextField birthDateTxt = new TextField();
	private TextField sexTxt = new TextField();
	private TextField jobStatusTxt = new TextField();
	private TextField payTypeTxt = new TextField();
	private TextField annualSalaryTxt = new TextField();
	private TextField yearsServiceTxt = new TextField();
	private DatabaseJDBC db;

	private boolean isCreated;
	private ArrayList<Employee> resultEmp;

	public InfoNode(){}

	public String setIdLbl(String txt) {
		return "ID: " + txt;
	}

	public String getLastnameTxt() {
		return lastnameTxt.getText();
	}

	public String getHireDateTxt() {
		return hireDateTxt.getText();
	}

	public String getBirthDateTxt() {
		return birthDateTxt.getText();
	}

	public String getSexTxt() {
		return sexTxt.getText();
	}

	public String getJobStatusTxt() {
		return jobStatusTxt.getText();
	}

	public String getPayTypeTxt() {
		return payTypeTxt.getText();
	}

	public String getAnnualSalaryTxt() {
		return annualSalaryTxt.getText();
	}

	public String getYearsServiceTxt() {
		return yearsServiceTxt.getText();
	}

	public boolean isValid() {
		return isCreated;
	}

	public Node createEditForm(){

		Button find = new Button("find");

		HBox h1 = new HBox(idLbl, idTxt,find);

		this.getChildren().add(h1);

		Button editEmp = new Button("EditEmp");
		Button delete = new Button("Delete Emp");

		find.setOnAction(new EventHandler<ActionEvent>(){		
			@Override
			public void handle(ActionEvent e) {

				Employee emp;
				try{
					emp = db.findByKey(idTxt.getText());

					if(emp != null) {
						find.setVisible(false);
						idTxt.setText(Integer.toString(emp.getId()));
						lastnameTxt.setText(emp.getLastname());
						hireDateTxt.setText(emp.getHireDate());
						birthDateTxt.setText(emp.getBirthDate());
						sexTxt.setText(emp.getSex());
						jobStatusTxt.setText(emp.getJobStatus());
						payTypeTxt.setText(emp.getPayType());
						annualSalaryTxt.setText(Double.toString(emp.getAnnualSalary()));
						yearsServiceTxt.setText(Integer.toString(emp.getYearsService()));


						HBox h1 = new HBox(idLbl, idTxt, lastnameLbl, lastnameTxt, hireDateLbl, hireDateTxt);
						HBox h2 = new HBox(birthDateLbl, birthDateTxt, sexLbl, sexTxt, jobStatusLbl, jobStatusTxt);
						HBox h3 = new HBox(payTypeLbl, payTypeTxt, annualSalaryLbl, annualSalaryTxt, yearsServiceLbl, yearsServiceTxt);

						idTxt.setDisable(true);
						h1.setSpacing(20);
						h2.setSpacing(20);
						h3.setSpacing(20);

						InfoNode.this.setSpacing(20);
						InfoNode.this.getChildren().add(h1);
						InfoNode.this.getChildren().add(h2);
						InfoNode.this.getChildren().add(h3);
						InfoNode.this.getChildren().add(editEmp);
						InfoNode.this.getChildren().add(delete);

						InfoNode.this.setPadding(new Insets(20, 20, 20, 20));
					}
				}
				catch(Exception e1){
					System.out.println("cannot locate");
				}
			}
		});

		editEmp.setOnAction(new EventHandler<ActionEvent>(){		
			@Override
			public void handle(ActionEvent e) {
				validateInfo();

				if(isCreated){
					System.out.println("is valid we can change");
					Employee emp = new Employee(Integer.parseInt(idTxt.getText()), getLastnameTxt(), getHireDateTxt(),
							getBirthDateTxt(), getSexTxt(), getJobStatusTxt(), getPayTypeTxt(),
							Double.parseDouble(getAnnualSalaryTxt()), Integer.parseInt(getYearsServiceTxt()));

					db.update(emp);

					idTxt.setText("");
					lastnameTxt.setText("");
					hireDateTxt.setText("");
					birthDateTxt.setText("");
					sexTxt.setText("");
					jobStatusTxt.setText("");
					payTypeTxt.setText("");
					annualSalaryTxt.setText("");
					yearsServiceTxt.setText("");	

					//center.getChildren().removeAll(addLabel, addNode);
					System.out.println("sent to DB");


				}
				System.out.println("submitted");

			}
		});

		delete.setOnAction(new EventHandler<ActionEvent>(){		
			@Override
			public void handle(ActionEvent e) {

				validateInfo();

				if(isCreated){
					System.out.println("is valid we can change");
					Employee emp = new Employee(Integer.parseInt(idTxt.getText()), getLastnameTxt(), getHireDateTxt(),
							getBirthDateTxt(), getSexTxt(), getJobStatusTxt(), getPayTypeTxt(),
							Double.parseDouble(getAnnualSalaryTxt()), Integer.parseInt(getYearsServiceTxt()));

					db.delete(idTxt.getText());

					idTxt.setText("");
					lastnameTxt.setText("");
					hireDateTxt.setText("");
					birthDateTxt.setText("");
					sexTxt.setText("");
					jobStatusTxt.setText("");
					payTypeTxt.setText("");
					annualSalaryTxt.setText("");
					yearsServiceTxt.setText("");	

					System.out.println("sent to DB");


				}
				System.out.println("submitted");

			}
		});


		return this;

	}

	public Node createAddForm(){

		HBox h1 = new HBox(lastnameLbl, lastnameTxt, hireDateLbl, hireDateTxt);
		HBox h2 = new HBox(birthDateLbl, birthDateTxt, sexLbl, sexTxt, jobStatusLbl, jobStatusTxt);
		HBox h3 = new HBox(payTypeLbl, payTypeTxt, annualSalaryLbl, annualSalaryTxt, yearsServiceLbl, yearsServiceTxt);
		Button addEmp = new Button("Add Emp");

		payTypeLbl.setWrapText(true);
		annualSalaryLbl.setWrapText(true);
		yearsServiceLbl.setWrapText(true);


		sexTxt.setPrefWidth(50);
		payTypeTxt.setPrefWidth(50);
		jobStatusTxt.setPrefWidth(50);
		yearsServiceTxt.setPrefWidth(50);


		h1.setSpacing(20);
		h2.setSpacing(20);
		h3.setSpacing(20);

		this.setSpacing(20);
		this.getChildren().add(h1);
		this.getChildren().add(h2);
		this.getChildren().add(h3);
		this.getChildren().add(addEmp);
		this.setPadding(new Insets(20, 20, 20, 20));

		addEmp.setOnAction(new EventHandler<ActionEvent>(){		
			@Override
			public void handle(ActionEvent e) {

				validateInfo();
				if(isCreated){
					System.out.println("we are about to add employee");
					Employee addEmp = new Employee(db.getNextEmpId(), getLastnameTxt(), getHireDateTxt(),
							getBirthDateTxt(), getSexTxt(), getJobStatusTxt(), getPayTypeTxt(),
							Double.parseDouble(getAnnualSalaryTxt()), Integer.parseInt(getYearsServiceTxt()));
					db.insert(addEmp);

					idTxt.setText("");
					lastnameTxt.setText("");
					hireDateTxt.setText("");
					birthDateTxt.setText("");
					sexTxt.setText("");
					jobStatusTxt.setText("");
					payTypeTxt.setText("");
					annualSalaryTxt.setText("");
					yearsServiceTxt.setText("");	

					//center.getChildren().removeAll(addLabel, addNode);
					System.out.println("inserted employee");


				}
				System.out.println("submitted");

			}
		});



		return this;
	}

	public Node createSearchForm(){

		Button submit = new Button("Submit");
		TextField ageRangeTxt = new TextField();

		CheckBox idCB = new CheckBox("ID");
		CheckBox lastnameCB = new CheckBox("Last Name");
		CheckBox sexCB = new CheckBox("Sex");
		CheckBox jobStatusCB = new CheckBox("Job Status");
		CheckBox payTypeCB = new CheckBox("Pay Type");
		CheckBox birthRangeCB = new CheckBox("Birthdate");
		CheckBox ageRangeCB = new CheckBox("Age");
		CheckBox yearsRangeCB = new CheckBox("Years Service");
		CheckBox hireRangeCB = new CheckBox("Hiredate");
		CheckBox annualRangeCB = new CheckBox("Annual Salary");
		CheckBox sameLastnameCB = new CheckBox("Same Last Names");


		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"=",
						">",
						"<");

		ComboBox<String> salaryCombo = new ComboBox<String>(options);
		ComboBox<String> birthCombo = new ComboBox<String>(options);

		ComboBox<String> ageCombo = new ComboBox<String>(options);
		ComboBox<String> hireCombo = new ComboBox<String>(options);
		ComboBox<String> yearsCombo = new ComboBox<String>(options);

		HBox idBox = new HBox(idCB, idTxt);
		HBox lastnameBox = new HBox(lastnameCB, lastnameTxt, sameLastnameCB);
		HBox sexBox = new HBox(sexCB, sexTxt);
		HBox jobStatusBox = new HBox(jobStatusCB, jobStatusTxt);
		HBox payTypeBox = new HBox(payTypeCB, payTypeTxt);
		HBox birthRangeBox = new HBox(birthRangeCB, birthCombo, birthDateTxt);
		HBox ageRangeBox = new HBox(ageRangeCB, ageCombo, ageRangeTxt);

		HBox hireRangeBox = new HBox(hireRangeCB, hireCombo, hireDateTxt);
		HBox yearsRangeBox = new HBox(yearsRangeCB, yearsCombo, yearsServiceTxt);
		HBox annualRangeBox = new HBox(annualRangeCB,salaryCombo, annualSalaryTxt);

		sexBox.setSpacing(20);
		ageRangeBox.setSpacing(20);
		idBox.setSpacing(20);
		lastnameBox.setSpacing(20);
		jobStatusBox.setSpacing(20);
		payTypeBox.setSpacing(20);
		birthRangeBox.setSpacing(20);
		hireRangeBox.setSpacing(20);
		yearsRangeBox.setSpacing(20);
		annualRangeBox.setSpacing(20);

		this.getChildren().addAll(idBox, lastnameBox, sexBox, jobStatusBox, payTypeBox
				, birthRangeBox, ageRangeBox, hireRangeBox, yearsRangeBox, annualRangeBox, submit);

		payTypeLbl.setWrapText(true);
		annualSalaryLbl.setWrapText(true);
		yearsServiceLbl.setWrapText(true);
		sexTxt.setPrefWidth(50);
		payTypeTxt.setPrefWidth(50);
		jobStatusTxt.setPrefWidth(50);
		yearsServiceTxt.setPrefWidth(50);

		this.setPadding(new Insets(20, 20, 20, 20));

		submit.setOnAction(new EventHandler<ActionEvent>(){		
			@Override
			public void handle(ActionEvent e) {

				ArrayList<String> selectedData = new ArrayList<String>();

				if(sameLastnameCB.isSelected()){
					selectedData.add("lastname in(select lastname from employee11 group by lastname having count(lastname) > 1)");
				}

				if(idCB.isSelected()){
					selectedData.add("emp_id = " + idTxt.getText());
				}
				if(lastnameCB.isSelected() && !lastnameTxt.getText().equals("")){

					selectedData.add("lastname = " + "'" + lastnameTxt.getText() + "'");

				}
				if(birthRangeCB.isSelected()){
					selectedData.add("birthdate" + birthCombo.getSelectionModel().getSelectedItem() + "'" + birthDateTxt.getText() + "'");

				}
				if(ageRangeCB.isSelected()){

					Calendar calendar = Calendar.getInstance();
					String operation = ageCombo.getSelectionModel().getSelectedItem();
					int year = calendar.get(Calendar.YEAR) - Integer.parseInt(ageRangeTxt.getText());
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					int month = calendar.get(Calendar.MONTH) + 1;


					if(operation.equals("=")){
						selectedData.add("birthdate>" + "'" + year + "-" + month + "-" + day+ "'");

					}

					else if(operation.equals(">")){
						selectedData.add("birthdate<" + "'" + year + "-" + month + "-" + day+ "'");
					}

					else if (operation.equals("<")){
						selectedData.add("birthdate>" + "'" + year + "-" + month + "-" + day+ "'");
					}				
				}


				if(hireRangeCB.isSelected()){
					selectedData.add("hiredate" + hireCombo.getSelectionModel().getSelectedItem()+ "'" + hireDateTxt.getText()+ "'");
				}

				if(annualRangeCB.isSelected()){
					selectedData.add("annual_salary" + salaryCombo.getSelectionModel().getSelectedItem() +  annualSalaryTxt.getText());
				}

				if(yearsRangeCB.isSelected()){
					selectedData.add("years_service" + yearsCombo.getSelectionModel().getSelectedItem() + yearsServiceTxt.getText());
				}

				if(sexCB.isSelected()){
					selectedData.add("sex = " + "'" + sexTxt.getText() + "'");
				}

				if(jobStatusCB.isSelected()){
					selectedData.add("job_status = " + "'"+ jobStatusTxt.getText()+ "'");
				}

				if(payTypeCB.isSelected()){
					selectedData.add("pay_type = " + "'"+ payTypeTxt.getText() + "'");
				}

				resultEmp = db.findAll(selectedData);

				idTxt.setText("");
				lastnameTxt.setText("");
				hireDateTxt.setText("");
				birthDateTxt.setText("");
				sexTxt.setText("");
				jobStatusTxt.setText("");
				payTypeTxt.setText("");
				annualSalaryTxt.setText("");
				yearsServiceTxt.setText("");	
				ageRangeTxt.setText("");
				idCB.setSelected(false);
				lastnameCB.setSelected(false);
				sexCB.setSelected(false);
				jobStatusCB.setSelected(false);
				payTypeCB.setSelected(false);
				birthRangeCB.setSelected(false);
				ageRangeCB.setSelected(false);
				yearsRangeCB.setSelected(false);
				hireRangeCB.setSelected(false);
				annualRangeCB.setSelected(false);
				sameLastnameCB.setSelected(false);



			}
		});



		return this;
	}

	public ArrayList<Employee> getResultEmp() {

		return this.resultEmp;

	}

	public void validateInfo() {

		isCreated = false;
		if(this.getLastnameTxt() != "" && this.getHireDateTxt() != ""
				&& this.getBirthDateTxt() != "" && this.getSexTxt() != "" && this.getJobStatusTxt() != ""
				&& this.getPayTypeTxt() != "" && this.getAnnualSalaryTxt() != "" && this.getYearsServiceTxt() != ""){


			try{
				//Integer.parseInt(this.getIdTxt());
				Integer.parseInt(this.getYearsServiceTxt());
				Double.parseDouble(this.getAnnualSalaryTxt());
				isCreated = true;
				System.out.println("info valid");
			}
			catch(Exception e){
				isCreated = false;
			}
		}

	}
}

