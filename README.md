# trabajoUY

**trabajoUY** is a project developed as part of the **Programming Workshop** course (Facultad de Ingeniería, Universidad de la República, 2023). 
It was developed by Ignacio Alesina, Joel Cabrera, Matias Marin, Luciana Munhos and Manuel Roca.

It is a job search platform where:
- Companies can post job offers and hire people.
- Users can create their profiles, apply to jobs, and manage their applications.
- Social features are included: users can add friends and see their activity.

---

## Main Features
- User management (applicants and companies)  
- Personal and company profiles  
- Job posting and application management  
- Social network features  
- Data persistence with a relational database  
- Object-oriented design and UML  
- Design patterns applied  
- User authentication and security  
- User interfaces: desktop (Swing) and web  

---

## Technologies Used
The project combines advanced software engineering concepts:

- Language and paradigm: Java, object-oriented programming  
- Interfaces: Swing (desktop) and web technologies  
- Architecture: client/server with remote communication  
- Persistence: relational databases (JPA / SQL)  
- Design and quality: UML, design patterns, software quality attributes  
- Security: user authentication  
- Version control: Git and Maven for build and dependency management  

---

## Project Structure

The project is organized in two main modules:

1. **swing_trabajouy**  
   Desktop application using Java Swing, handling users, job offers, and applications. Includes persistence logic, a local server for companies to manage postings, and unit tests.

2. **trabajouy**  
   Web application and central backend in Java. Manages user and company profiles, applications, and remote communication. Includes persistence, security, and is organized with Maven for building and dependencies.

> This structure allows combining a desktop experience for users and companies with a centralized web server managing the system logic.
