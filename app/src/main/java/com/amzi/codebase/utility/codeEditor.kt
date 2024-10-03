package com.amzi.codebase.utility

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.amzi.codebase.ui.theme.lightBlack
import com.amzi.codebase.ui.theme.lightGrey
import com.amzi.codebase.ui.theme.paleWhite
import com.amzi.codebase.ui.theme.pureWhite
import com.amzi.codebase.ui.theme.srcpro

@Composable
fun MainScreen(code: MutableState<String>) {
//    var code by remember { mutableStateOf("/* Write your code here */") }

    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).fillMaxWidth()
        .background(
            color = paleWhite,
            shape = RoundedCornerShape(8.dp)
        )
        .border( // Add border with conditional width
            width = 1.5.dp,
            color = lightGrey,
            shape = RoundedCornerShape(8.dp)
        ).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)  {
        /*SyntaxHighlightedTextField(
            value = code,
            onValueChange = { newCode -> code = newCode }
        )*/
        HighlightedCode(code.value)
    }
}


/*
@Composable
fun SyntaxHighlightedTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val tokens = remember(value) { tokenize(value) }

    var textValue by remember { mutableStateOf(TextFieldValue(value)) }

    val visualTransformation = remember(tokens) {
        Highlighter(tokens)
    }

    BasicTextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            onValueChange(newValue.text)
        },
        textStyle = TextStyle(fontFamily = FontFamily.Monospace),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),visualTransformation = visualTransformation
    )
}
*/

@Composable
fun SyntaxHighlightedTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(TextFieldValue(value)) }
    var autocompleteSuggestion by remember { mutableStateOf<String?>(null) }
    var showSuggestionBox by remember { mutableStateOf(false) }

    // Tokenize for syntax highlighting
    val tokens = remember(value) { tokenize(value) }

    // Visual transformation for highlighting
    val visualTransformation = remember(tokens) {
        Highlighter(tokens)
    }

    // Update autocomplete suggestion based on the current text input
    LaunchedEffect(textValue.text) {
        val words = textValue.text.split(" ", "\n")
        val lastWord = words.lastOrNull() ?: ""
        autocompleteSuggestion = if (lastWord.length >= 2) getSuggestion(lastWord) else null
        showSuggestionBox = autocompleteSuggestion != null && autocompleteSuggestion?.isNotEmpty() == true
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        BasicTextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                onValueChange(newValue.text)
            },
            textStyle = TextStyle(fontFamily = FontFamily.Monospace),
            modifier = Modifier
                .fillMaxSize()
                .onKeyEvent { event ->
                    if (event.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_TAB ||
                        event.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                        autocompleteSuggestion?.let { suggestion ->
                            val words = textValue.text.split(" ", "\n")
                            val lastWord = words.lastOrNull() ?: ""
                            val newText = textValue.text.dropLast(lastWord.length) + suggestion + " "
                            textValue = textValue.copy(
                                text = newText,
                                selection = TextRange(newText.length)
                            )
                            onValueChange(newText)
                            autocompleteSuggestion = null
                            showSuggestionBox = false
                        }
                        true
                    } else {
                        false
                    }
                },
            visualTransformation = visualTransformation // Apply syntax highlighting
        )

        if (showSuggestionBox) {
            AutocompleteSuggestionBox(
                suggestion = autocompleteSuggestion ?: "",
                onSuggestionClick = {
                    val words = textValue.text.split(" ", "\n")
                    val lastWord = words.lastOrNull() ?: ""
                    val newText = textValue.text.dropLast(lastWord.length) + autocompleteSuggestion.orEmpty() + " "
                    textValue = textValue.copy(
                        text = newText,
                        selection = TextRange(newText.length) // Set cursor to end
                    )
                    onValueChange(newText)
                    autocompleteSuggestion = null
                    showSuggestionBox = false
                }
            )
        }
    }
}



@Composable
fun AutocompleteSuggestionBox(
    suggestion: String,
    onSuggestionClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .offset(y = - 48.dp)
            .background(lightBlack)
            .border(1.dp, Color.Gray)
            .wrapContentSize()
            .padding(vertical = 6.dp, horizontal = 12.dp)
    ) {
        Text(
            text = suggestion,
            style = TextStyle(color = pureWhite, fontFamily = FontFamily.Monospace),
            modifier = Modifier.padding(8.dp).clickable { onSuggestionClick() }
        )
    }
}


fun getSuggestion(input: String): String? {
    // Return the first keyword that starts with the input text, case-insensitive
    Log.d("SUGGESSTION: ", input + "   " + combinedKeywords.firstOrNull { it.startsWith(input, ignoreCase = true) })
    return combinedKeywords.firstOrNull { it.startsWith(input, ignoreCase = true) }
}





class Highlighter(private val tokens: List<Token>) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedString {
                tokens.forEach { token ->
                    val color = when (token.type) {
                        "annotation" -> Color(0xFF64B5F6)
                        "keyword" -> Color(0xFFCC7832)
                        "number" -> Color(0xFF9876AA)
                        "string" -> Color(0xFF6A8759)
                        "comment" -> Color(0xFF808080)
                        "symbol" -> Color(0xFF000000)
                        "whitespace" -> Color.Transparent
                        "default" -> Color(0xFF000000)
                        else -> Color(0xFF000000)
                    }
                    withStyle(style = SpanStyle(color = color)) {
                        append(token.text)
                    }
                }
            },
            OffsetMapping.Identity
        )
    }
}

data class Token(val text: String, val type: String)

@Composable
fun HighlightedCode(code: String) {
    val tokens = tokenize(code)

    Text(buildAnnotatedString {
        tokens.forEach { token ->
            val color = when (token.type) {
                "error" -> Color.Red
                "annotation" -> Color(0xFF64B5F6)
                "keyword" -> Color(0xFFCC7832) // Orange for keywords
                "number" -> Color(0xFF9876AA) // Purple for numbers
                "string" -> Color(0xFF6A8759) // Green for strings
                "comment" -> Color(0xFF808080) // Gray for comments
                "symbol" -> Color(0xFF000000) // Black for symbols (braces, etc.)
                "whitespace" -> Color.Transparent // Maintain spaces
                // Handle the "default" case explicitly to avoid unintended coloring
                "default" -> Color(0xFF000000) // Black for default text
                else -> Color(0xFF000000) // Black for any other unhandled type
            }
            withStyle(style = SpanStyle(color = color)) {
                append(token.text)
            }
        }
    }, modifier = Modifier.padding(16.dp), style = TextStyle(
        fontFamily = srcpro
    )
    )
}

fun tokenize(code: String): List<Token> {
    val tokens = mutableListOf<Token>()
    val keywords = combinedKeywords
    val symbols = setOf(
        '{', '}', '(', ')', '[', ']', '=', ';', ',', '.', '+', '-', '*', '/', '%', '!', '<', '>', '&', '|', '^', '?', ':'
    )

    var i = 0
    while (i < code.length) {
        val char = code[i]
        when {
            // Handle annotations
            char == '@' -> {
                var annotationEnd = i + 1
                while (annotationEnd < code.length &&
                    code[annotationEnd] !in listOf(' ', '(', '\n')
                ) {
                    annotationEnd++
                }
                val annotation = code.substring(i, annotationEnd)
                tokens.add(Token(annotation, "annotation"))
                i = annotationEnd
            }
            // Handle single-line comments
            char == '/' && i + 1 < code.length && code[i + 1] == '/' -> {
                val commentEnd = code.indexOf('\n', i).takeIf { it != -1 } ?: code.length
                tokens.add(Token(code.substring(i, commentEnd), "comment"))
                i = commentEnd
            }
            // Handle multi-line comments
            char == '/' && i + 1 < code.length && code[i + 1] == '*' -> {
                val commentEnd = code.indexOf("*/", i + 2).takeIf { it != -1 }?.plus(2) ?: code.length
                tokens.add(Token(code.substring(i, commentEnd), "comment"))
                i = commentEnd
            }
            // Handle whitespace
            char.isWhitespace() -> {
                tokens.add(Token(char.toString(), "whitespace"))
                i++
            }
            // Handle symbols
            char in symbols -> {
                tokens.add(Token(char.toString(), "symbol"))
                i++
            }
            // Handle strings
            char == '"' -> {
                val stringEnd = code.indexOf('"', i + 1).takeIf { it != -1 } ?: code.length
                // Ensure that stringEnd + 1 does not exceed the length of the code
                val endIndex = minOf(stringEnd + 1, code.length)
                tokens.add(Token(code.substring(i, endIndex), "string"))
                i = endIndex
            }
            // Handle words (keywords, identifiers)
            char.isLetterOrDigit() || char == '_' -> {
                val start = i
                while (i < code.length && (code[i].isLetterOrDigit() || code[i] == '_')) {
                    i++
                }
                val word = code.substring(start, i)
                val type = when {
                    word in keywords -> "keyword"
                    word.all { it.isDigit() } -> "number"
                    else -> "default"
                }
                tokens.add(Token(word, type))
            }
            // Handle anything else as default
            else -> {
                tokens.add(Token(char.toString(), "default"))
                i++
            }
        }
    }
    return tokens
}


val combinedKeywords = listOf(
    // Kotlin Keywords
    "as", "as?", "break", "class", "continue", "do", "else", "false", "for", "fun",
    "if", "in", "!in", "interface", "is", "!is", "null", "object", "package", "return",
    "super", "this", "throw", "true", "try", "typealias", "val", "var", "when",
    "while", "by", "catch", "constructor", "delegate", "dynamic", "field", "file",
    "finally", "get", "import", "init", "param", "property", "receiver", "set",
    "setparam", "where", "actual", "abstract", "annotation", "companion", "const",
    "crossinline", "data", "enum", "expect", "external", "final", "infix", "inline",
    "inner", "internal", "lateinit", "noinline", "open", "operator", "out", "override",
    "private", "protected", "public", "reified", "sealed", "suspend", "tailrec",
    "vararg", "assert", "boolean", "byte", "case", "catch", "char", "default",
    "double", "extends", "float", "goto", "implements", "instanceof", "int", "long",
    "native", "new", "short", "static", "strictfp", "switch", "synchronized", "throws",
    "transient", "void", "volatile", "@Composable", "Surface", "Column", "Text", "Button", "LazyColumn", "items",
    "Row", "Box", "Icon", "Card", "Modifier", "fillMaxSize", "padding", "verticalArrangement",
    "horizontalArrangement", "background", "size", "shape", "CircleShape", "RoundedCornerShape",
    "MaterialTheme", "colorScheme", "Icons", "default", "tint", "contentDescription",
    "Image", "LaunchedEffect", "SideEffect", "DisposableEffect", "producedStateOf",
    "derivedStateOf", "LazyRow", "Scaffold", "TopAppBar", "BottomNavigation",
    "FloatingActionButton", "TextField", "OutlinedTextField", "CheckBox", "RadioButton",
    "Switch", "Slider", "DropdownMenu", "DropdownMenuItem", "Dialog", "AlertDialog",
    "ModalBottomSheet", "Navigation", "NavHost", "NavController", "NavGraph",
    "NavHostController", "NavBackStackEntry", "main", "class", "interface", "sealed", "data", "object",
    "enum","abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
    "class", "const", "continue", "default", "do", "double", "else", "enum",
    "extends", "final", "finally", "float", "for", "goto", "if", "implements",
    "import", "instanceof", "int", "interface", "long", "native", "new", "null",
    "package", "private", "protected", "public", "return", "short", "static",
    "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
    "transient", "try", "void", "volatile", "while"
)


val javaKeywords = listOf(
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
    "class", "const", "continue", "default", "do", "double", "else", "enum",
    "extends", "final", "finally", "float", "for", "goto", "if", "implements",
    "import", "instanceof", "int", "interface", "long", "native", "new", "null",
    "package", "private", "protected", "public", "return", "short", "static",
    "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
    "transient", "try", "void", "volatile", "while"
)