package brooklyn.entity.rebind.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import brooklyn.BrooklynVersion;
import brooklyn.mementos.Memento;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

public abstract class AbstractMemento implements Memento, Serializable {

    private static final long serialVersionUID = -8091049282749284567L;

    protected static abstract class Builder<B extends Builder<?>> {
        protected String brooklynVersion = BrooklynVersion.get();
        protected String id;
        protected String type;
        protected String displayName;
        public Map<String, Object> customProperties = Maps.newLinkedHashMap();
        
        @SuppressWarnings("unchecked")
        protected B self() {
            return (B) this;
        }
        public B from(Memento other) {
            brooklynVersion = other.getBrooklynVersion();
            id = other.getId();
            type = other.getType();
            displayName = other.getDisplayName();
            customProperties.putAll(other.getCustomProperties());
            return self();
        }
        public B brooklynVersion(String val) {
            brooklynVersion = val; return self();
        }
        public B id(String val) {
            id = val; return self();
        }
        public B type(String val) {
            type = val; return self();
        }
        public B displayName(String val) {
            displayName = val; return self();
        }
        public B customProperties(Map<String,?> vals) {
            customProperties.putAll(vals); return self();
        }
    }
    
    private String brooklynVersion;
    private String id;
    private String type;
    private String displayName;
    private Map<String,Object> customProperties;
    
    // for de-serialization
    protected AbstractMemento() {
    }

    // Trusts the builder to not mess around with mutability after calling build()
    protected AbstractMemento(Builder<?> builder) {
        brooklynVersion = builder.brooklynVersion;
        id = builder.id;
        type = builder.type;
        displayName = builder.displayName;
        customProperties = Collections.unmodifiableMap(builder.customProperties );
    }

    @Override
    public String getBrooklynVersion() {
        return brooklynVersion;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getType() {
        return type;
    }
    
    @Override
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public Object getCustomProperty(String name) {
        return customProperties.get(name);
    }
    
    @Override
    public Map<String, ? extends Object> getCustomProperties() {
        return customProperties;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("type", getType()).add("id", getId()).toString();
    }
}
