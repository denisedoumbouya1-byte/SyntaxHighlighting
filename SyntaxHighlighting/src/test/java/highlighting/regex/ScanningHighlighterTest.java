package highlighting.regex;

import static org.junit.jupiter.api.Assertions.*;

import highlighting.core.HighlightRegion;
import highlighting.presets.MiniJavaColours;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class ScanningHighlighterTest {

    @Test
    void collectMatches_prefersLongestMatchAtPosition() {

        Token shortToken =
            Token.of(
                Pattern.compile("if"),
                MiniJavaColours.KEYWORD_COLOUR);

        Token longToken =
            Token.of(
                Pattern.compile("[a-z]+"),
                MiniJavaColours.STRING_LITERAL_COLOUR);

        ScanningHighlighter highlighter =
            new ScanningHighlighter(List.of(shortToken, longToken));

        List<HighlightRegion> regions =
            highlighter.collectMatches("ifx");

        assertEquals(1, regions.size());

        assertEquals(0, regions.get(0).start());
        assertEquals(3, regions.get(0).end());

        assertEquals(
            MiniJavaColours.STRING_LITERAL_COLOUR,
            regions.get(0).colour());
    }

    @Test
    void collectMatches_prefersEarlierTokenOnTie() {

        Token first =
            Token.of(
                Pattern.compile("ab"),
                MiniJavaColours.KEYWORD_COLOUR);

        Token second =
            Token.of(
                Pattern.compile("ab"),
                MiniJavaColours.STRING_LITERAL_COLOUR);

        ScanningHighlighter highlighter =
            new ScanningHighlighter(List.of(first, second));

        List<HighlightRegion> regions =
            highlighter.collectMatches("ab");

        assertEquals(1, regions.size());

        assertEquals(0, regions.get(0).start());
        assertEquals(2, regions.get(0).end());

        assertEquals(
            MiniJavaColours.KEYWORD_COLOUR,
            regions.get(0).colour());
    }

    @Test
    void collectMatches_scansLeftToRight() {

        Token letters =
            Token.of(
                Pattern.compile("[a-z]+"),
                MiniJavaColours.KEYWORD_COLOUR);

        Token digits =
            Token.of(
                Pattern.compile("\\d+"),
                MiniJavaColours.STRING_LITERAL_COLOUR);

        ScanningHighlighter highlighter =
            new ScanningHighlighter(List.of(letters, digits));

        List<HighlightRegion> regions =
            highlighter.collectMatches("abc 123");

        assertEquals(2, regions.size());

        assertEquals(0, regions.get(0).start());
        assertEquals(3, regions.get(0).end());

        assertEquals(4, regions.get(1).start());
        assertEquals(7, regions.get(1).end());
    }

    @Test
    void collectMatches_skipsNonMatchingCharacters() {

        Token digit =
            Token.of(
                Pattern.compile("\\d"),
                MiniJavaColours.KEYWORD_COLOUR);

        ScanningHighlighter highlighter =
            new ScanningHighlighter(List.of(digit));

        List<HighlightRegion> regions =
            highlighter.collectMatches("a1b");

        assertEquals(1, regions.size());

        assertEquals(1, regions.get(0).start());
        assertEquals(2, regions.get(0).end());
    }

    @Test
    void normalize_returnsInputUnchanged() {

        ScanningHighlighter highlighter =
            new ScanningHighlighter(List.of());

        List<HighlightRegion> input = List.of(
            new HighlightRegion(
                0,
                1,
                MiniJavaColours.KEYWORD_COLOUR),

            new HighlightRegion(
                2,
                4,
                MiniJavaColours.STRING_LITERAL_COLOUR)
        );

        assertSame(input, highlighter.normalize(input));
    }
}
