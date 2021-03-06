# 718 Runescape Base
<img src="https://i.gyazo.com/3043a41874f6a791cb2899cb36e794f1.png">
<p>This 718 client/server build is aimed to be a semi-vanilla base for public use. This source encourages creating a vanilla core first, then adding custom features later.
It is based on Matrix 718 but is put together as one client/server IntelliJ project. The cache is interchangeable and there are no anti-leech functions.</p>

You are welcome to leech, join in on development or make a pull request! If you just want the original Matrix release working in IntelliJ then clone the first few commits.

<h2>Setup Instructions</h2>
<p>I recommend using Maven to build, IntelliJ to edit & Java JDK 8 is just required for editing the source, but you can also clone to Eclipse or build in Gradle or others. The client mostly requires Java 8 so the release on this page will be a windows .exe installer which includes Java 8. For Mac youll need to use a jar with Java 8 installed. Any other Java version works but is not optimal.</p> 
<p>Here are the steps:</p>
<ol>
<li>Fork the repo
<li>Clone the repo as "an existing source from version control" in IntelliJ. Copy and paste the repo URL.
<li>Port forward 43594 or whichever port you choose. It is in Applet.java & Settings.java
<li>Go to https://whatismyipaddress.com/ and place that ip address into the Applet.java. The server automatically hosts on the computer you run it on.
<li>Open the project in IntelliJ and create 2 modules, the Client & the Server. Follow the standard Maven layout src->main->java in each, marking java as the source directory.
Note the pom.xml is created for both client & server individually, each module has its own pom
<li>Download cache.zip from Mega: https://mega.nz/file/u1cm2BgB#y9ud8X6Wh_1IrEWBIkQmyONqb7PQuE_qbWNYlTHqgkA
<li>Unzip cache.zip to the server-module->data folder. It should just be Matrix Server->data->cache while the rest of the server folders are on the same level as data. Here is a picture:<br> 
<img src="https://i.gyazo.com/2e95faffe500e88dd3eb5658efe717b7.png">
<li>Go to the server folder and install maven dependency jar files onto your Maven local repo on the server by running terminal commands that look like these. If you have never installed jars to your local repo 
just make sure to follow a guide online and have the everything in the command matching the pom.xml dependency info in the dependency tags.
<br>```<br>
mvn install:install-file -Dfile=[folder_with_dependencies]\FileStore.jar -DgroupId=com.warbycode -DartifactId=FileStore -Dversion=1.0.0 -Dpackaging=jar<br>
mvn install:install-file -Dfile=[folder_with_dependencies]\netty-3.6.5.Final.jar -DgroupId=com.warbycode -DartifactId=netty-3.6.5.Final -Dversion=1.0.0 -Dpackaging=jar
<br>```<br>
<li>Run Server.java as the server
<li>Run Applet.java as the client
<li>You should be able to enter into a local server
</ol>
 <h2>Additional Info</h2>
 -You can create a .jar of the server and client using Maven<br>
 -You can then run the server jar in a remote VPS to make it live. Remember to point the client to your live jar & also move the data folder to the same folder 
 you place your server jar into so you can leech.
