# Why not Keep Your FPS up?

You probably see this warning below when you are writing Kotlin mixins.

> [!WARNING]
>
> Mixins should be written in Java. See this [Mixin issue](https://github.com/SpongePowered/Mixin/issues/245).

Why should you write mixins in Java? Why not Kotlin? 

Here I'm not going to introduce you some hacks or workarounds.<br>
I'm just trying to write some mixins in Kotlin and see how if it works.

So I mixined vanilla Minecraft's `Options` and `FramerateLimitTracker` classes via Kotlin.<br>
And it really works. 