package sample.sms.pract.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import sample.sms.pract.entity.Student;
import sample.sms.pract.service.StudentService;

@Controller
// we need to create this class as controller annotation because to make this class as a spring mvc class to handle the request
public class StudentController {
	// to create feature 
	
	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	//handler method to handle list students request and return model and view
	@GetMapping("/students")
	public String listStudent(Model model) {
		
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
		
	}
	@GetMapping("/students/new")
	 public String CreateStudentForm(Model model) {
		 //creating object tohold student form data 
		 Student s1=new Student();
		 model.addAttribute("student",s1);
		return "create_student";
		 
	 }
		
		  @PostMapping("/students") 
		  public String saveStudent(@ModelAttribute("student") Student student) {
		 studentService.saveStudent(student); 
		  return "redirect:/students";
		  
		  }
		 //add path variable annotation to get the id it is long type and we need to return some data to the view so we need model
	@GetMapping("/students/update/{id}")
	 public String updatestudentform(@PathVariable Long id, Model model) {
		 //creating object to hold student form data 
		// we need to get student id from the database for that we need to add method to the student service
		model.addAttribute("student",studentService.getStudentById(id));
		return "update_student";
		 
	 }
	
	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,@ModelAttribute("student") Student student, Model model) {
		 // get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		//save uploaded student objects
		studentService.updateStudent(existingStudent);
		return "redirect:/students";
		
	}
	 @GetMapping("/students/delete")
	 public String deletestudent(Model model){
		return "delete_student";
		 
		 
	 }
}
