package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ScanningHighlighter extends SyntaxHighlighter {

    private final List<Token> tokens;

    public ScanningHighlighter(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public List<HighlightRegion> collectMatches(String text) {
        List<HighlightRegion> regions = new ArrayList<>();
        int position = 0;

        while (position < text.length()) {
            HighlightRegion bestRegion = null;
            int bestLength = -1;

            for (Token token : tokens) {
                Matcher matcher = token.pattern().matcher(text);
                matcher.region(position, text.length());

                if (matcher.lookingAt()) {
                    int start;
                    int end;

                    int group = token.matchingGroup();
                    if (group >= 0) {
                        start = matcher.start(group);
                        end = matcher.end(group);
                    } else {
                        start = matcher.start();
                        end = matcher.end();
                    }

                    int length = end - start;

                    if (start == position && length > bestLength) {
                        bestLength = length;
                        bestRegion = new HighlightRegion(start, end, token.colour());
                    }
                }
            }

            if (bestRegion != null) {
                regions.add(bestRegion);
                position = bestRegion.end();
            } else {
                position++;
            }
        }

        return regions;
    }

    @Override
    public List<HighlightRegion> normalize(List<HighlightRegion> candidates) {
        return candidates;
    }
}
