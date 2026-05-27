package highlighting.regex;
import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;

import java.util.ArrayList;
import java.util.List;

public class RegexHighlighter extends SyntaxHighlighter {

    private final List<Token> tokens;

    public RegexHighlighter(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public List<HighlightRegion> collectMatches(String text) {

        List<HighlightRegion> regions = new ArrayList<>();

        for (Token token : tokens) {

            regions.addAll(token.test(text));
        }

        return regions;
    }

    @Override
    public List<HighlightRegion> resolveConflicts(List<HighlightRegion> normalized) {

        List<HighlightRegion> resolved = new ArrayList<>();

        for (HighlightRegion current : normalized) {

            boolean overlaps = false;

            for (HighlightRegion existing : resolved) {

                boolean hasOverlap =
                    current.start() < existing.end()
                        && existing.start() < current.end();

                if (hasOverlap) {
                    overlaps = true;
                    break;
                }
            }

            if (!overlaps) {
                resolved.add(current);
            }
        }

        return resolved;
    }
}
