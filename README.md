# COMP3607_Jeopardy_Project
Java-based multiplayer Jeopardy game simulation 
Supports 2-4 players


----------------- Reading data files ----------------------

Sample CSV, Json and Xml files are included in src\resources.
I used their respective relative paths to load the questions.

If other files are used, they can either be added to the 
resource folder or their absolute path can be used as 
input when prompted.

Use the following commands to setup the program:

-> mvn dependency:resolve
-> mvn clean compile exec:java "-Dexec.mainClass=com.jeopardyProject.GameRunner"
-> mvn javadoc:javadoc
-> mvn clean install
