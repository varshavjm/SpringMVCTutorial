<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="${pageContext.request.contextPath}/resources/style.css"
    rel="stylesheet">
<!--  script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script-->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function addList(data, index) {
		//var JsonData = JSON.parse(data);
		var JsonData = JSON.parse( JSON.stringify(data).replace(/[^\x00-\x7F]/g, ""));
;
		var list = document.createElement('ol');
		for (i = 0; i < JsonData.length; i++) {

			var item = document.createElement('li');
			var a = document.createElement('a');
			a.className = "contentLinks";
			a.href = JsonData[i].link;
			a.text = " Click for more...";
			a.target = "#";
			item.appendChild(document.createTextNode(JsonData[i].query));
			item.appendChild(a);
			list.appendChild(item);
		}
		var collapse = document.getElementById("collapseChild"
				+ index.toString());
		collapse.innerHTML = "";
		collapse.appendChild(list);
	}

	function addLifeToCollapses(i) {
		// 	alert("Query "+$("#collapse"+i.toString()).text());
		$.ajax({
			url : 'http://localhost:8080/SpringWebApp/ajax',
			type : 'POST',
			data : {
				"query" : $(".collapse" + i.toString()).text()
			},
			success : function(data) {
				addList(data, i);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(xhr.status);
				alert(thrownError);
			}
		});
	}
</script>

</head>
<header><div id="heading" style="height:80px;background-color:#563d7c;">
			<h1 style="color:#ffffff;text-align:center;margin-top:0!important;padding:20px;">Assignment 3</h1>
			</div></header>
<body>

	<script type="text/javascript">
		$(window).bind("load", function() {
			//	alert("Window loading");
			for (i = 1; i <= 10; i++) {
				addLifeToCollapses(i);
			}
		});

		//For accordion
		$('.panel-heading a').on(
				'click',
				function(e) {
					if ($(this).parents('.panel').children('.panel-collapse')
							.hasClass('in')) {
						e.preventDefault();
						e.stopPropagation();
					}
				});
	</script>
	<div class="se-pre-con"></div>
	<div class="well">
		<h3>How Indexing is Done</h3>
		<ul>
			<i class="glyphicon glyphicon-hand-right"></i>  Crawling
				<ul>
					<li>Crawled Pages across Java WikiBooks and Oracle Java Docs Pages Recursively</li>
					<li>Extracted Paragraphs instead of Pages and stored them in
						files</li>
					<li>Removed duplicate paragraphs</li>
				</ul>
			
			<i class="glyphicon glyphicon-hand-right"></i>  Indexing and Searching
				<ul>
					<li>Designed Custom Analyzer that performs <b>Porter Stemming</b> on
						crawled content</li>
					<li><b>Removes stopwords</b></li>
					<li>Creates an in-memory index</li>
					<li>Remove stopwords from query</li>
					<li>Ranks query keywords using TF-IDF</li>
					<li>Retrieves top 10 paragraphs using weighted query and
						in-memory index</li>
				</ul>
		
			<i class="glyphicon glyphicon-hand-right"></i>  To view top 10 posts of a query, Click on the link associated with the question/answer
		</ul>
	</div>
	
	<div class="panel-group" id="accordion">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 ><i class="glyphicon glyphicon-question-sign"></i> Question</h3>
				<h4 class="panel-title">
					<a class="collapse1" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild1"> I was
						presented with this question in an end of module open book exam
						today and found myself lost. i was reading Head first Java and
						both definitions seemed to be exactly the same. i was just
						wondering what the MAIN difference was for my own piece of mind. i
						know there are a number of similar questions to this but, none i
						have seen which provide a definitive answer. Thanks, Darren </a>
				</h4>

			</div>
			
			<div id="collapseChild1" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-thumbs-up"></i> Answer Accepted</h3>
				<h4 class="panel-title">
					<a class="collapse2" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild2">
						Inheritance is when a 'class' derives from an existing 'class'. So
						if you have a Person class, then you have a Student class that
						extends Person, Student inherits all the things that Person has.
						There are some details around the access modifiers you put on the
						fields/methods in Person, but that's the basic idea.For example,
						if you have a private field on Person, Student won't see it
						because its private, and private fields are not visible to
						subclasses. Polymorphism deals with how the program decides which
						methods it should use, depending on what type of thing it has.If
						you have a Person, which has a read method, and you have a Student
						which extends Person, which has its own implementation of read,
						which method gets called is determined for you by the runtime,
						depending if you have a Person or a Student. It gets a bit tricky,
						but if you do something likePerson p = new Student();p.read(); the
						read method on Student gets called.Thats the polymorphism in
						action. You can do that assignment because a Student is a Person,
						but the runtime is smart enough to know that the actual type of p
						is Student. Note that details differ among languages.You can do
						inheritance in javascript for example, but its completely
						different than the way it works in Java. </a>
				</h4>

			</div>
			<div id="collapseChild2" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-check"></i> Answer</h3>
				<h4 class="panel-title">
					<a class="collapse3" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild3">
						Polymorphism: The ability to treat objects of different types in a
						similar manner. Example: Giraffe and Crocodile are both Animals,
						and animals can Move. If you have an instance of an Animal then
						you can call Move without knowing or caring what type of animal it
						is. Inheritance: This is one way of achieving both Polymorphism
						and code reuse at the same time. Other forms of polymorphism:There
						are other way of achieving polymorphism, such as interfaces, which
						provide only polymorphism but no code reuse (sometimes the code is
						quite different, such as Move for a Snake would be quite different
						from Move for a Dog, in which case an Interface would be the
						better polymorphic choice in this case.In other dynamic languages
						polymorphism can be achieved with Duck Typing, which is the
						classes don't even need to share the same base class or interface,
						they just need a method with the same name.Or even more dynamic
						like Javascript, you don't even need classes at all, just an
						object with the same method name can be used polymorphically. </a>
				</h4>

			</div>
			<div id="collapseChild3" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-question-sign"></i> Question</h3>
				<h4 class="panel-title">
					<a class="collapse4" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild4">I found
						out that the above piece of code is perfectly legal in Java. I
						have the following questions. ThanksAdded one more question
						regarding Abstract method classes.<br/>
						<em>public class TestClass{public
						static void main(String[] args) {TestClass t = new TestClass();}
						private static void testMethod(){abstract class TestMethod{int
						a;int b;int c;abstract void implementMe();} class DummyClass
						extends TestMethod{void implementMe(){}}DummyClass dummy = new
						DummyClass();}}</em> </a>
				</h4>

			</div>
			<div id="collapseChild4" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-question-sign"></i> Question</h3>
				<h4 class="panel-title">
					<a class="collapse5" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild5">In java
						it's a bit difficult to implement a deep object copy function.
						What steps you take to ensure the original object and the cloned
						one share no reference? </a>
				</h4>

			</div>
			<div id="collapseChild5" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-check"></i> Answer</h3>
				<h4 class="panel-title">
					<a class="collapse6" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild6"> You can
						make a deep copy serialization without creating some files. Copy:
						Restore: <br/>
						<em>
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new
						ObjectOutputStream(bos);oos.writeObject(object);oos.flush();
						oos.close();bos.close();byte[] byteData = bos.toByteArray();;
						ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
						(Object) object = (Object) new
						ObjectInputStream(bais).readObject();</em> </a>
				</h4>

			</div>
			<div id="collapseChild6" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
			<h3><i class="glyphicon glyphicon-check"></i> Answer</h3>
				<h4 class="panel-title">
					<a class="collapse7" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild7">Java has the ability to create classes at runtime.
						 These classes are known as Synthetic Classes or Dynamic Proxies. See for more information. 
						 Other open-source libraries, such as and also allow you to generate synthetic classes, 
						 and are more powerful than the libraries provided with the JRE. Synthetic classes are used by AOP (Aspect Oriented Programming)
						 libraries such as Spring AOP and AspectJ, as well as ORM libraries such as Hibernate.  </a>
				</h4>

			</div>
			<div id="collapseChild7" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-thumbs-up"></i> Answer Accepted</h3>
				<h4 class="panel-title">
					<a class="collapse8" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild8">A safe way
						is to serialize the object, then deserialize. This ensures
						everything is a brand new reference.about how to do this
						efficiently. Caveats: It's possible for classes to override
						serialization such that new instances are created, e.g. for
						singletons.Also this of course doesn't work if your classes aren't
						Serializable. </a>
				</h4>

			</div>
			<div id="collapseChild8" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-check"></i> Answer</h3>
				<h4 class="panel-title">
					<a class="collapse9" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild9"> comment
						this code <br/>
						<em>/*if (savedinstancestate == null) {
						getsupportfragmentmanager().begintransaction() .add(r.id.container
						new placeholderfragment()) .commit(); }*/ </em></a>
				</h4>

			</div>
			<div id="collapseChild9" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3><i class="glyphicon glyphicon-question-sign"></i> Question</h3>
				<h4 class="panel-title">
					<a class="collapse10" data-toggle="collapse"
						data-parent="#accordion" href="#collapseChild10"> in a
						class i can have as many constructors as i want with different
						argument types. i made all the constructors as private it didn't
						give any error because my implicit default constructor was public
						but when i declared my implicit default constructor as private
						then its showing an error while extending the class. why? this
						works fine this can not be inherited<br/><em> public class demo4 { private
						string name; private int age; private double sal; private
						demo4(string name int age) { this.name=name; this.age=age; }
						demo4(string name) { this.name=name; } demo4() { this(\"unknown\"
						20); this.sal=2000; } void show(){
						system.out.println(\"name\"+name); system.out.println(\"age:
						\"+age); } } public class demo4 { private string name; private int
						age; private double sal; private demo4(string name int age) {
						this.name=name; this.age=age; } demo4(string name) {
						this.name=name; } private demo4() { this(\"unknown\" 20);
						this.sal=2000; } void show() { system.out.println(\"name\"+name);
						system.out.println(\"age: \"+age); } }</em></a>
				</h4>

			</div>
			<div id="collapseChild10" class="panel-collapse collapse">
				<div class="panel-body"></div>
			</div>
		</div>
	</div>
</body>
</html>