
package org.xmlactions.email;

/**
 * Translate HTML to plain text.  This is required for email text/html mime types
 * that need to be displayed in a PDF document.
 *
 * @author MichaelMurphy
 */
public class HtmlToText {

    /**
     * The MatchPattern array is a list of regexp patterns we want to replace
     * with a replacement string in an html source.
     * what some of the patterns mean:<br/>
     * "\\s*" - swallows zero or more whitespace characters<br/>
     * "(?i)" - case insensitive<br/>
     * "\n"   - new line<br/>
     * ".*"   - all characters except linefeed, return,...<br/>
     */
    private MatchPattern [] patterns ={
        new MatchPattern("<\\s*(?i)BR\\s*>", "\n"),
        new MatchPattern("<\\s*(?i)BR\\s*/>", "\n"),
        new MatchPattern("<\\s*/\\s*(?i)BR\\s*>", "\n"),
        new MatchPattern("<\\s*/\\s*(?i)P\\s*>", "\n"),
        new MatchPattern("<.*>", ""),
        new MatchPattern("&(?i)gt;", ">"),
        new MatchPattern("&(?i)lt;", "<"),
        new MatchPattern("&(?i)nbsp;", " "),
    };

    public String map(String htmlSource)
    {
        // first remove <head>...</head>
        String output = htmlSource.toLowerCase();
        int from = output.indexOf("<head>");
        int to = output.indexOf("</head>");
        if (from >= 0 && to > from)
        {
            output = htmlSource.substring(to+"</head>".length());
        }
        else
        {
            output = htmlSource;
        }

        for (MatchPattern matchPattern : patterns)
        {
            output = output.replaceAll(matchPattern.getExp(), matchPattern.getReplacement());
        }
        return output;
    }
    class MatchPattern
    {
        String exp;
        String replacement;
        public MatchPattern(String exp, String replacement)
        {
            this.exp = exp;
            this.replacement = replacement;
        }
        public String getExp()
        {
            return exp;
        }
        public String getReplacement()
        {
            return replacement;
        }
    }
}
