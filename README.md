Overview
The School Management System App is a comprehensive solution designed to streamline the management of students, teachers, and administrative tasks within a school. It provides a seamless interface for managing classes, assigning teachers and students, tracking tasks, generating timetables, and more. Built with Java Spring Boot, this system ensures a robust and scalable backend with RESTful APIs for effective integration with frontend applications.

Features

Students Management:
Register and assign students to classes.
Generate unique student numbers based on registration year.
Assign subjects based on class type (Junior or Senior) and category (Science, Art, or Commercial).
View and manage tasks assigned to students.
Retrieve assigned tasks using task ID or class ID.
Mark daily attendance by teachers.

Teachers Management:
Register teachers with detailed information (e.g., name, email, qualifications, etc.).
Assign teachers to one or more classes and subjects.
Track teacher roles (e.g., Head Teacher).
Enable teachers to assign and monitor tasks for students in their classes.
Allow communication between teachers and students for task updates.
Generate and retrieve class attendance reports.

Class Management:
Create and categorize classes as Junior or Senior.
Assign predefined subjects to classes based on their type and category.
Randomly generate timetables for classes by the admin.
Retrieve daily timetables for students and teachers.

Task and Comment Management:
Enable teachers to assign tasks to students and monitor progress.
Task statuses include In Progress, Awaiting Review, and Done.
Integrate a chat system for teachers and students to communicate about tasks.
Map comments to DTOs for better presentation.

Administrative Features:
Admins can generate and manage timetables.
Perform validations for removing teachers or students, ensuring data integrity.
Enable admins to view and manage all users, tasks, classes, and subjects.
Technology Stack
Backend: Java Spring Boot
Database: PostgreSQL 
Tools: Maven, Hibernate, Spring Data JPA
API Integration: RESTful APIs
Testing: JUnit, Mockito
Installation and Setup
Clone the Repository

bash
git clone https://github.com/cressy1/School-management-System.git
cd school-management-system
Configure Database
Update the application.properties file with your database credentials:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/school
spring.datasource.username=root
spring.datasource.password=A12345badamashi
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
Run the Application
Use Maven to build and run the application:

Usage
Admin: Manage users, classes, subjects, and timetables.
Teachers: Assign tasks, mark attendance, and communicate with students.
Students: View tasks, update task statuses, and access timetables.

Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.
Create a new branch: git checkout -b feature-name.
Commit your changes: git commit -m 'Add some feature'.
Push to the branch: git push origin feature-name.
Open a pull request.

Contact
For questions or suggestions, please reach out to:
Email: martgabril@gmail.com
LinkedIn: https://www.linkedin.com/in/martins-agbadamashi
