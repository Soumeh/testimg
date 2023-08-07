# Multiple Splash Files

Splash files will now load from every namespace that has a splash file.

This allows you to add splash texts without replacing texts already in the game.

!!! tip
    If you want to remove all of the vanilla splash texts, just create a empty `minecraft:texts/splashes.txt` file.

# JSON Splash Files

Splash files can also now be JSON files, which take a list of JSON text elements as splash texts. Simply name your file `splashes.json` instead of `splashes.txt`.

!!! bug
    Splash texts currently do not support icons for unknown reasons.


![Example](https://media.discordapp.net/attachments/1053402759031963748/1122460209072312381/image.png)

# TLDR

```json title="texts/splashes.json"
[
    "This is my new splash text!", // (1)!
    [{"translate": "item.minecraft.diamond"}, {"text": "!"}] // (2)!
]
```

1. This line would show up as `This is my new splash text!`
2. This line would show up as `Diamond!` if your in-game language is set to English.

<!---->


- Multiple resource packs splash texts in one pool.
- JSON splash files with translation support.
- JSON splash files take priority over regular splash files.

# To-Do

- Make the title screen splash text render `Text` objects instead of strings.