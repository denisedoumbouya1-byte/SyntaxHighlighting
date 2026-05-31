package highlighting.regex;

import static org.junit.jupiter.api.Assertions.*;

import highlighting.core.HighlightRegion;
import highlighting.presets.MiniJavaColours;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class RegexHighlighterTest {

    @Test
    void collectMatches_findsKeyword() {

        Token keyword =
            Token.of(
                Pattern.compile("\\bclass\\b"),
                MiniJavaColours.KEYWORD_COLOUR);

        RegexHighlighter highlighter =
            new RegexHighlighter(List.of(keyword));

        List<HighlightRegion> regions =
            highlighter.computeRegions("class Test");

        assertEquals(1, regions.size());

        assertEquals(0, regions.get(0).start());
        assertEquals(5, regions.get(0).end());

        assertEquals(
            MiniJavaColours.KEYWORD_COLOUR,
            regions.get(0).colour());
    }

    @Test
    void collectMatches_emptyInput() {

        RegexHighlighter highlighter =
            new RegexHighlighter(List.of());

        List<HighlightRegion> regions =
            highlighter.computeRegions("");

        assertTrue(regions.isEmpty());
    }

    @Test
    void resolveConflicts_keepsFirstRegion() {

        Token shortToken =
            Token.of(
                Pattern.compile("class"),
                MiniJavaColours.KEYWORD_COLOUR);

        Token longToken =
            Token.of(
                Pattern.compile("classA"),
                MiniJavaColours.STRING_LITERAL_COLOUR);

        RegexHighlighter highlighter =
            new RegexHighlighter(List.of(shortToken, longToken));

        List<HighlightRegion> regions =
            highlighter.computeRegions("classA");

        assertEquals(1, regions.size());

        assertEquals(0, regions.get(0).start());
        assertEquals(6, regions.get(0).end());
    }

    @Test
    void resolveConflicts_allowsAdjacentRegions() {

        HighlightRegion first =
            new HighlightRegion(
                0,
                5,
                MiniJavaColours.KEYWORD_COLOUR);

        HighlightRegion second =
            new HighlightRegion(
                5,
                10,
                MiniJavaColours.STRING_LITERAL_COLOUR);

        RegexHighlighter highlighter =
            new RegexHighlighter(List.of());

        List<HighlightRegion> resolved =
            highlighter.resolveConflicts(List.of(first, second));

        assertEquals(2, resolved.size());
    }

    @Test
    void collectMatches_multipleMatches() {

        Token keyword =
            Token.of(
                Pattern.compile("\\bif\\b"),
                MiniJavaColours.KEYWORD_COLOUR);

        RegexHighlighter highlighter =
            new RegexHighlighter(List.of(keyword));

        List<HighlightRegion> regions =
            highlighter.computeRegions("if (x) if (y)");

        assertEquals(2, regions.size());
    }
}
