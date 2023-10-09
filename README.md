# StepByStepBackend

StepByStepBackend is a Kotlin-based backend application designed to manage and authenticate users, record steps, and provide leaderboard functionalities. The application leverages Spring Boot and integrates OAuth2 for user authentication.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Features

- **User Authentication**: Integrated OAuth2 for secure user authentication.
- **Step Recording**: Allows users to record their steps and view their history.
- **Leaderboard**: Displays the top users based on the number of steps recorded.
- **Email Validation**: Utility to validate email addresses strictly.

## Prerequisites

- JDK 11 or higher
- Gradle
- PostgreSQL (or any other relational database)

## Installation

1. **Clone the Repository**:
   \```bash
   git clone https://github.com/Hampster8/StepByStepBackend.git
   \```

2. **Navigate to the Directory**:
   \```bash
   cd StepByStepBackend
   \```

3. **Build the Application**:
   \```bash
   gradle build
   \```

4. **Update Configuration**:
   - Ensure you update the `application.properties` file with your database credentials and other configurations.

5. **Run the Application**:
   \```bash
   gradle bootRun
   \```

## Usage

Once the application is running, you can access the various endpoints provided by the application. For instance:

- `/api/steps` to record steps or view step history.
- `/api/users` to manage user data.

## Testing

To run the tests included in the repository:

\```bash
gradle test
\```

## Contributing

Contributions are welcome! Please read the [CONTRIBUTING.md](CONTRIBUTING.md) for details on how to contribute.

## License

This project is licensed under the MIT License. See the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Thanks to all the contributors who have helped improve this project.
- Special thanks to the Spring Boot community for their comprehensive documentation and support.
