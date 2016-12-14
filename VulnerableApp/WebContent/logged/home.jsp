<!DOCTYPE html>
<html>
<head>
<%@page import="org.owasp.esapi.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Vulnerable App</title>
<link href="/style/css/bootstrap.min.css" rel="stylesheet">
<script src="/js/jquery.min.js"></script>
<style>
html, body {
	height: 100%;
	overflow: hidden;
}
.navbar-brand {
	cursor: default;
	color: #FFF;
    background-color: #18bc9c;;
}
.navbar-brand:hover {
    color: #FFF;
    background-color: #18bc9c;;
    cursor: default;
}
#wrap {
	min-height: 100%;
	height: auto;
	margin: 0 auto -80px;
	padding: 0 0 80px;
}

hr {
	border-color: #000;
}

.jumbotron {
	padding-bottom: 0px;
	margin-bottom: 0px;
	background-color: #FFF;
}

.topLinks {
	cursor: pointer;
}

#reflected {
	color: white;
}
</style>
</head>
<body style="">

	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span> <span class="icon-bar"></span>
				</button>

				<a class="navbar-brand" href="#">Vulnerable App</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">

					<li><a class="topLink"
						href="home.jsp?user=User&url=/logged/home.html%23profile">Home</a>
					</li>
					<li class="liLink active"><a class="link topLink"
						href="home.html#documents">Documents</a></li>
					<li class="liLink "><a class="link topLink" href="home.html#posts">Posts</a>
					</li>
					<li class="liLink"><a class="link topLink" href="home.html#profile">Profile</a>
					</li>
					<li class="liLink"><a class="link topLink" href="home.html#feedback">Website
							Feedback</a></li>
				</ul>
					<div class="btn-group pull-right">
					<button class="btn btn-info" id="logoutButton"
						style="margin-top: 7px;">
						<a class="glyphicon glyphicon-off white"></a>
					</button>
				</div>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<div id="wrap">
		<div class="jumbotron">
			<div class="container">
				<h1>
					Welcome
					<%= request.getParameter("user") %>!
				</h1>
				<p>
					Click <a href="<%= request.getParameter("url")%>">here</a> to start
				</p>
				<div id="error"></div>
			</div>
		</div>
	</div>
	<script>
    $('#logoutButton').click(function(e) {
        e.preventDefault();
        window.location = '/auth';
    });
	</script>
</body>
</html>