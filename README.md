# Movie Search App

## Overview

Movie Search App is a simple Android application built with Kotlin and Jetpack Compose that fetches popular movies and supports live search by title using the [TMDB API](https://www.themoviedb.org/documentation/api). The app features:

- Fetching and displaying a list of popular movies with poster, title, and release date.
- Real-time search functionality that fetches matching movies from the API.
- Navigation between a Home screen (movie list + search) and a Movie Detail screen.
- Robust error handling for network issues and empty results.
- Clean architecture with separation of UI and business logic.
- Dependency injection using Hilt.
- Unit tests for repository and ViewModel.
- Structured with atomic design principles for reusable components.

## Getting Started

### Prerequisites

- Android Studio Bumblebee or higher
- Android SDK 33+
- Internet connection (for API calls)

### Setup

1. Clone or download the project.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.


### Testing

The project includes unit tests.

```
./gradlew test
```

## Notes

The search is debounced to reduce excessive API calls.