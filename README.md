# Java 21 Spring Boot Template

A boilerplate template for Spring Boot applications with Supabase integration.

> **📖 New to this template?** Start with [GETSTARTED.md](GETSTARTED.md) for instructions on how to clone this template and create your own repository. This is a read-only template - you should not push changes to this repository.

## Features

- **Java 21** with Spring Boot 3.5.7
- **Supabase Authentication** integration via REST client
- **PostgreSQL** database connectivity
- **CORS** enabled for all origins
- Standard project structure: Controller, Service, Repository, Entity, DTO layers
- **UserProfile entity and repository** - Pre-configured example showing how to link your database entities with Supabase Auth

## Project Structure

```
src/main/java/nl/blitz/java21springboottemplate/
├── config/
│   └── SecurityConfig.java          # CORS configuration
├── controller/                      # REST controllers
├── service/                         # Business logic
├── repository/
│   └── UserProfileRepository.java   # User profile data access
├── entity/
│   └── UserProfile.java             # User profile entity (linked to Supabase Auth)
├── dto/                             # Data transfer objects
└── client/
    └── SupabaseClient.java          # Supabase REST client
```

## Configuration

Before running the application, configure the following in `application.yaml`:

- `supabase.url` - Your Supabase project URL
- `supabase.anon-key` - Your Supabase anonymous key
- Database connection settings for PostgreSQL

## Getting Started

### Step 1: Clone from GitHub Template

When using this as a GitHub template:

1. Click "Use this template" on GitHub, or
2. Clone the repository:
```bash
git clone <repository-url>
cd <your-project-name>
```

### Step 2: Rename the Application Class

After cloning, you'll need to rename the main application class to match your project name.

**Example:** If your project is called "my-awesome-app", rename to `MyAwesomeAppApplication`

1. **Rename the Java class file:**
   - Rename `Java21SpringbootTemplateApplication.java` to `<YourProjectName>Application.java`
   - Update the class name inside the file:
   ```java
   public class <YourProjectName>Application {
       public static void main(String[] args) {
           SpringApplication.run(<YourProjectName>Application.class, args);
       }
   }
   ```

2. **Update the test class:**
   - Rename `Java21SpringbootTemplateApplicationTests.java` to `<YourProjectName>ApplicationTests.java`
   - Update the class name inside the file:
   ```java
   class <YourProjectName>ApplicationTests {
   ```

3. **Update `pom.xml`:**
   - Change the `<artifactId>` to your project name (e.g., `my-awesome-app`)
   - Update `<name>` and `<description>` if desired

4. **Update `application.yaml`:**
   - Change `spring.application.name` to your project name (e.g., `my-awesome-app`)

**Note:** The package name (`nl.blitz.java21springboottemplate`) can remain unchanged, or you can refactor it to match your organization. If you change the package name, you'll need to update all Java files and move them to the new package structure.

### Step 3: Configure Supabase

1. Configure your Supabase credentials in `application.yaml`:
   - Set `supabase.url` - Your Supabase project URL
   - Set `supabase.anon-key` - Your Supabase anonymous key
   - Configure database connection settings

### Step 4: Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## UserProfile Entity

This template includes a `UserProfile` entity and `UserProfileRepository` as a learning example. 

**Why is this included?**

When building applications with Supabase Auth, you'll often need to store additional user information beyond what Supabase's authentication system provides. The `UserProfile` entity demonstrates how to:

- Link your application's user data to Supabase's authentication system using the `supabaseUserId` field
- Store additional profile information (email, display name, etc.) in your own database
- Query user profiles after authentication using the user ID from Supabase's auth token

**How it works:**

1. When a user registers via Supabase Auth, Supabase returns a user object with a UUID `id`
2. You can create a `UserProfile` record using that UUID as the `supabaseUserId`
3. Later, when the user authenticates, you can retrieve their profile using `findBySupabaseUserId()`

This pattern helps students understand how to bridge Supabase's authentication system with their application's data layer.

## Dependencies

- Spring Boot Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Security (for CORS configuration)

