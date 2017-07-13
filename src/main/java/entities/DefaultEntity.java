package entities;

/**
 * Created by Romaaan on 27/06/2017.
 */
public abstract class DefaultEntity {

    protected abstract Object getKeyComparer();

    public abstract boolean isEmpty();

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DefaultEntity && ((DefaultEntity)obj).getKeyComparer().equals(this.getKeyComparer());
    }

    @Override
    public int hashCode() {
        return getKeyComparer().hashCode();
    }
}
