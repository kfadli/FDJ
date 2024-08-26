This project app is  built entirely with Kotlin and Jetpack Compose with MultiModular Architecture.

Structure of Project is organized as below:

- *App Module* (Responsible to use on all other modules)
- *Feature Module* (UI Layer with Compose Layout and ViewModel
- *Domain Module* (contains UseCases)
- *Data Module* (for serving network requests)

Libraries used: 
- Koin for Dependency Injection,
- Kotlin
- Ktor for NetworkRequest
- Timber for Logging
- Coil for ImageLoader base on URL
- Turbine for testing Flow
- Mockk for mock :)

And of course JUnit
  
