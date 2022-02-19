<h1 align="center">
	<img
		alt="wirez"
		src="https://i.postimg.cc/yxhZZWT8/image-2022-02-18-164450.png">
</h1>

<h3 align="center">
  WireZ is a Minecraft platform diagnostics tool used to collect system statistics and interactive database information
</h3>

### What's WireZ job?
<h1>
WireZ has two jobs as a whole:

* **System Diagnostics Tool**: Gather system statistics of your platform
* **Interactive Database Handler**: Multi database interactive system to handle databases

### :electric_plug: System Diagnostics Tool

WireZ system diagnostics tool allows you to gather system statistics of your platform. WireZ system diagnostics tool can be broken up in these components:

* **CPU Profiler** View performance aspects of a platform.
* **Memory Summarizer** View memory aspects of a platform.
* **Thread Information** View the use of threads of a platform.
* **Health Format Graphs** View the health of a certain system component.

### :electric_plug: CPU Profiler:

WireZ CPU profiler is used to collect the use of system performance.

*Benefits:

* **Lightweight** Will have zero impact on your platform's performance
* **Collection** Collects real time and real time averages of both system and processed CPU
* **Format** Show real time percentages with a readable chat graph

### 🔌 Memory Summarizer:

WireZ Memory summarizer gives you a clue of how your platform's memory is running.

* **Disk Space** Shows used disk space out of the total disk space a platform has. 
* **RAM Usage** Shows used RAM out of the total RAM a platform has.  
* **Heap Summary** Full analysis of your platform's memory. JVM interaction to see the amount of bytes and instances each Java class takes up.
* **Heap Summary Format** Given information of a Heap Summary as an output comes back as a https request paste with a key. 

### 🔌 Thread Information: 

WireZ Thread Information aspect allows you to inspect a platforms use of threads, and to perform thread dumps.

* **Information Given** Using ThreadMXBean the information give is the threads running on a platform and extra details (id, name, state, etc)
* **Thread Dumps** Gives off stack traces of the plaforms use of threads.
* **Format** Given information of Thread Information and Thread Dumps as an output comes back as a https request paste with a key. 

### 🔌 Health Graphs:

Chat formatted graphs with a use of 0-100 with specfic color codes.

* **Support** These graphs support CPU use, Disk Space, and RAM usage. 
