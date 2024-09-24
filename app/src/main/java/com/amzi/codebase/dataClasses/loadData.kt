package com.amzi.codebase.dataClasses
// Function to create ItemMainData
fun createItemMainData(): List<itemMainData> {
    return listOf(
        itemMainData(itemID = 1, itemDisplayName = "Row Layout", itemGroup = GroupType.ROW, itemType = ItemType.LAYOUT, itemLevel = Level.MEDIUM, isPremium = true, info = "A layout that arranges items in a horizontal sequence."),
        itemMainData(itemID = 2, itemDisplayName = "Column Layout", itemGroup = GroupType.COLUMN, itemType = ItemType.LAYOUT, itemLevel = Level.EASY, isPremium = false, info = "A layout that arranges items in a vertical sequence."),
        itemMainData(itemID = 3, itemDisplayName = "Grid Layout", itemGroup = GroupType.GRID, itemType = ItemType.LAYOUT, itemLevel = Level.HARD, isPremium = true, info = "A layout that arranges items in a grid format."),
        itemMainData(itemID = 4, itemDisplayName = "Alert Dialog", itemGroup = GroupType.ALERTDIAOLOG, itemType = ItemType.UI, itemLevel = Level.MEDIUM, isPremium = false, info = "A dialog that alerts the user."),
        itemMainData(itemID = 5, itemDisplayName = "Dropdown Menu", itemGroup = GroupType.DROPDOWNMENU, itemType = ItemType.MENU, itemLevel = Level.EASY, isPremium = true, info = "A menu that drops down to show options."),
        itemMainData(itemID = 6, itemDisplayName = "Navigator", itemGroup = GroupType.NAVIGATOR, itemType = ItemType.NAVIGATION, itemLevel = Level.MEDIUM, isPremium = true, info = "A component that navigates between screens."),
        itemMainData(itemID = 7, itemDisplayName = "Media Player", itemGroup = GroupType.NAVIGATOR, itemType = ItemType.MEDIA, itemLevel = Level.HARD, isPremium = false, info = "A player for audio and video media."),
        itemMainData(itemID = 8, itemDisplayName = "State Management", itemGroup = GroupType.COLUMN, itemType = ItemType.STATES, itemLevel = Level.EASY, isPremium = true, info = "Managing the state of the application."),
        itemMainData(itemID = 9, itemDisplayName = "Animations", itemGroup = GroupType.NAVHOST, itemType = ItemType.ANIMATION, itemLevel = Level.MEDIUM, isPremium = true, info = "Adding animations to your UI."),
        itemMainData(itemID = 10, itemDisplayName = "State Management", itemGroup = GroupType.NAVHOST, itemType = ItemType.STATES, itemLevel = Level.MEDIUM, isPremium = true, info = "Adding animations to your UI."),
        itemMainData(itemID =11, itemDisplayName = "State Management", itemGroup = GroupType.NAVHOST, itemType = ItemType.STATES, itemLevel = Level.MEDIUM, isPremium = true, info = "Adding animations to your UI."),
        itemMainData(itemID = 12, itemDisplayName = "State Management", itemGroup = GroupType.NAVHOST, itemType = ItemType.ANIMATION, itemLevel = Level.MEDIUM, isPremium = true, info = "Adding animations to your UI."),
        itemMainData(itemID = 13, itemDisplayName = "Navigation Host", itemGroup = GroupType.NAVHOST, itemType = ItemType.NAVIGATION, itemLevel = Level.HARD, isPremium = false, info = "A host for managing navigation in your app.")
    )
}

fun create2DList(): List<List<itemMainData>> {
    val allItems = createItemMainData()

    // Create a 2D list
    val groupedItems: MutableList<List<itemMainData>> = mutableListOf()

    // Grouping based on ItemType
    val layoutItems = allItems.filter { it.itemType == ItemType.LAYOUT }
    val uiItems = allItems.filter { it.itemType == ItemType.UI }
    val menuItems = allItems.filter { it.itemType == ItemType.MENU }
    val navigationItems = allItems.filter { it.itemType == ItemType.NAVIGATION }
    val mediaItems = allItems.filter { it.itemType == ItemType.MEDIA }
    val statesItems = allItems.filter { it.itemType == ItemType.STATES }
    val animationItems = allItems.filter { it.itemType == ItemType.ANIMATION }

    // Add the grouped lists to the 2D list
    groupedItems.add(layoutItems)
    groupedItems.add(uiItems)
    groupedItems.add(menuItems)
    groupedItems.add(navigationItems)
    groupedItems.add(mediaItems)
    groupedItems.add(statesItems)
    groupedItems.add(animationItems)

    return groupedItems
}

// Function to create ItemInnerDetails linked by parentID
fun createItemInnerDetails(): List<ItemInnerDetails> {
    return listOf(
        ItemInnerDetails(parentID = 1, itemIDInner = 1, itemNameInner = "Row Item 1", info = "First item in row layout."),
        ItemInnerDetails(parentID = 1, itemIDInner = 2, itemNameInner = "Row Item 2", info = "Second item in row layout."),
        ItemInnerDetails(parentID = 2, itemIDInner = 3, itemNameInner = "Column Item 1", info = "First item in column layout."),
        ItemInnerDetails(parentID = 2, itemIDInner = 4, itemNameInner = "Column Item 2", info = "Second item in column layout."),
        ItemInnerDetails(parentID = 3, itemIDInner = 5, itemNameInner = "Grid Item 1", info = "First item in grid layout."),
        ItemInnerDetails(parentID = 3, itemIDInner = 6, itemNameInner = "Grid Item 2", info = "Second item in grid layout."),
        ItemInnerDetails(parentID = 4, itemIDInner = 7, itemNameInner = "Dialog Item 1", info = "Item related to alert dialog."),
        ItemInnerDetails(parentID = 5, itemIDInner = 8, itemNameInner = "Dropdown Item 1", info = "First item in dropdown menu."),
        ItemInnerDetails(parentID = 6, itemIDInner = 9, itemNameInner = "Navigator Item 1", info = "Item related to navigator."),
        ItemInnerDetails(parentID = 7, itemIDInner = 10, itemNameInner = "Media Item 1", info = "Item for media player.")
    )
}

// Function to create CodeSnippets linked by parentID
fun createCodeSnippets(): List<CodeSnippets> {
    return listOf(
        CodeSnippets(parentID = 1, id = 1, fileName = "RowSnippet1.kt", code = """
            fun createRow() {
                // This function creates a row layout
                val rowLayout = Row {
                    Text("Row Item 1")
                    Text("Row Item 2")
                }
            }
        """.trimIndent()),
        CodeSnippets(parentID = 2, id = 2, fileName = "ColumnSnippet1.kt", code = """
            fun createColumn() {
                // This function creates a column layout
                val columnLayout = Column {
                    Text("Column Item 1")
                    Text("Column Item 2")
                }
            }
        """.trimIndent()),
        CodeSnippets(parentID = 3, id = 3, fileName = "GridSnippet1.kt", code = """
            fun createGrid() {
                // This function creates a grid layout
                val gridLayout = Grid {
                    // Add grid items here
                }
            }
        """.trimIndent()),
        CodeSnippets(parentID = 4, id = 4, fileName = "AlertDialogSnippet.kt", code = """
            fun showAlertDialog() {
                AlertDialog(
                    onDismissRequest = { /* TODO */ },
                    title = { Text("Alert Title") },
                    text = { Text("Alert message goes here.") },
                    confirmButton = {
                        Button(onClick = { /* TODO */ }) {
                            Text("OK")
                        }
                    }
                )
            }
        """.trimIndent()),
        CodeSnippets(parentID = 5, id = 5, fileName = "DropdownMenuSnippet.kt", code = """
            fun showDropdownMenu() {
                var expanded by remember { mutableStateOf(false) }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    // Dropdown items here
                }
            }
        """.trimIndent()),
        CodeSnippets(parentID = 6, id = 6, fileName = "NavigatorSnippet.kt", code = """
            fun navigateTo(destination: String) {
                // Navigation logic here
            }
        """.trimIndent()),
        CodeSnippets(parentID = 7, id = 7, fileName = "MediaPlayerSnippet.kt", code = """
            fun playMedia() {
                // Media playing logic
            }
        """.trimIndent()),
        CodeSnippets(parentID = 8, id = 8, fileName = "StateManagementSnippet.kt", code = """
            fun manageState() {
                // State management logic
            }
        """.trimIndent()),
        CodeSnippets(parentID = 9, id = 9, fileName = "AnimationSnippet.kt", code = """
            fun animateView() {
                // Animation logic here
            }
        """.trimIndent()),
        CodeSnippets(parentID = 10, id = 10, fileName = "NavigationHostSnippet.kt", code = """
            fun setupNavigationHost() {
                // Navigation host setup logic
            }
        """.trimIndent())
    )
}

// Function to create Reviews linked by parentID
fun createReviews(): List<Reviews> {
    return listOf(
        Reviews(parentID = 1, name = "User1", review = "Great row layout!", stars = 5),
        Reviews(parentID = 1, name = "User2", review = "Very useful.", stars = 4),
        Reviews(parentID = 2, name = "User3", review = "Nice column layout.", stars = 5),
        Reviews(parentID = 3, name = "User4", review = "Grid layout is fantastic.", stars = 5),
        Reviews(parentID = 4, name = "User5", review = "Very informative alert dialog.", stars = 4),
        Reviews(parentID = 5, name = "User6", review = "Dropdown menu works well.", stars = 5),
        Reviews(parentID = 6, name = "User7", review = "Navigator is easy to use.", stars = 5),
        Reviews(parentID = 7, name = "User8", review = "Media player is smooth.", stars = 4),
        Reviews(parentID = 8, name = "User9", review = "State management made easy.", stars = 5),
        Reviews(parentID = 9, name = "User10", review = "Animations are cool.", stars = 4)
    )
}
