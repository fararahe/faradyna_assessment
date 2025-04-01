# Android Assessment
##FakeStoreAPI android app

## Description
This project is developed as part of an Android assessment to build a fully functional Android application that interacts with the FakeStoreAPI. The app provides essential features for an e-commerce platform, allowing users to browse products, manage a shopping cart, and proceed with checkout.

## Features
- Login: Secure user authentication, enabling users to log into the app and view personalized content.
- Product & Category Listing: Fetch and display a list of products and categories from the FakeStoreAPI.
- Cart: Add products to the cart, update quantities, and remove items.
- Checkout: Handle the checkout process, where users can review their cart and confirm the purchase.

## Tech Stack
- Kotlin: The primary programming language used for Android development, chosen for its concise syntax and interoperability with Java.
- Jetpack Compose: A modern toolkit for building native Android UIs, enabling a declarative UI paradigm with Kotlin.
- Hilt: A dependency injection library to simplify dependency management and improve code modularity and testability.
- Retrofit & OkHttp: Retrofit, with OkHttp as its underlying networking layer, handles the communication between the app and the FakeStoreAPI for fetching products, categories, and user information.
- Room: An abstraction layer over SQLite for persistent local database storage of cart items, user details, and other necessary data.
- DataStore: A modern alternative to SharedPreferences, used to store user preferences, such as authentication details and other small data.
