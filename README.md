
# 🔒 VaultKeeper: Elevating Your Digital Security for Passwords and Banking Cards 💳

<img width="auto" height="200px" align="left" src="doc/main_logo.png" />

🚀 **VaultKeeper**: The ultimate solution to simplify and secure your digital life! With VaultKeeper, you can efficiently and securely manage your passwords and banking details, all within an encrypted vault. 🔐

🔑 No more juggling multiple credentials; our app provides hassle-free access to both your accounts and banking cards, all within a safeguarded and encrypted environment.

👨‍💻 VaultKeeper is designed to be your all-in-one digital security companion. The app ensures that every password and piece of financial information you store is encrypted with the latest security protocols. This encryption is not only robust but also ensures that even if your device is lost or stolen, your data remains inaccessible to unauthorized users. 🛡️

🔒 With VaultKeeper, you can generate strong, unique passwords for every account and store them safely. The intuitive interface, powered by Jetpack Compose, makes navigation and management of your data straightforward and enjoyable. No more struggling with remembering multiple passwords or worrying about the security of your banking information.

<p align="center">
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
  <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/firebase-ffca28?style=for-the-badge&logo=firebase&logoColor=black" />
  <img src="https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white" />
</p>

## Key Features:

* **🔐 Top-Notch Security:** VaultKeeper uses advanced encryption to ensure your passwords and financial data are always protected.
* **⚡ Easy and Fast Access:** Manage your passwords and banking cards from anywhere, anytime, without complications.
* **🎨 Modern and Intuitive Interface:** Designed with the latest design technologies, including Jetpack Compose, offering a seamless and attractive user experience.
* **🛠️ Comprehensive Management:** From creating secure passwords to managing card details, VaultKeeper covers all your digital security needs.
Best Practices and Modern Technologies:

At VaultKeeper, we have applied the best mobile architecture practices and design patterns to ensure a robust and scalable application. Our approach includes:

* **🏗️ Clean Architecture:** To ensure maintainable and testable code.
* **📐 Design Patterns:** Implementation of patterns like MVI (Model-View-Intent) for clear separation of concerns.
* **🚀 Jetpack Compose:** Utilizing the latest Android ecosystem innovations to build declarative and reactive user interfaces.
* **🔧 Jetpack Components:** Integration with Jetpack components for more efficient development and advanced features.

<img src="doc/screenshots/picture_25.png" />

## User Benefits:

* **🛡️ Peace of Mind and Security:** Enjoy the peace of mind knowing your data is protected with the highest security standards.
* **⏱️ Efficiency and Convenience:** Simplify your digital life by having all your critical information in one securely accessible place.
* **🌟 Innovation and Modernity:** Experience an app that is not only functional but also modern and at the forefront of mobile technology.

## Developed with Brownie UI Library

🍫 **Brownie**: [Jetpack Compose UI Library](https://github.com/sergio11/brownie_ui_library) 🚀

Brownie is a 🌟 Jetpack Compose library module that provides a set of pre-defined components to accelerate the development of Android interfaces and applications. It is designed to help developers apply best practices in screen state management and utilize the most effective design patterns in their projects.

### Features 🎉

- **Pre-defined Components**: Brownie offers a variety of ready-to-use components, from buttons to lists and cards, to facilitate the creation of attractive and consistent user interfaces.
- **State Management**: Facilitates screen state management by implementing patterns such as MVI (Model-View-Intent) or MVVM (Model-View-ViewModel).
- **Customization**: Brownie components are highly customizable and can easily adapt to the visual style of any application.
- **Jetpack Compose Compatibility**: Fully integrated with Jetpack Compose, the modern Android UI library, to ensure optimal performance and a smooth development experience.

### Model-View-Intent (MVI) Architecture 🏗️

Brownie encourages the use of the Model-View-Intent (MVI) architecture pattern for effective screen state management. In this pattern:

- **Model**: Represents the state of the UI. Brownie provides a `BrownieViewModel` class that extends from `ViewModel` and handles the UI state.
- **View**: Renders the UI based on the state provided by the ViewModel. Brownie components are seamlessly integrated with Compose to create a declarative UI.
- **Intent**: Represents user actions or events that trigger state changes. Brownie's components, like buttons and text fields, are designed to emit these intents efficiently.

This setup leverages Brownie's components and ViewModel to accelerate the development of robust features while adhering to best practices in architecture and UI design.

For more information, check out the [Brownie UI Library on GitHub](https://github.com/sergio11/brownie_ui_library) and give it a ⭐ star to show your support!

## 🌟 Seamless User Experience

I have focused on providing a seamless user experience by leveraging cutting-edge Android technologies. Jetpack Compose enables me to create a responsive and visually appealing interface that adapts to various screen sizes and orientations. Integration with Jetpack components ensures smooth data synchronization, reliable background tasks, and efficient navigation flows, resulting in a fluid and enjoyable user experience.

## 🔒 Security at Its Core

Security is at the heart of VaultKeeper. I implement industry best practices in cryptographic algorithms and key management to ensure your data is always protected. My use of Clean Architecture and the MVVM design pattern ensures the codebase is organized, maintainable, and easily testable, translating into a more secure and reliable application.

##  🏗️ Clean Architecture
VaultKeeper employs Clean Architecture principles to ensure the app is scalable, maintainable, and testable. Clean Architecture separates the code into distinct layers, each with clear responsibilities:

* **🎨 Presentation Layer:** Built with Jetpack Compose, this layer handles the UI and user interactions. It communicates with the ViewModel to retrieve data and update the UI reactively.
* **💡 Domain Layer:** The core of the application, containing business logic and application rules. It includes use cases that orchestrate the flow of data to and from the presentation and data layers.
* **🗄️ Data Layer:** Responsible for data management, this layer includes repositories that abstract the data sources, whether they are local (Room database) or remote (Firestore).

## 📊 Data Sources

VaultKeeper ensures data persistence and security using a combination of local and remote data sources:

* **📂 Local Data Source (Room):** Room is used to cache debit card information locally. The data is encrypted using advanced cryptographic techniques to ensure security, even if the device is compromised.
* **☁️ Remote Data Source (Firestore):** Firestore serves as the remote data repository, ensuring data is not lost even if the app is uninstalled. Firestore provides real-time data synchronization and offline support.

## 🛡️ Cryptographic Techniques
To protect user data, VaultKeeper implements robust cryptographic practices:

* **🔑 Master Key and Salt:** Each user's data is encrypted with a unique master key and salt, which are generated programmatically and stored securely in Firestore.
* **🔐 Data Encryption:** All sensitive information, including passwords and banking details, is encrypted using the master key and salt.
* **🗝️ Application Master Key:** The master keys and salts are encrypted using the application's master key. This key is stored in a secure .so file, accessible via Java NDK, preventing extraction through decompilation.

##    🔏 Key Storage

The .so file containing the application's master key ensures that the key cannot be easily extracted by attackers, enhancing the security of the encryption keys stored in Firestore.

## App Screenshots

Here are some screenshots from our app to give you a glimpse of its design and functionality.

<img width="250px" align="left" src="doc/screenshots/picture_1.png" />
<img width="250px" align="left" src="doc/screenshots/picture_2.png" />
<img width="250px" src="doc/screenshots/picture_3.png" />

<img width="250px" align="left" src="doc/screenshots/picture_4.png" />
<img width="250px" align="left" src="doc/screenshots/picture_5.png" />
<img width="250px" src="doc/screenshots/picture_6.png" />

<img width="250px" align="left" src="doc/screenshots/picture_7.png" />
<img width="250px" align="left" src="doc/screenshots/picture_8.png" />
<img width="250px" src="doc/screenshots/picture_9.png" />

<img width="250px" align="left" src="doc/screenshots/picture_10.png" />
<img width="250px" align="left" src="doc/screenshots/picture_11.png" />
<img width="250px" src="doc/screenshots/picture_12.png" />

<img width="250px" align="left" src="doc/screenshots/picture_13.png" />
<img width="250px" align="left" src="doc/screenshots/picture_14.png" />
<img width="250px" src="doc/screenshots/picture_15.png" />

<img width="250px" align="left" src="doc/screenshots/picture_16.png" />
<img width="250px" align="left" src="doc/screenshots/picture_17.png" />
<img width="250px" src="doc/screenshots/picture_18.png" />

<img width="250px" align="left" src="doc/screenshots/picture_19.png" />
<img width="250px" align="left" src="doc/screenshots/picture_20.png" />
<img width="250px" src="doc/screenshots/picture_21.png" />

<img width="250px" align="left" src="doc/screenshots/picture_22.png" />
<img width="250px" align="left" src="doc/screenshots/picture_23.png" />
<img width="250px" src="doc/screenshots/picture_24.png" />

<img width="250px" align="left" src="doc/screenshots/picture_26.png" />
<img width="250px" align="left" src="doc/screenshots/picture_27.png" />
<img width="250px" src="doc/screenshots/picture_28.png" />

<img width="250px" align="left" src="doc/screenshots/picture_29.png" />
<img width="250px" align="left" src="doc/screenshots/picture_30.png" />
<img width="250px" src="doc/screenshots/picture_34.png" />

<img width="250px" align="left" src="doc/screenshots/picture_35.png" />
<img width="250px" align="left" src="doc/screenshots/picture_36.png" />
<img width="250px" src="doc/screenshots/picture_37.png" />

<img width="250px" align="left" src="doc/screenshots/picture_31.png" />
<img width="250px" align="left" src="doc/screenshots/picture_32.png" />
<img width="250px" src="doc/screenshots/picture_33.png" />

## Contribution
Contributions to VaultKeeper are highly encouraged! If you're interested in adding new features, resolving bugs, or enhancing the project's functionality, please feel free to submit pull requests.

## Credits
VaultKeeper is developed and maintained by Sergio Sánchez Sánchez (Dream Software). Special thanks to the open-source community and the contributors who have made this project possible. If you have any questions, feedback, or suggestions, feel free to reach out at dreamsoftware92@gmail.com.

## Acknowledgements 🙏

- We express our deep appreciation to [Freepik](https://www.freepik.es/) for generously providing the resources used in this project.

- <div> Icons and images takes from <a href="https://www.freepik.com" title="Freepik"> Freepik </a> from <a href="https://www.flaticon.es/" title="Flaticon">www.flaticon.es'</a></div>
- Template mockup from https://previewed.app/template/AFC0B4CB
 ## Visitors Count

