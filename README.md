<div align="center"><img height="150" src="icon/icon.png" width="150"/>

# Awesome Sheep Swell

[![GitHub license](https://img.shields.io/github/license/Wulian233/AwesomeSheepSwell?style=flat-square)](LICENSE)
[![CurseForge-badge](https://img.shields.io/curseforge/dt/1188071?style=flat-square&logo=curseforge&label=CurseForge)](https://www.curseforge.com/minecraft/mc-mods/awesome-sheep-swell)
![Modrinth Downloads](https://img.shields.io/modrinth/dt/awesome-sheep-swell?label=Modrinth&logo=Modrinth&style=flat-square)

This mod has improved the sheep's wool, which is very useful and fun. The wool thickens as the sheep eats grass, and
you'll get more wool when sheared!

<a href="https://modrinth.com/project/awesome-sheep-swell/">
<img alt="modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg">
</a>
<a href="https://www.curseforge.com/minecraft/mc-mods/awesome-sheep-swell">
<img alt="curseforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg">
</a>
<img alt="architectury-api" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3.2.0/assets/cozy/requires/architectury-api_vector.svg">
<img alt="cloth-config-api" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3.2.0/assets/cozy/requires/cloth-config-api_vector.svg">
</div>

![](https://cdn.modrinth.com/data/DXfVJLBA/images/10f8b5e0714d65320dfef09b21eb9f2604a8cd1a.gif)

## 📖 What's this mod?

Awesome Sheep Swell (ASS) is the unofficial Architectury port
of [Chonky Sheep](https://github.com/suppergerrie2/ChonkySheep), now available for Fabric and Forge.

This mod has improved the sheep's wool, which is very useful and fun. The wool thickens as the sheep eats grass, and
you'll get more wool when sheared!

This mod adds a new NBT `thickness` to sheep, which you can summon with a thickness of 20
via `/summon minecraft:sheep ~ ~ ~ {thickness:20}`.

## ✅ Features                                                                                                                                                                                                                       

- The wool thickness increases as the sheep eats grass.
- Shearing sheep yields extra wool, calculated as 0-2 additional wool based on the sheep's wool thickness.
- Configurable maximum wool thickness.
- Sheep spawn with a random thickness from 1 to 10, with decreasing weight as thickness increases.

## 📦 Compatibility

This mod reimplements the rendering method for sheep, so some effects from other mods that modify sheep
via Mixin might not be displayed as intended. Instead, they will be overridden by this mod, but it will
not cause any crashes.

Mods known to have their effects overridden by this mod: [Sheep Consistency (Forge)](https://www.curseforge.com/minecraft/mc-mods/sheep-consistency-forge) and [Sheep Consistency](https://www.curseforge.com/minecraft/mc-mods/sheep-consistency)

## ✨ Screenshots

![](icon/gallery.png)

![](icon/screenshot.png)

![](icon/preview.mp4)

## 👀 License

This mod is licensed under the [GPL3 license](LICENSE.md).

**Credits**: Original author [suppergerrie2](https://github.com/suppergerrie2/).
