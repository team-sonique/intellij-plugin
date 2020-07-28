.DEFAULT_GOAL := build

.PHONY: clean
clean:
	gradlew clean

.PHONY: build
build:
	gradlew check build
	cp ./build/libs/sonique-intellij-plugin* ~/Downloads/.
