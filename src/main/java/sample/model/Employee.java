package sample.model;
public class Employee {
   private Integer id;
   private String name;
   private String department;
   private String position;
   
   
   public Employee() {
   }

   public Employee(Integer id, String name, String department, String position) {
       this.id = id;
       this.name = name;
       this.department = department;
       this.position = position;
   }
   public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}

}