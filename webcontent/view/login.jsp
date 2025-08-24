<!DOCTYPE html>
<html>
<head>
    <title>Bank Login</title>
</head>
<body>
    <h2>Login to your account</h2>
    <p style="color:red;">${errorMessage}</p>
    <form action="LoginServlet" method="post">
        Username: <input type="text" name="username"><br/>
        Password: <input type="password" name="password"><br/>
        <br/>
        Login As: 
        <select name="role">
            <option value="Customer">Customer</option>
            <option value="Admin">Admin</option>
        </select>
        <br/><br/>
        <input type="submit" value="Login">
    </form>
</body>
</html>