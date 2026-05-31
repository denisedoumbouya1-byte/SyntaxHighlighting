package highlighting.presets;

import highlighting.regex.Token;
import java.util.List;
import java.util.regex.Pattern;

public final class MiniJavaTokens {

    public static List<Token> defaultTokens() {

        return List.of(

            // =========================
            // String literals
            // =========================
            Token.of(
                Pattern.compile("\"([^\"\\\\]|\\\\.)*\""),
                MiniJavaColours.STRING_LITERAL_COLOUR
            ),

            // =========================
            // Character literals
            // =========================
            Token.of(
                Pattern.compile("'([^'\\\\]|\\\\.)'"),
                MiniJavaColours.CHAR_LITERAL_COLOUR
            ),

            // =========================
            // Java keywords
            // =========================
            Token.of(
                Pattern.compile(
                    "\\b(class|public|private|protected|static|void|int|char|boolean|if|else|while|for|return|new|package|import|extends|this|null|true|false|final|try|catch|switch|case|break|continue|default|do|super)\\b"
                ),
                MiniJavaColours.KEYWORD_COLOUR
            ),

            // =========================
            // Annotations
            // =========================
            Token.of(
                Pattern.compile("@[A-Za-z_][A-Za-z0-9_]*"),
                MiniJavaColours.ANNOTATION_COLOUR
            ),

            // =========================
            // JavaDoc comments
            // =========================
            Token.of(
                Pattern.compile("/\\*\\*[\\s\\S]*?\\*/"),
                MiniJavaColours.JAVADOC_COMMENT_COLOUR
            ),

            // =========================
            // Block comments
            // =========================
            Token.of(
                Pattern.compile("/\\*[\\s\\S]*?\\*/"),
                MiniJavaColours.BLOCK_COMMENT_COLOUR
            ),

            // =========================
            // Line comments
            // =========================
            Token.of(
                Pattern.compile("//.*"),
                MiniJavaColours.LINE_COMMENT_COLOUR
            )
        );
    }
}
