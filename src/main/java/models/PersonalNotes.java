package models;

public class PersonalNotes {
    private int id;
    private String writtenBy;
    private int entryId;
    private String content;

    public PersonalNotes(String writtenBy, int entryId, String content) {
        this.writtenBy = writtenBy;
        this.entryId = entryId;
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

    // equals and hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalNotes that = (PersonalNotes) o;

        if (id != that.id) return false;
        if (entryId != that.entryId) return false;
        if (!writtenBy.equals(that.writtenBy)) return false;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + writtenBy.hashCode();
        result = 31 * result + entryId;
        result = 31 * result + content.hashCode();
        return result;
    }
}
