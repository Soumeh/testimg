package com.sindercube.iconic.emote;

import com.sindercube.iconic.cache.MappedCache;
import com.sindercube.iconic.cache.SingleCache;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmoteStorage {
    public static EmoteStorage INSTANCE = new EmoteStorage();
    public MappedCache<String, Text> emotes = new MappedCache<>();
    public SingleCache<List<String>> suggestions = new SingleCache<>(new ArrayList<>());
    public void addEmote(String key, Text value) {
        emotes.put(key, value);
    }

    public Text get(String key) {
        return this.emotes.get(key);
    }
    public List<String> getSuggestions() {
        if (this.suggestions.isEmpty()) {
            List<String> suggestions = new ArrayList<>();
            for (String key : this.emotes.map.keySet()) {
                Text value = this.emotes.get(key);
                suggestions.add(":" + key + ": " + value.getString());
            }
            this.suggestions.value.addAll(suggestions);
        }
        return this.suggestions.value;
    }
    public static final Pattern EMOTE_PATTERN = Pattern.compile(":(\\w+?):");
}
