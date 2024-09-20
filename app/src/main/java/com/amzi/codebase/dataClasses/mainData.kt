package com.amzi.codebase.dataClasses



data class itemMainData(
    val itemID:Int?=0,
    val itemDisplayName:String?="",
    val itemGroup:GroupType?=GroupType.ALL,
    val itemType: ItemType?=ItemType.ALL,
    val itemLevel:Level?=Level.EASY,
    val isPremium:Boolean?=true,
    val listOfInnerItems:List<ItemInnerDetails>? = emptyList()
)

data class ItemInnerDetails(
    val itemIDInner:Int?=0,
    val itemNameInner:String?="",
    val gitLink:String?="",
    val youtubeLink:String?="",
    val listOfReviews:List<Reviews>? = emptyList(),
    val codeSnippets:List<CodeSnippets>? = emptyList()
)

data class CodeSnippets(
    val id:Int?=0,
    val fileName:String?="",
    val code:String?="",
    val belongsTo:Int?=0
    )

data class Reviews(
    val name:String?="",
    val review:String?="",
    val stars:Int?=0
)


enum class GroupType(val value:String){
    ALL("All"),
    COLUMN("Column"),
    ROW("Row"),
    GRID("Grid"),

    ALERTDIAOLOG("Alert Dialog"),
    DROPDOWNMENU("Dropdown Menu"),
    DIALOGHOST("Dialog Host"),

    NAVIGATOR("Navigator"),
    NAVHOST("Nav Host"),
}

enum class ItemType(val value:String){

    ALL("All"),
    LAYOUT("Layout"),
    INPUTS("Inputs"),
    STATES("States"),
    UI("UI"),
    MEDIA("Media"),
    DISPLAY("Display"),
    MENU("Menu"),
    OTHER("Other"),
    ANIMATION("Animation"),
    NAVIGATION("Navigation")

}

enum class Level(val value:String){
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}