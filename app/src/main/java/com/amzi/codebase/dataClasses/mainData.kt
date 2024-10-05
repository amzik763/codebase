package com.amzi.codebase.dataClasses

data class itemMainData(
    val itemID:Int?=0,
    val itemDisplayName:String?="",
    val itemGroup:GroupType?=null,
    val itemType: ItemType?=null,
    val itemLevel:Level?=Level.EASY,
    val info:String?=""
)

data class itemMainDataFromServer(
    val itemID: Int? = 0,
    val noOfViews:Int?=0,
    val noOfReviews:Int?=0,
)

data class ItemInnerDetails(
    val parentID:Int?=0,
    val itemIDInner:Int?=0,
    val itemNameInner:String?="",
    val info:String?="",
)

data class ItemInnerDetailsFromServer(
    val itemID: Int? = 0,
    val gitLink:String?="",
    val youtubeLink:String?="",
    val isPremium:Boolean?=true,
)

//update class to add dummy text and list of codesnippets
data class CodeSnippets(
    val id:Int?=0,
    val fileName:String?="",
    val code:String?="",
    val parentID:Int?=0
)

data class CodeSnippetsV2(
    val id:Int?=0,
    val fileName:String?="",
    val code:String?="",
    val info:String?="",
    val parentID:Int?=0
)

data class Reviews(
    val parentID: Int? = 0,
    val name:String?="",
    val review:String?="",
    val stars:Int?=0
)

enum class GroupType(val value:String){
    COLUMN("Column"),
    ROW("Row"),
    GRID("Grid"),
    BOX("Box"),
    HORIZONTALLIST("Horizontal List"),
    VERTICALLIST("Vertical List"),
    SCROLLABLE("Scrollable"),

    TEXTFIELD("Text Field"),
    OUTLINEDTEXTFIELD("Outlined Text Field"),
    BASICTEXTFIELD("Basic Text Field"),

    ALERTDIAOLOG("Alert Dialog"),
    DROPDOWNMENU("Dropdown Menu"),
    DIALOGHOST("Dialog Host"),

    NAVIGATOR("Navigator"),
    NAVHOST("Nav Host"),
}

enum class ItemType(val value: String, val info: String) {
    LAYOUT("Layout", "Layouts define how composables are arranged on the screen. They manage the positioning, size, and alignment of child composables, such as Column, Row, Box, etc."),
    INPUTS("Inputs", "Inputs are UI components that allow users to interact with the app by providing data or triggering actions. This includes text fields, buttons, sliders, switches, checkboxes, etc."),
    STATES("States", "States manage and represent the dynamic data within a composable. They allow UI components to react to changes in data and trigger recompositions."),
    UI("UI", "UI elements refer to the core building blocks of the user interface, such as Text, Image, and other basic composables used to display information."),
    MEDIA("Media", "Media elements include components for displaying and interacting with media content, such as images, audio, and video."),
    DISPLAY("Display", "Display components are focused on showing data to the user, often in a structured or formatted way, like lists, grids, and cards."),
    MENU("Menu", "Menus provide navigation and action options for users, such as dropdowns, context menus, and toolbars."),
    OTHER("Other", "This category includes miscellaneous components that don’t fit into the main categories but still contribute to the UI and functionality."),
    ANIMATION("Animation", "Animations provide visual feedback and transitions, helping to enhance user experience by making interactions more intuitive and engaging."),
    NAVIGATION("Navigation", "Navigation components help users move between screens or sections of an app, such as NavController and navigation routes."),

    // Additional Categories
    DRAWING("Drawing", "Drawing components allow custom rendering and creation of shapes, paths, and other visual elements using Canvas and other drawing APIs."),
    GESTURES("Gestures", "Gesture components allow users to interact with the app through touch events, such as swipes, drags, and multi-touch gestures."),
    PERMISSIONS("Permissions", "Permissions handle requesting and managing runtime permissions from the user, such as camera, location, and storage access."),
    NETWORKING("Networking", "Networking components manage API calls, data fetching, and network-related tasks, ensuring smooth data communication between the app and external services."),
    THEMING("Theming", "Theming components control the app's appearance, including color schemes, typography, and other visual styles that contribute to branding."),
    PERFORMANCE("Performance", "Performance-focused components and tools are designed to ensure optimal rendering and responsiveness, such as LazyColumn, LazyRow, and optimizations for large data sets."),
    TESTING("Testing", "Testing components help in creating UI tests and ensuring that the composables work as expected. This includes testing libraries like Espresso and Compose UI testing."),
    ACCESSIBILITY("Accessibility", "Accessibility components enhance the app’s usability for users with disabilities, such as screen readers and content descriptions."),

    // Even More Categories
    DATABASE("Database", "Database components manage local storage and data persistence, such as Room, SQLite, or data stores."),
    BACKGROUND_TASKS("Background Tasks", "Components for managing work that occurs outside the main UI thread, such as WorkManager, coroutines, and services."),
    SECURITY("Security", "Security components ensure user data protection, manage authentication, encryption, and permissions."),
    CACHING("Caching", "Caching components optimize performance by storing data locally, reducing network calls or recomputation."),
    CLOUD("Cloud", "Cloud components interact with cloud services, like Firebase, AWS, or other backend services."),
    LOCALIZATION("Localization", "Localization components ensure the app supports multiple languages and formats for different regions."),
    ERROR_HANDLING("Error Handling", "Components for managing exceptions, crashes, and showing user-friendly error messages."),
    INTEGRATIONS("Integrations", "These components interact with third-party libraries, APIs, and SDKs to extend app functionality."),
    FILES("Files", "Components that manage file handling, such as reading from or writing to the file system, handling PDFs, images, and documents."),
    NOTIFICATIONS("Notifications", "Notification components manage push notifications, local notifications, and in-app alerts."),
    SENSORS("Sensors", "Sensors provide access to device hardware such as GPS, accelerometer, gyroscope, and other motion sensors."),
    PAYMENTS("Payments", "Components that manage in-app purchases, subscriptions, and payment gateways."),
    CUSTOM_VIEWS("Custom Views", "Components for creating custom, reusable UI elements that go beyond standard composables."),
    WEB("Web", "Web components enable web content embedding or interaction with web APIs, such as WebView or JavaScript integration."),
    DEPENDENCY_INJECTION("Dependency Injection", "Components to manage the injection of dependencies, such as Hilt or Dagger, into your composables and view models."),
    MULTI_WINDOW("Multi-Window", "Components for handling multi-window or split-screen interactions, ensuring proper layout and functionality across different contexts."),
    SCHEDULED_TASKS("Scheduled Tasks", "Manage scheduled background tasks like alarms, reminders, or periodic work using WorkManager or AlarmManager.")
}


enum class Level(val value:String){
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}