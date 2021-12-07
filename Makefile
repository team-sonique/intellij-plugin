.DEFAULT_GOAL := build

.PHONY: clean
clean:
	gradlew clean

.PHONY: build
build:
	gradlew check runInspections build
	cp ./build/libs/sonique-intellij-plugin* ~/Downloads/.
