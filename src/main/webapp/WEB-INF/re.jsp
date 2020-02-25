<html>
<head>
    <title>spring boot form submit example</title>
</head>
<body>
<h2>Employee Details</h2>
<form method="post" action="/restaurant/addRestaurant">   // saveDetails url mapping in EmployeeController
    Enter Employee Name : <input type="text" name="employeeName"/>
    Enter Employee Email Address: <input type="email" name="employeeEmail">
    <input type="submit" value="Submit">
</form>
</body>
</html>