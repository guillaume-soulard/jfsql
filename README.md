#JFSQL (Java File System Query Language)

JFSQL is a Java library for querying the file system. This library provide a query language to allow external uses for exemple as shell command.

```shell
java -jar jfsql.jar "<query>"
```

##Maven
###Project dependency
Work in progress
###Build jar

Run the following command to build a standalone jar library

```
mvn clean compile assembly:single
```
Be careful, this command don't run any tests. To do that you have to run a 'clean install' or 'clean verify'

##Usage
Sample usage in a Java application :
```java
Query query = Queryfactory.newQuery("get file or directory in('/home/Bob)");
List<Comparable> results = query.execute();
```

##Syntaxe

General syntax :

```
get [<limit>] [<distinct>] <attribute> in (<path> [, <path>]*) [deep <striclyPositiveNumber> | infinite] [having <conditions>] [sort by <property> ascending | descending]
```

###Get clause

####Attributes
Select the file attribute to return as a result of the query.

* name : get the name of files or directories
* owner : get the owner of files or directories
* path : get the path
* parent : get the parent name
* size : get the size (byte)
* content : select the content (only on files)
* last_update_date : get the last modification date (yyyy/mm/dd hh/mm/ss)
* last_access_date : get the last access date (yyyy/mm/dd hh/mm/ss)
* creation_date : get the creation date (yyyy/mm/dd hh/mm/ss)
* status : 'readable', 'writable', 'executable'
* type : 'file' or 'directory' 

Additional attributes for Java only : 
* file : select the Java File object (select only files and not directories)
* directory : select the Java File object (select only directories and not files)
* file or directory : select the Java File object (select files and directories)


####Restrictions
#####Limit
You can limit the number of results.

```
get 10 file in ('.')
```
This query return the first ten files in the current directory. 

#####Disinct
If the distinct key word is specified, the query ensure that the result contains no doubles values.

```
get distrinct name in ('.')
```
Get files or directories names with no double values.

###In clause
Sepcify with directories the query has to walk. Paths must be absolutes.

```
get file or directory in ('/home/Bob')
```
List all files or directories recursivly in Bob's home folder

You can specify as many locations as you want like so :

```
get file or directory in ('/home/Bob', '/home/Jane', '/home/William')
```

Instead of write the full path, you can specify the current directory by '.'

```
get file in ('.')
```
List all files in current directory

By default, jfsql search for files recursivly (all sub folder will be walked). It's possible to limit the depth like this :

```
get directory in ('/') deep 1
```
List all directories at the root (sub directories are not read)
Liste tout les dossiers à la racine du disque (les sous-dossie

###Having clause
This clause specify with kind of file or directory the query can select (This part look like where SQL clause).

Exemple : 
```
get file int ('.') having size > 0
```
Get all files in the current directory with a size greather than 0

You can filter on followings properties :

* name : name of the file or the directory
* owner : the owner of the file or the directory
* path : the path
* parent : the parent name
* size : the size in bytes
* content : the content (only on files)
* last_update_date : the last modification date (yyyy/mm/dd hh/mm/ss)
* last_access_date : the last access date (yyyy/mm/dd hh/mm/ss)
* creation_date : the creation date (yyyy/mm/dd hh/mm/ss)
* status : 'readable', 'writable', 'executable'
* type : 'file' or 'directory'

Supported comparators :
* '='
* '<>'
* '>'
* '>='
* '<'
* '<='
* 'in'
* 'like'
* 'between'

Supported logical operators : 

* and
* or
* not

Complex query exemple : 

```
get content in ('.') having parent = 'logs' or (size > 0 and name like '.log') and type = 'file'
```

####Sub queries
Having clause also support sub queries. The sub query has to be surround with '#{}' : 

```
get directory in ('/') deep 1 having size = #{get 1 size in ('/') deep 1 sort by size descending}
```
Get the bigest directory in root with sub query. Of course, this exemple can be written differently

###Sort by clause
Simple clause for sorting query result by a specific property.

```
get file in ('.') sort by name ascending
```
Get all files in current directory in alphabetic order

You can sort on followings properties :

* name : name of the file or the directory
* owner : the owner of the file or the directory
* path : the path
* parent : the parent name
* size : the size in bytes
* content : the content (only on files)
* last_update_date : the last modification date (yyyy/mm/dd hh/mm/ss)
* last_access_date : the last access date (yyyy/mm/dd hh/mm/ss)
* creation_date : the creation date (yyyy/mm/dd hh/mm/ss)
* status : 'readable', 'writable', 'executable'


