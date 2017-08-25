package models;

public class PersonalNotes {
    private int id;
    private String writtenBy;
    private int entryId;
    private int personalNotesId;
    private String content;

    public PersonalNotes(String writtenBy, int entryId, int personalNotesId, String content) {
        this.writtenBy = writtenBy;
        this.entryId = entryId;
        this.personalNotesId = personalNotesId;
        this.content = content;
    }

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPersonalNotesId() {
        return personalNotesId;
    }

    public void setPersonalNotesId(int personalNotesId) {
        this.personalNotesId = personalNotesId;
    }

    // equals and hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalNotes that = (PersonalNotes) o;

        if (entryId != that.entryId) return false;
        if (personalNotesId != that.personalNotesId) return false;
        if (!writtenBy.equals(that.writtenBy)) return false;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        int result = writtenBy.hashCode();
        result = 31 * result + entryId;
        result = 31 * result + personalNotesId;
        result = 31 * result + content.hashCode();
        return result;
    }
}
