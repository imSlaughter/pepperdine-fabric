package pepper.dine.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import pepper.dine.PepperDine;
import pepper.dine.client.command.CommandManager;
import pepper.dine.client.features.api.BindSetting;
import pepper.dine.client.features.api.BooleanSetting;
import pepper.dine.client.features.api.ModeSetting;
import pepper.dine.client.features.api.MultiBooleanSetting;
import pepper.dine.client.features.api.RunSetting;
import pepper.dine.client.features.api.Setting;
import pepper.dine.client.features.api.SliderSetting;
import pepper.dine.client.modules.api.Module;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path file = FabricLoader.getInstance().getConfigDir().resolve("pepperdine").resolve("config.json");
    private boolean loading;

    public void load() {
        if (!Files.exists(file)) {
            save();
            return;
        }

        loading = true;
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            if (root == null) {
                return;
            }

            CommandManager commandManager = PepperDine.getInstance().getCommandManager();
            JsonElement prefix = root.get("prefix");
            if (commandManager != null && prefix != null && prefix.isJsonPrimitive()) {
                commandManager.setPrefix(prefix.getAsString());
            }

            JsonObject modules = root.getAsJsonObject("modules");
            if (modules == null) {
                return;
            }

            for (Module module : PepperDine.getInstance().getModuleRepository().getModules()) {
                JsonObject moduleObject = modules.getAsJsonObject(module.getName());
                if (moduleObject == null) {
                    continue;
                }

                JsonElement enabled = moduleObject.get("enabled");
                if (enabled != null && enabled.isJsonPrimitive()) {
                    module.applySavedEnabled(enabled.getAsBoolean());
                }

                JsonElement key = moduleObject.get("key");
                if (key != null && key.isJsonPrimitive()) {
                    module.setKey(key.getAsInt());
                }

                JsonObject settings = moduleObject.getAsJsonObject("settings");
                if (settings == null) {
                    continue;
                }

                for (Setting<?> setting : module.getSettings()) {
                    loadSetting(setting, settings.get(setting.getName()));
                }
            }
        } catch (Exception ignored) {
        } finally {
            loading = false;
        }
    }

    public void save() {
        try {
            Files.createDirectories(file.getParent());

            JsonObject root = new JsonObject();
            CommandManager commandManager = PepperDine.getInstance().getCommandManager();
            root.addProperty("prefix", commandManager != null ? commandManager.getPrefix() : "^");

            JsonObject modules = new JsonObject();
            for (Module module : PepperDine.getInstance().getModuleRepository().getModules()) {
                JsonObject moduleObject = new JsonObject();
                moduleObject.addProperty("enabled", module.isEnabled());
                moduleObject.addProperty("key", module.getKey());

                JsonObject settings = new JsonObject();
                for (Setting<?> setting : module.getSettings()) {
                    JsonElement value = saveSetting(setting);
                    if (value != null) {
                        settings.add(setting.getName(), value);
                    }
                }

                moduleObject.add("settings", settings);
                modules.add(module.getName(), moduleObject);
            }

            root.add("modules", modules);

            try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            }
        } catch (IOException ignored) {
        }
    }

    public void requestSave() {
        if (!loading) {
            save();
        }
    }

    private JsonElement saveSetting(Setting<?> setting) {
        if (setting instanceof BooleanSetting booleanSetting) {
            return GSON.toJsonTree(booleanSetting.getValue());
        }
        if (setting instanceof SliderSetting sliderSetting) {
            return GSON.toJsonTree(sliderSetting.getValue());
        }
        if (setting instanceof ModeSetting modeSetting) {
            return GSON.toJsonTree(modeSetting.getValue());
        }
        if (setting instanceof BindSetting bindSetting) {
            return GSON.toJsonTree(bindSetting.getValue());
        }
        if (setting instanceof MultiBooleanSetting multiBooleanSetting) {
            JsonObject object = new JsonObject();
            for (BooleanSetting value : multiBooleanSetting.getValue()) {
                object.addProperty(value.getName(), value.getValue());
            }
            return object;
        }
        if (setting instanceof RunSetting) {
            return null;
        }
        return GSON.toJsonTree(setting.getValue());
    }

    private void loadSetting(Setting<?> setting, JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return;
        }

        if (setting instanceof BooleanSetting booleanSetting && element.isJsonPrimitive()) {
            booleanSetting.setValue(element.getAsBoolean());
            return;
        }
        if (setting instanceof SliderSetting sliderSetting && element.isJsonPrimitive()) {
            sliderSetting.setValue(element.getAsFloat());
            return;
        }
        if (setting instanceof ModeSetting modeSetting && element.isJsonPrimitive()) {
            String value = element.getAsString();
            if (modeSetting.getModes().contains(value)) {
                modeSetting.setValue(value);
            }
            return;
        }
        if (setting instanceof BindSetting bindSetting && element.isJsonPrimitive()) {
            bindSetting.setValue(element.getAsInt());
            return;
        }
        if (setting instanceof MultiBooleanSetting multiBooleanSetting && element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            for (BooleanSetting value : multiBooleanSetting.getValue()) {
                JsonElement entry = object.get(value.getName());
                if (entry != null && entry.isJsonPrimitive()) {
                    value.setValue(entry.getAsBoolean());
                }
            }
        }
    }
}
