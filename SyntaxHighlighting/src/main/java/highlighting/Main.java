package highlighting;

import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;
import highlighting.presets.Texts;
import highlighting.regex.RegexHighlighter;
import highlighting.regex.ScanningHighlighter;
import highlighting.ui.EditorUI;

public class Main {

    public static void main(String... args) {

        // Phase I: RegexHighlighter
        SyntaxHighlighter regex =
            new RegexHighlighter(MiniJavaTokens.defaultTokens());

        // Phase II: ScanningHighlighter
        SyntaxHighlighter scanning =
            new ScanningHighlighter(MiniJavaTokens.defaultTokens());

        // and go ...
        EditorUI.show(Texts.START_TEXT, regex);
        EditorUI.show(Texts.START_TEXT, scanning);
    }
}
