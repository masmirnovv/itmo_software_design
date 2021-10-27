package ru.masmirnov.sd.mvc.model;

import ru.masmirnov.sd.mvc.utils.TagsUtils;

import java.util.*;

public class TodoEntry {

    private String id;
    private String description;

    private Set<String> tags;

    public TodoEntry() { }

    public TodoEntry(String id, String description, String tagsString) {
        setId(id);
        setDescription(description);
        setTagsString(tagsString);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
