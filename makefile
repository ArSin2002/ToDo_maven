# Variables
MVN = mvn
JAR = java -jar
APP_JAR = target/todo-tracker-1.0-SNAPSHOT.jar
CONFIG_FILE = src/main/resources/config.yml

# Default target: Build and run
.PHONY: all
all: run

# Clean the project
.PHONY: clean
clean:
	$(MVN) clean

# Build the project
.PHONY: build
build:
	$(MVN) clean install

# Run the application
.PHONY: run
run: build
	$(JAR) $(APP_JAR) server $(CONFIG_FILE)

# Clean, build, and run in one command
.PHONY: rebuild-run
rebuild-run: clean run
