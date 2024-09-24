package com.amzi.codebase.dataClasses

data class itemMainData(
    val itemID:Int?=0,
    val itemDisplayName:String?="",
    val itemGroup:GroupType?=null,
    val itemType: ItemType?=null,
    val itemLevel:Level?=Level.EASY,
    val isPremium:Boolean?=true,
    val info:String?=""
)

data class itemMainDataFromServer(
    val itemID: Int? = 0,
    val noOfViews:Int?=0,
    val noOfReviews:Int?=0
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
    val youtubeLink:String?=""
)

data class CodeSnippets(
    val id:Int?=0,
    val fileName:String?="",
    val code:String?="",
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

    ALERTDIAOLOG("Alert Dialog"),
    DROPDOWNMENU("Dropdown Menu"),
    DIALOGHOST("Dialog Host"),

    NAVIGATOR("Navigator"),
    NAVHOST("Nav Host"),
}

enum class ItemType(val value:String, val info:String) {
    LAYOUT("Layout","Layouts are used to define how UI elements (composables) are arranged and displayed on the screen. These functions determine the position, size, and alignment of child composables."),
    INPUTS("Inputs","Inputs Lorem Ipsum"),
    STATES("States","States Lorem Ipsum"),
    UI("UI","Lorem Ipsum"),
    MEDIA("Media","Lorem Ipsum"),
    DISPLAY("Display","Lorem Ipsum"),
    MENU("Menu","Lorem Ipsum"),
    OTHER("Other","Lorem Ipsum"),
    ANIMATION("Animation","Lorem Ipsum"),
    NAVIGATION("Navigation","Lorem Ipsum")
}

enum class Level(val value:String){
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}