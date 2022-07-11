#/bin/bash
cur_dir=$(cd "$ (dirname "$0")"; pwd)
all:
	/usr/lib/jvm/jdk-11.0.10+9/bin/java -Dmaven.multiModuleProjectDirectory=${cur_dir} -Dmaven.home=/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3 -Dclassworlds.conf=/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3/bin/m2.conf -Dmaven.ext.class.path=/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven-event-listener.jar -javaagent:/usr/local/idea-IU-213.6777.52/lib/idea_rt.jar=35783:/usr/local/idea-IU-213.6777.52/bin -Dfile.encoding=UTF-8 -classpath /usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3/boot/plexus-classworlds.license:/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3/boot/plexus-classworlds-2.6.0.jar org.codehaus.classworlds.Launcher -Didea.version=2021.3.2 install -P $(active)
	docker build -t taozi-be:$(ver) .
	docker save -o taozi-be.$(ver).tar taozi-be:$(ver)

clean:
	docker image rm taozi-be:$(ver)
	rm taozi-be.$(ver).tar
	/usr/lib/jvm/jdk-11.0.10+9/bin/java -Dmaven.multiModuleProjectDirectory=${cur_dir} -Dmaven.home=/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3 -Dclassworlds.conf=/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3/bin/m2.conf -Dmaven.ext.class.path=/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven-event-listener.jar -javaagent:/usr/local/idea-IU-213.6777.52/lib/idea_rt.jar=39707:/usr/local/idea-IU-213.6777.52/bin -Dfile.encoding=UTF-8 -classpath /usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3/boot/plexus-classworlds.license:/usr/local/idea-IU-213.6777.52/plugins/maven/lib/maven3/boot/plexus-classworlds-2.6.0.jar org.codehaus.classworlds.Launcher -Didea.version=2021.3.2 clean -P $(active)
