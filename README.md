KUKA in-memory data grid concept
=====

#### Key principles:

* All data in memory. It makes predictable time for all requests, needs for realtime.
* MapReduce support. Even for memory it works faster than random access
* Write-behind to the flash disk for durability
* Needs to read all data from the flash disk on startup
* Explicit compression: network, off-heap memory storage, flash storage
* Separation of the data schema from Java code, realtime schema updates
* Server side stateless application with realtime start/stop/migrations
* Data model access based on Regions: replicated, partitioned.
* Colocated and linked regions
* Asyncronous API and event loop
* Minimized GC work
* JDK >= 1.7 (for asynchronous file API)

#### Requirements:

As for realtime IMDG we have SLA requirement for all requests. This lead to the
solution that will have all data in memory. We can not keep 10% or 20% in memory only
and store the rest in the disk. Disk almost 1000 times slower than memory access,
so in this case we need to be far away from general DB architectures.

Thread management is also very expensive solution. Almost all IMDGs in the market works
like Apache HTTPD. On each request is leases thread from the thread pool.
For real-time we need to build something near Nginx/NodeJs, where we will have N worker
threads equals N CPUs in system and event loops in each thread. We expecting that this
architecture is faster for real-time processing than legacy based on thread leasing/releasing 
from the thread pool.

GC work is very important part for realtime systems. We meed to minimize its working time,
so the solution will be to use off-heap memory as much as possible.

Compression helps to place more objects in memory and general CPU has power to handle
it on the fly. It also decreases traffic to the disk and improves average performance
of the system. It will be enabled by default for all objects in memory and for network channels.

Uptime of the grid have to be near 100%, but some business logic needs to be updated
on server side. For this purpose we need to support stateless applications that will
work on server side, can be started, stopped, can serve some ports and so on. But
the grid at same time will be a stateful platform for application.
