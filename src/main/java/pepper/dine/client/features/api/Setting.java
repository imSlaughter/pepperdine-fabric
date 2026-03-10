package pepper.dine.client.features.api;

import lombok.Getter;
import lombok.Setter;
import pepper.dine.PepperDine;

import java.util.Objects;
import java.util.function.Supplier;

    @Getter
    @Setter
    public abstract class Setting<T> {
        protected String name;
        protected T value;
        protected Supplier<Boolean> visibilityCondition = () -> true;
        protected Runnable action;

        public Setting(String name) {
            this.name = name;
        }

        public Setting<T> setVisible(Supplier<Boolean> condition) {
            this.visibilityCondition = condition;
            return this;
        }

        public void runAction() {
            if (this.action != null) {
                action.run();
            }
        }

        protected boolean sameValue(T newValue) {
            return Objects.equals(value, newValue);
        }

        protected void changed() {
            if (PepperDine.getInstance() != null) {
                PepperDine.getInstance().requestConfigSave();
            }
        }

        public Setting<T> onAction(Runnable action) {
            this.action = action;
            return this;
        }

        public boolean isVisible() {
            return visibilityCondition.get();
        }

        public abstract Setting<T> value(T value);
    }
