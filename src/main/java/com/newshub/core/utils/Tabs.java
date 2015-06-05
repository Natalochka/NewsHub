package com.newshub.core.utils;

/**
 * Created by Natalie_2 on 6/1/2015.
 */
public class Tabs {
    private Boolean drafts = false;
    private Boolean being_processed_by_corrector = false;
    private Boolean being_processed_by_editor = false;
    private Boolean checked_by_corrector = false;
    private Boolean rejected = false;
    private Boolean published = false;

    public Boolean isDrafts() {
        return drafts;
    }

    public void setDrafts(Boolean drafts) {
        this.drafts = drafts;
    }

    public Boolean isBeing_processed_by_corrector() {
        return being_processed_by_corrector;
    }

    public void setBeing_processed_by_corrector(Boolean being_processed_by_corrector) {
        this.being_processed_by_corrector = being_processed_by_corrector;
    }

    public Boolean isBeing_processed_by_editor() {
        return being_processed_by_editor;
    }

    public void setBeing_processed_by_editor(Boolean being_processed_by_editor) {
        this.being_processed_by_editor = being_processed_by_editor;
    }

    public Boolean isChecked_by_corrector() {
        return checked_by_corrector;
    }

    public void setChecked_by_corrector(Boolean checked_by_corrector) {
        this.checked_by_corrector = checked_by_corrector;
    }

    public Boolean isRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}