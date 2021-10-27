package ru.masmirnov.sd.mvc.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagsUtils {

    private static String removeBoundSpaces(String s) {
        int first = 0;
        while (first < s.length() && Character.isWhitespace(s.charAt(first))) {
            first++;
        }
        if (first == s.length())
            return "";

        int last = s.length() - 1;
        while (last >= 0 && Character.isWhitespace(s.charAt(last))) {
            last--;
        }
        return s.substring(first, last + 1);
    }

    public static Set<String> getTagsSet(String tags) {
        if (tags.isEmpty())
            return new HashSet<>();
        return new HashSet<>(List.of(removeBoundSpaces(tags).split("\\s+")));
    }

    public static String toString(Set<String> tags) {
        return tags == null || tags.isEmpty()? "" : String.join(" ", tags);
    }

}
