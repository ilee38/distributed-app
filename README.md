# distributed-app
<h1><strong>Distributed Objects-based application using Java RMI.</strong></h1>

<h3>Project	Description:</h3>
You	are tasked	to	design	Mybooks.com - the	World's	smallest	book	store.	
Mybooks.com only	has	the	following four	books	for	sale:	

• How	to	be	good	at CS5523.

• RPCs	and RMI in	distributed	systems.

• Why	go	to	the	graduate	school.

• How	to	survive the graduate	school.

Implement	a	client-server	program	using	sockets/RPCs/RMIs.	
There	is	only	one	server and multiple	clients. The	clients	and	servers	may	run	on	
different	machines.	

The	server	is	going	to	maintain	the	information	about	these	four	books.
For	each	book,	it	maintains	the	number	of available items	in	stock,	the	cost	and	topic	
of	each book.	Currently	all	books belong	to	one	of	two	topics:	distributed	systems	
(first	two	books)	and	graduate	school	(the	last	two	books).	The	server	also	
maintains	a	list	of	all orders	received	for	the	books,	thus	it can	calculate	how	many	
requests that	it	have	received,	and	how	many items	are	still	available.	More	
information can	be	seen	in	the	following:

The	server	supports	the	following services:	

• search(topic) : allows	the	user	to	specify	a	topic	and	returns	all	books
belonging	to	this	topic.	For	each	book,	it	will	show different	book’s
item_number	(e.g.	an internal	id).

• lookup(item_number)	: allows	an	item_number	to	be	specified	and	returns	
details	of	all	items,	such	as	number	of	available	items	in	stock,	cost and	topic

• order(item_number)	: allows	the	user	to	buy	a	book	with	the	specified	
item_number.	If	there	are	some	available	items	on	a	book,	it	will	return	some	
successful	information.	Otherwise,	it	will	return	the	fail	information.	

The	server	also	supports	some	report	functionalities	so	that	we	can	use	it	to	
improve	our	service	or	performance	in	the	future.		For	example,	if	we	have	too	many	
requests	on	a book,	we	can	increase	the	number	of	available	items	for	the	next	year.	
Also,	it	can	report	the	performance	to	serve	each	request,	thus	this	can	provide	
some	information	of	how	to	improve	the	performance	in	the	future.	

• reportRequestsNumber	(service):	allows	the	user	to	query	that	how	many	
requests	on	each	service	have	been	received.	

• reportGoodOrders():	tell	users	how	many	books	have	been	sold	successfully	
starting	from	the	beginning.

• reportFailedOrders():	report	how	many	orders	are	failed	in	total.

• reportServicePerformance(service):	report	the	average	performance	for	
serving	a	request	for the	specific	service.

Each	client may perform	search,	lookup	or buy	operations.	The	server	should	be	
able	to	accept	multiple	requests	at	the	same	time.	This	could	be	easily	done	using	
threads.	Be	aware	of	the	thread	synchronizing	issues	to	avoid	inconsistency	or	
deadlock	in	your	system.	For	instance,	the server	should	be	able	to	process	multiple	
concurrent	buy requests	and	decrementing	the	number	of	items	in	stock	should	be	
done	using	synchronization.	
