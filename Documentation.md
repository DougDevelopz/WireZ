<h1 align="center">
	<img
		alt="wirez"
		src="https://i.postimg.cc/yxhZZWT8/image-2022-02-18-164450.png">
</h1>

## 🔌 Commands

WireZ has two different breakdowns of Commands:

## 🔌 System Commands
* /wirez cpu (provides realtime CPU results and realtime CPU averages of both system and processed CPU)
* /wirez memory (provides both disk space, and ram usage in this format "used / total")
* /wirez heapdump (provides a paste with a key with a full analysis of your platforms memory. JVM interaction to see the amount of bytes and instances each Java class takes up.)
* /wirez threadinfo (provides a paste with a key with full details of what threads are in use for the platform WireZ is running on)
* /wirez threaddump (provides a paste with a key with thread dump stacktraces. becareful when using this as this could result in your platform freezing due to syncs locking)

## 🔌Database Commands
* /wirez connect <host> <port> <database> <user> <password> <timeout> <poolSize> (connects to a specified database if credentials of that database match up. you can connect to multiple databases but attempts to connecting to a database in use will be blocked.)
* /wirez disconnect <database> (disconnect you from the specified database in an established way)
* /wirez dblist | ./wirez dblist <target> (provides you a list of the database you're connected to or databases another player is connected to)
* /wirez dumptable <database> <table> <fileName> (replicates a table's data into a csv file and can be located in dblogs)
* /wirez listtables <database> (provides a paste with a key of what tables a database is made up of)

## 🔌 Permissions

WireZ has only two permission nodes: 
	
* wirez.sysadmin (permission to interact with system commands)
* wirez.dbadmin (permission to interact with database commands)
	

