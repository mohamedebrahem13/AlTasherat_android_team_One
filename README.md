AlTasherat
AlTasherat is a user-friendly Android application developed for buying and selling visas. The app streamlines transactions with secure features, making it easier for users to manage their visa transactions efficiently.

Key Features
User Authentication: Secure login and registration process.
Visa Transactions: Facilitates buying and selling of visas.
Secure Features: Data encryption to ensure user information is protected.
Product Flavors: Different configurations for various use cases.
Modern UI/UX: Designed with modern Android development practices.

Technologies Used
Kotlin: The primary language for development.

Retrofit: For handling API requests and responses.

Kotlin Coroutines: For background threading and asynchronous operations.

Flow and StateFlow: To manage data streams and UI states.

MVI (Model-View-Intent): Architecture pattern for managing state and user interactions.

Encryption: To ensure secure data storage and transmission.

Product Flavors: Configurations to support different versions of the app, tailored to various needs or markets.

ViewBinding: For type-safe access to views in the layout files, reducing the need for findViewById calls and improving code safety.

Coil: For efficient image loading and caching.

Data Store: For storing small amounts of key-value data in a type-safe manner, replacing SharedPreferences.

Dagger Hilt: For dependency injection, simplifying the process of providing dependencies throughout the app.

Navigation Graph: For managing app navigation in a declarative way, handling transitions between different screens.

WorkManager: For performing background work, such as fetching countries based on the user's language preference (ar/en).

Dark Theme: Support for a dark theme to improve user experience in low-light environments.

Architecture
MVI (Model-View-Intent)
MVI is an architecture pattern used to handle state management and user interactions in a predictable manner. In the AlTasherat app, MVI was utilized to ensure clear separation of concerns and a unidirectional data flow, enhancing the app's scalability and maintainability.

Model: Represents the application's state and business logic. It handles data operations and updates the state based on user actions or other events.
View: Displays the UI and renders the state to the user. It listens to user interactions and sends intents to the Model.
Intent: Represents user actions or events that trigger changes in the Model. Intents are processed by the Model to update the application's state.
By adopting MVI, the AlTasherat app ensures that the state management and UI logic are cleanly separated, leading to more predictable behavior and easier debugging.
Actions: Actions represent user interactions, such as clicks or gestures, that trigger specific intents. These actions are sent from the View to the Model, which processes them to update the state.

SOLID Principles
S: Single Responsibility Principle
O: Open/Closed Principle
L: Liskov Substitution Principle
I: Interface Segregation Principle
D: Dependency Inversion Principle

Acknowledgements
SolutionPlus: For the internship opportunity and guidance.
