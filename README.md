# Project Name: EuroTreino API

A RESTful API built with Java and Spring Boot to manage periodized workout routines. The focus is on performance, clarity, and backend best practices.

---

## 📌 Project Purpose

The goal of this project is to develop an API to register workouts, training cycles (macro, meso, micro), exercises, training sessions, and calculate metrics such as muscle group volume. It serves as practice for domain modeling, JPA relationships, and efficient aggregate calculations.

---

## 🚀 Technologies Used

- Java 21 (Zulu JDK)
- Spring Boot
- Maven
- PostgreSQL
- JPA / Hibernate
- Lombok
- JUNIT

---

## 🧭 Domain Model (UML)

Below is the current UML diagram representing the core domain of the API:

![UML Diagram](./docs/uml-eurotreino.png)

---

## 🧱 Development Progress

| Milestone                                | Description / Challenge                                                                                                                                        | Solution or Lesson Learned                                                                                                                                                                                                                                                                                                            |
| ---------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Initial project setup                    | Created the UML diagram, the PostgreSQL database, defined the internal package structure, configured `application.properties`, and set up `.gitignore`         | The UML structure was initially sketched on paper, then recreated using PlantUML. The database was created using pgAdmin4. The package structure was defined in Eclipse IDE. Sensitive credentials were moved to an `application.properties.example` file and excluded using `.gitignore`, along with unnecessary files.              |
| Entity modeling                          | Created entities `MuscularGroup`, `Exercise`, and `Cycle` to represent the core training logic                                                                 | Used `@Enumerated(EnumType.STRING)` for better database readability, based on community best practices and documentation recommendations.                                                                                                                                                                                             |
| Modeling `Workout` and `Micro`           | Faced challenges using `@OneToMany` and `@ManyToOne` annotations, especially for managing sets, reps, and weight lifted to calculate training volume correctly | After studying official documentation, the entity mapping issues were resolved. To properly handle training data, the UML diagram was updated and two new entities were added: `Set` (storing order, reps, and weight) and `ExerciseSession` (containing the exercise and its list of sets, including the method `getTotalVolume()`). |
| Understanding Set <-> ExerciseSession    | Understanding why the `Set` class needed a `@OneToMany List<ExerciseSession> exercises` relationship                                                           | This bidirectional mapping ensures that it's possible to identify which sessions use a particular set, which is useful for tracking training history or safely removing entities while preserving consistency.                                                                                                                        |
| Unit test for `getHistoricGroupVolume()` | Writing a unit test for the method in the `Meso` class that returns training volume for a specific muscle group across all `Micro` cycles                      | First, the JUnit 5 dependency was added to the project. Then, the test skeleton was implemented using mocks to simulate related objects (like `Micro`) and validate the expected result.                                                                                                                                              |
| Learning JUnit 5 basics                  | Learning how to write and run tests using JUnit, creating a test skeleton, and understanding the red (fail) and green (pass) feedback cycle                    | Used the standard test structure: the `@Test` annotation, `assertEquals`, and created real or mock objects to validate expected behavior. This helped confirm the correctness of key business logic.                                                                                                                                  |
| Connecting Micro <-> Meso                | Added a constructor and annotated `@ManyToOne` in the `Micro` class to link it to the `Meso` class                                                             | This allowed the periodization structure to be properly represented in the database, and made the relationship navigable in the code, enabling each `Micro` to reference its corresponding `Meso` instance.                                                                                                                           |
| Fianl Entity modeling and Unit testing for Macro and User classes  | Created Macro and User classes, unit tests for the `Macro` and `User` classes to verify constructors and getter methods.                         | Constructors and basic getters were tested successfully using JUnit. Confirmed that `getName()` from `User`, `getInitialDate()` from `Macro`, and `getGroupVolume(group)` from `Micro` all return the expected values. Helped validate domain logic without relying on persistence.                                |


---

## ⚠️ Challenges Faced

- Understanding bidirectional relationships in JPA
- Avoiding infinite JSON serialization loops with `@OneToMany` / `@ManyToOne`
- Handling JSON serialization issues caused by infinite loops (`@JsonManagedReference`, `@JsonBackReference`)
- Structuring packages (e.g., `entity`, `repository`, `service`, `controller`) in a clean and scalable way
- Deciding where to place logic: entity vs. service
- Designing a flexible and clear database schema
- Understanding why the Set class needed `@OneToMany List<ExerciseSession> exercises`
- Writing a unit test for the method `getHistoricGroupVolume(MuscularGroup group)` in the `Meso` class
- Added the `@ManyToOne` constructor in the `Micro` class, establishing the correct relationship between `Micro` and `Meso`

---

## 🛠️ Next Steps

- [ ] Create CRUD endpoints for all main entities
- [ ] Add DTOs and mappers to separate concerns

---

## 🤝 Contact

If you'd like to learn more about this project or collaborate with me:

- GitHub: [Victor-f-Paiva](https://github.com/Victor-f-Paiva)
- LinkedIn: [Victor Paiva](https://www.linkedin.com/in/victor-paiva-b4392ab7/)
