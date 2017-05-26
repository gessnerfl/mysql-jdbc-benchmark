# mysql-jdbc-benchmark
a Simple MySQL JDBC benchmark to test insert + delete performance of a MySQL system.
The benchmark will run n iterations where it will insert m records in a test database.
Afterwards the database table will be cleaned (all records will be deleted in a single 
statement) to get clean environment for each execution
 
The number of iterations (n) and the number of records which will be inserted (m) can be 
configured.

The tool will create a report which will summarize the total duration and the average duration
to insert m records. The report will also contain a report for each iteration.

## Configuration

```
spring.datasource.url=jdbc:mysql://localhost:3306/temp   #JDBC url of the datasource
spring.datasource.username=root                          #User to connect to the database
spring.datasource.password=Test1234                      #Password of the database user

de.gessnerfl.docker.mysqlBenchmark.iterations=5                #The number of iterations (n) to be executed
de.gessnerfl.docker.mysqlBenchmark.insertsPerIteration=100000  #The number of inserts per iteration (m)
```

For more details regarding the configuration of the JDBC datasource please consult the spring 
boot documentation

## Tune Docker OSX

Set sync mode to none:
```
echo none > ~/Library/Containers/com.docker.docker/Data/database/com.docker.driver.amd64-linux/disk/on-flush
```

Script to do the configuration:
```
#!/bin/bash

set -e
cd ~/Library/Containers/com.docker.docker/Data/database
git reset --hard

echo -n "Current full-sync-on-flush setting: "
cat ./com.docker.driver.amd64-linux/disk/full-sync-on-flush
echo

echo -n "Current on-flush setting: "
cat ./com.docker.driver.amd64-linux/disk/on-flush
echo

echo -n false > ./com.docker.driver.amd64-linux/disk/full-sync-on-flush
echo -n none > ./com.docker.driver.amd64-linux/disk/on-flush

git add ./com.docker.driver.amd64-linux/disk/full-sync-on-flush
git add ./com.docker.driver.amd64-linux/disk/on-flush
git commit -s -m "disable flushing"

echo "Docker should restart by itself now."
```

```
~/.../com.docker.driver.amd64-linux/disk $ docker -v
Docker version 17.03.0-ce, build 60ccb22
~/.../com.docker.driver.amd64-linux/disk $ ps aux | grep docker.hyperkit
booi             10428   1.4  0.9  6912772 144912   ??  S    10:13AM   0:06.73 /Applications/Docker.app/Contents/MacOS/com.docker.hyperkit -A -m 4096M -c 4 -u -s 0:0,hostbridge -s 31,lpc -s 2:0,virtio-vpnkit,uuid=1f915cd0-018b-4aa0-9b90-bd093b15a288,path=/Users/booi/Library/Containers/com.docker.docker/Data/s50,macfile=/Users/booi/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/mac.0 -s 3,virtio-blk,file:///Users/booi/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/Docker.qcow2?sync=none&buffered=1,format=qcow,qcow-config=discard=false;compact_after_unmaps=0 -s 4,virtio-9p,path=/Users/booi/Library/Containers/com.docker.docker/Data/s40,tag=db -s 5,virtio-rnd -s 6,virtio-9p,path=/Users/booi/Library/Containers/com.docker.docker/Data/s51,tag=port -s 7,virtio-sock,guest_cid=3,path=/Users/booi/Library/Containers/com.docker.docker/Data,guest_forwards=2376;1525 -l com1,autopty=/Users/booi/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/tty,log=/Users/booi/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/console-ring -f kexec,/Applications/Docker.app/Contents/Resources/moby/vmlinuz64,/Applications/Docker.app/Contents/Resources/moby/initrd.img,earlyprintk=serial console=ttyS0 com.docker.driver="com.docker.driver.amd64-linux", com.docker.database="com.docker.driver.amd64-linux" ntp=gateway mobyplatform=mac vsyscall=emulate page_poison=1 panic=1 -F /Users/booi/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/hypervisor.pid
```
