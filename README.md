Rive 🚀️
<img align="right" width="300" src="screenshots/riveanimation.gif" alt="riveanimation.gif">

Rive is a powerful tool for creating interactive animations and integrating them into applications.
It allows designers to create animations and control them dynamically within an app. Rive animations
can change in real time and respond to user interactions, enhancing the visual and interactive
experience of the application.

Using Jetpack Compose and ViewModel, I have created an example login screen featuring these
interactive animations. This application offers a dynamic way to toggle password visibility and
provides a user interface enriched with animations.

## Getting Started

Follow the steps below for a quick start on integrating Rive into your Android app.

### Add the Rive dependency

Add the following dependencies to your `build.gradle` file in your project:

```gradle
dependencies {
    implementation 'app.rive:rive-android:5.1.0'
    // During initialization, you may need to add a dependency
    // for Jetpack Startup
    implementation "androidx.startup:startup-runtime:1.1.1"
}
```

Rive needs to initialize its runtime when your app starts. It can be done via an initializer that
does this for you automatically. The initialization provider can be set up directly in your
app'smanifest file:

```xml

<application>
    <activity>
        ...
    </activity>
    <provider android:name="androidx.startup.InitializationProvider"
        android:authorities="${applicationId}.androidx-startup" android:exported="false"
        tools:node="merge">
        <meta-data android:name="app.rive.runtime.kotlin.RiveInitializer"
            android:value="androidx.startup" />
    </provider>
</application>
```

### Add RiveAnimation to your compose

The simplest way to integrate a Rive animation into your application is to include it as part of a
layout. For this, you will load the Rive file from raw resources and automatically play its first
animation. This example assumes that you have downloaded a .riv file (e.g.,
animated_login_character.riv) and placed it in your res/raw directory.

```kotlin
@Composable
fun RiveAnimationExample() {
    ComposableRiveAnimationView(
        modifier = Modifier.fillMaxSize(),
        animation = R.raw.animated_login_character, // Resource ID of the Rive file
        stateMachineName = "LoginMachine" // Name of the state machine
    ) { view ->
        // Optionally, set some states to control the animation
        view.setBooleanState("LoginMachine", "isAnimating", true)
        // You can set additional states or perform other actions as needed
    }
}
```

Rive is a powerful tool for interactive animations, allowing real-time changes and user interaction.
This guide shows how to integrate Rive animations into your Android app using Jetpack Compose.

---

Thank you for visiting my repository! Enjoy exploring the content and feel free to reach out if you
have any questions or feedback.
