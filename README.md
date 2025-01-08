Text-based games are one of the oldest versions of interactive fiction. In their simplest form, the user finds themselves in a dark room that they must escape. **arandomroom** is a minimalist immersive text-based game where every story unfolds with a touch of unpredictability. Inspired by the simplicity of classics like A Dark Room, it leverages AI to craft unique, dynamic narratives for each playthrough, giving users the freedom to do as they wish. Each journey is a blend of exploration, strategy, and the unexpected, ensuring no two experiences are identical. Built with compose multiplatform, for both Android and iOS, this project also explores the possibility of building minimalistic mobile games, without the use of complex engines. Dive in and see where randomness takes you! Welcome to

![logo_transp](https://github.com/user-attachments/assets/510783b5-59f0-4ae3-906a-a22963a10797)

# Demo

# How to run the project

## Prerequisites

You need a Mac with macOS to run iOS-specific code on simulated or real devices. You will also need [AndroidStudio](https://developer.android.com/studio), [XCode](https://developer.apple.com/xcode/), [JDK](https://www.oracle.com/java/technologies/downloads/?er=221886), [KMP plugin](https://kotlinlang.org/docs/multiplatform-plugin-releases.html) and [Kotlin plugin](https://kotlinlang.org/docs/releases.html#update-to-a-new-release). Please follow the [set up environment guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-create-first-app.html#set-up-the-environment) before cloning/forking the project.

## Set up a Gemini API key and pick a model model

Create a new Gemini Api Key [here](https://aistudio.google.com/) and pick a model from [here](https://ai.google.dev/gemini-api/docs/models/gemini). Create a local.properties file in the project and add the API key and the model variant name as follows:

```
apiKey=your-generated-api-key
model=gemini-1.5-flash
```

You are now ready to [follow the run the application guide for either Android or iOS](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-create-first-app.html#run-your-application)!

# Built using

1. **[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)**  
   Enables sharing code across iOS, Android, JVM, Web, and native platforms.  

2. **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)**  
   Declarative UI framework for building cross-platform apps in Kotlin.  

3. **[Gemini SDK](https://github.com/PatilShreyas/generative-ai-kmp/tree/main)**  
   SDK for building Gemini protocol-based applications for KMP. 

4. **[Kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization)**  
   Multi-format reflectionless serialization

5. **[Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/)**  
   Lightweight dependency injection framework for Kotlin projects.  

6. **[Room](https://developer.android.com/kotlin/multiplatform/room)**  
   Abstraction layer over SQLite for simplified database interactions.  

7. **[SQLite](https://developer.android.com/kotlin/multiplatform/sqlite)**  
   Lightweight embedded relational database for local data storage.  

8. **[Napier](https://github.com/AAkira/Napier)**  
   Multiplatform logging library for Kotlin projects.  

9. **[Build Konfig](https://github.com/yshrsmz/BuildKonfig)**  
   Gradle plugin for type-safe configuration in multiplatform projects.  

# Architecture

![arandomroom](https://github.com/user-attachments/assets/97bc92a3-3e1c-4b94-b736-50c3c76521d3)

# License
This project is licensed under the [MIT License](LICENSE).
