# Sonique Awesome IntelliJ Plugin

[![Build](https://github.com/team-sonique/intellij-plugin/workflows/Build/badge.svg)][gh:build]
[![Version](https://img.shields.io/jetbrains/plugin/v/14788-sonique-awesome-utils.svg)][jb:plugin]
[![Downloads](https://img.shields.io/jetbrains/plugin/d/14788-sonique-awesome-utils.svg)][jb:plugin]

<!-- Plugin description -->
IntelliJ Plugin with useful features, for moving between open projects and generating accessor methods and builder injectors.

* _Open projects_ - easy switching between open projects, similar to inbuilt _Recent Files_, essential for teams that work on multiple projects simultaneously. Mapped to 'control alt P' by default.

* _Generate Popup additions_
    * _Named Accessors_ - generate accessors named after the field without 'get' prefix, i.e. #myField() rather than #getMyField()
    * _Builder Methods_ - generate 'setter' methods in Builder style: 
        * with(field) for tiny types
        * withName(field) i.e. #withMyField(value)

<!-- Plugin description end -->


[gh:build]: https://github.com/team-sonique/intellij-plugin/actions?query=workflow%3ABuild
[jb:plugin]: https://plugins.jetbrains.com/plugin/14788-sonique-awesome-utils
