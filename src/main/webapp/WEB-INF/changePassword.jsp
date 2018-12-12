<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>QUANT-Question Answering Curator</title>
    <link rel="stylesheet" href="webjars/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="webjars/jquery/3.3.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>

</head>
<body class="text-center">
<form method="POST" action="/changePassword" style="max-width: 300px">
    <h1 class="h3 mb-3 font-weight-normal">ChangePassword of user ${logedInAs}</h1>

    <label for="oldpassword" class="sr-only">Password</label>
    <input type="password" name="old-password" id="oldpassword" class="form-control" placeholder="Old Password" required>
    <label for="newpassword" class="sr-only">Password</label>
    <input type="password" name="new-password" id="newpassword" class="form-control" placeholder="New Password" required>
    <label for="confirm-password" class="sr-only">Confirm Password</label>
    <input type="password" name="confirm-password" id="confirm-password" class="form-control" placeholder="Confirm new Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Change</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
</form>
<!-- Plugin JavaScript -->
</body>
<!-- Bootstrap core JavaScript -->

</html>