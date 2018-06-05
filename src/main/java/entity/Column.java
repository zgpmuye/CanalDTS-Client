package entity;

public class Column {
    private String name;
    private String value;
    private boolean update;
    private boolean isKey;
    private boolean isNull;
    private String columnType;
    private int index;

    public Column() {
    }

    public Column(String name, String value, boolean update, boolean isKey, boolean isNull, String columnType, int index) {
        this.name = name;
        this.value = value;
        this.update = update;
        this.isKey = isKey;
        this.isNull = isNull;
        this.columnType = columnType;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", update=" + update +
                ", isKey=" + isKey +
                ", isNull=" + isNull +
                ", columnType='" + columnType + '\'' +
                ", index=" + index +
                '}';
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
