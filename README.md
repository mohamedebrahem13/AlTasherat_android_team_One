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
screen shots 
![Verification 1](https://github.com/user-attachments/assets/79832478-f08b-4d4e-bf39-f6085cf96eee)
![verified by email](https://github.com/user-attachments/assets/1196b471-288b-4a45-a529-b509d24dc1e8)
![Menu without image](https://github.com/user-attachments/assets/bbb0d873-400d-4580-9e9c-11486cb1e87e)
![Menu when not login](https://github.com/user-attachments/assets/610f47a0-971c-48da-bbed-d3271139c16c)
![Menu](https://github.com/user-attachments/assets/b646e7dd-986c-4b9b-8643-44b4aae24466)
![Language change](https://github.com/user-attachments/assets/664ee912-bfe4-4361-9bb0-df420c2cc7b6)
![About](https://github.com/user-attachments/assets/8a1fc6c0-f9a4-4501-8b44-3ef38d4b6007)
![Employment form 2](https://github.com/user-attachments/assets/296c1d16-8b23-4b5d-a7d6-4ab8064d6776)
![Employment form 1](https://github.com/user-attachments/assets/132509a9-9dde-4b18-bf9d-f33bb4e78492)
![Orders in Employment visa](https://github.com/user-attachments/assets/2ac57afc-fd22-4a9c-adda-4e56d3593326)
![Orders in Employment visa details](https://github.com/user-attachments/assets/066c23fc-90cb-4378-b7d1-ef3be0557bdd)
![Tourism form 1](https://github.com/user-attachments/assets/902e6e15-b56d-4291-8742-16f252279765)
![Tourism form 2](https://github.com/user-attachments/assets/f4534dd8-94df-4f56-bf4a-f23c081f1fc6)
![Orders in tourism visa](https://github.com/user-attachments/assets/1204feeb-496d-4f2e-81ea-7525c4069abe)
![Order details in tourism visa Copy 2](https://github.com/user-attachments/assets/598bfe79-fca9-4e51-88d1-07ff7e54fa0a)
![Order details in tourism visa Copy](https://github.com/user-attachments/assets/3104b050-0d1d-4b4d-ba67-2622d4a538db)
![when not login emptystate](https://github.com/user-attachments/assets/2675fa0f-9a69-4073-a31f-f9438e83fba2)
![Personal info](https://github.com/user-attachments/assets/9ddcf408-af48-421b-bdcb-4dc674c7fc73)
![delete account 1](https://github.com/user-attachments/assets/a97098f1-edc3-4dc4-8da8-085097c56c61)
![delete account 2](https://github.com/user-attachments/assets/26983ef4-9b40-4ced-b7ef-ab8d23cdb37f)
![delete account 3](https://github.com/user-attachments/assets/555a309b-ac77-4b09-b7f8-d0ba9df92f38)
![Edit Password](https://github.com/user-attachments/assets/36a19fcb-323b-443a-b4d3-ed1e38c9a741)
![Verification 2](https://github.com/user-attachments/assets/be6ecc68-2ef7-4fd3-a0c5-e1094db28b9b)

