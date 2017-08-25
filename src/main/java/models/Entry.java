package models;

public class Entry {
    private String commonName;
    private int id;

    public Entry(String commonName) {
        this.commonName = commonName;
    }

    // getters and setters

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // equals and hashcode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (id != entry.id) return false;
        return commonName.equals(entry.commonName);
    }

    @Override
    public int hashCode() {
        int result = commonName.hashCode();
        result = 31 * result + id;
        return result;
    }
}
