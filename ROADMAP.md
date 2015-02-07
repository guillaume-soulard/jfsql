#ROADMAP

##2.0

### Write operations
This version will support write operations (Copy, Move, Delete, Create, Rename)

All of those news functionnalities will use previous version features. For exemple you will be able to delete files retured be à GET query. Such query will look like :

```
/* this query will delete all files in current directory having content contains any 4 didgits number*/

delete file in('.') having content match '[0-9]{4}';
```

###Events
Event feature will be also add to listen for file system changes for exemple files and directory creation, deletion, etc. Exemple : 

```
when file in('.') having name like '.log' changed then
	copy this to '/home/user/save/logs';
end when;
```

###Transactions

Will support transaction on write operations on a specific item (file or directory).

Exemple : 
```
begin transation on '/home/user/directory';

delete '/home/ogama/directory;
// /home/ogama/directory not exists

rollback;
// /home/ogama/directory exists 
```

