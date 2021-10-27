package ru.masmirnov.sd.mvc.model;

import ru.masmirnov.sd.mvc.utils.TagsUtils;

import java.util.Set;

public class TodoFilter {

    private String filter;

    private Set<String> tags;

    public TodoFilter() { }

    public TodoFilter(String tagsString, String filter) {
        setTagsString(tagsString);
        setFilter(filter);
    }

    public String getTagsString() {
        return TagsUtils.toString(tags);
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTagsString(String tagsString) {
        this.tags = TagsUtils.getTagsSet(tagsString);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}
