# Project Name: EuroTreino API

A RESTful API built with Java and Spring Boot to create and manage periodized workout routines. The focus is on performance, clarity, and backend best practices.

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
- Mockito

---

## 🧭 Domain Model (UML)

Below is the current UML diagram representing the core domain of the API:

![UML Diagram](./docs/uml-eurotreino.png)
                               |


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

- First time creating Global Exception Handling with hierarchy.
- Understanding the link between `AppException`, `NotFoundException`, `ErrorResponse`, and `GlobalExceptionHandler`.
- Understanding how `GlobalExceptionHandler` and Logger work together when exceptions are thrown from a Service class.

- Refactoring the `MuscularGroup` enum to avoid database breaks by assigning explicit integer values to each constant — ensuring DB consistency and flexibility for future features.

- Refactoring the service layer logic to simplify responsibilities. Created only `UserService` and `TrainingService`, which automatically handle the creation of `Macro`, `Meso`, and `Micro` when adding a new `Workout` to a user.

- Implementing URI building best practices using `UriComponentsBuilder.fromPath().buildAndExpand().toUri()` to generate response locations dynamically.

- Requested AI assistance to generate an initial list of exercises for database seeding.

- Learned how to initialize the database by creating a `config` package with a DataLoader class to populate the initial data at application startup.

- Adjusted HTTP method in `TrainingController` from `PUT` to `POST` to correctly represent resource creation semantics.

- Added dependency injection in `TrainingService` using the `@Autowired` annotation to improve testability and follow Spring best practices.

- Changed HTTP response of `addWorkout` from `NO_CONTENT` to `CREATED` with a message body ("Treino criado com sucesso") to provide better feedback to the client.

- Added new endpoints to visualize workouts and calculate total workout volume.

- Fixed persistence issues caused by unidirectional relationships. The database was not saving data on both sides, so created helper methods (`addMacro`, `addMeso`, `addMicro`, `addWorkout`) to ensure associations are handled consistently and persisted correctly.
---
## 🤝 Contact

If you'd like to learn more about this project or collaborate with me:

- GitHub: [Victor-f-Paiva](https://github.com/Victor-f-Paiva)
- LinkedIn: [Victor Paiva](https://www.linkedin.com/in/victor-paiva-b4392ab7/)
