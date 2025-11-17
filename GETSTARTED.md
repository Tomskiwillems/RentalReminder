# Getting Started with Java 21 Spring Boot Template

## What is This?

This is a **template repository** - a boilerplate starter project for building Spring Boot applications with Supabase integration. It's designed to be used as a starting point for new projects, not as a project you contribute to directly.

**Important:** This repository is read-only. You cannot and should not push changes to this template. Instead, you'll create your own copy to work with.

## How to Use This Template

There are two ways to get started with this template:

### Option 1: Use GitHub's Template Feature (Recommended)

This is the easiest way to create your own project from this template:

1. **Click the "Use this template" button** on the GitHub repository page
   - You'll find this button at the top right of the repository page
   - Select "Create a new repository"

2. **Configure your new repository:**
   - Choose a name for your new repository (e.g., `my-awesome-app`)
   - Select the owner (your GitHub account or organization)
   - Choose visibility (public or private)
   - **Do NOT** initialize with README, .gitignore, or license (this template already includes them)

3. **Click "Create repository"** - GitHub will create a new repository with all the template code

4. **Clone your new repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
   cd YOUR_REPO_NAME
   ```

### Option 2: Clone and Create Your Own Repository

If you prefer to clone directly:

1. **Clone this template:**
   ```bash
   git clone https://github.com/blitz-boilerplate/java-21-springboot-template.git
   cd java-21-springboot-template
   ```

2. **Remove the existing git history:**
   ```bash
   rm -rf .git
   ```

3. **Initialize a new git repository:**
   ```bash
   git init
   git add .
   git commit -m "Initial commit from template"
   ```

4. **Create a new repository on GitHub** (via web interface or `gh` CLI):
   ```bash
   gh repo create YOUR_REPO_NAME --public --source=. --remote=origin --push
   ```

## Next Steps After Creating Your Repository

Once you have your own copy of the template, follow these steps:

### Step 1: Rename the Application Class

The template includes a generic application class name that you should rename to match your project:

1. **Rename the main application class:**
   - Rename `Java21SpringbootTemplateApplication.java` to `<YourProjectName>Application.java`
   - Update the class name inside the file:
   ```java
   public class YourProjectNameApplication {
       public static void main(String[] args) {
           SpringApplication.run(YourProjectNameApplication.class, args);
       }
   }
   ```

2. **Rename the test class:**
   - Rename `Java21SpringbootTemplateApplicationTests.java` to `<YourProjectName>ApplicationTests.java`
   - Update the class name inside the file

3. **Update `pom.xml`:**
   - Change `<artifactId>` to your project name
   - Update `<name>` and `<description>` if desired

4. **Update `application.yaml`:**
   - Change `spring.application.name` to your project name

### Step 2: Configure Supabase

1. **Get your Supabase credentials:**
   - Sign up at [supabase.com](https://supabase.com) if you haven't already
   - Create a new project
   - Go to Project Settings → API to find your URL and anon key
   - Go to Project Settings → Database to find your database connection details

2. **Update `application.yaml`:**
   - Set `supabase.url` - Your Supabase project URL
   - Set `supabase.anon-key` - Your Supabase anonymous key
   - Configure database connection settings:
     - `SUPABASE_DB_HOST` - Your database host
     - `SUPABASE_DB_PORT` - Usually 5432
     - `SUPABASE_DB_NAME` - Usually "postgres"
     - `SUPABASE_DB_USER` - Your database user
     - `SUPABASE_DB_PASSWORD` - Your database password

   You can set these as environment variables or directly in the `application.yaml` file.

### Step 3: Set Up the Database

1. **Create the `user_profiles` table** in your Supabase database:
   ```sql
   CREATE TABLE user_profiles (
       supabase_user_id UUID PRIMARY KEY,
       email VARCHAR(255) NOT NULL UNIQUE,
       display_name VARCHAR(255),
       created_at TIMESTAMP NOT NULL,
       updated_at TIMESTAMP
   );
   ```

   Or use Supabase's SQL Editor to run this migration.

### Step 4: Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## Understanding the Template Structure

This template includes:

- **SupabaseClient** - REST client for Supabase authentication (register/login)
- **UserProfile Entity** - Example entity showing how to link your data with Supabase Auth
- **SecurityConfig** - CORS configuration (allows all traffic for learning purposes)
- **Standard folder structure** - Controller, Service, Repository, Entity, DTO layers

See the [README.md](README.md) for more details about the project structure and features.

## Important Notes

- **This is a template** - Don't try to contribute changes back to this repository
- **Create your own repository** - Always work from your own copy
- **Security warning** - The SecurityConfig allows all traffic. This is intentional for learning, but you should secure it before production use
- **Package names** - You can keep the default package name or refactor it to match your organization

## Need Help?

- Check the [README.md](README.md) for more detailed information
- Review the inline code comments for guidance
- Consult the [Spring Boot documentation](https://spring.io/projects/spring-boot)
- Check the [Supabase documentation](https://supabase.com/docs)

## Contributing

**Do not contribute to this template repository.** This is a read-only template. If you have improvements or suggestions, please create an issue in your own fork or contact the template maintainers.

