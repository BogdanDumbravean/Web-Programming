<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <nav>
        <a href="listCars.html">View Cars</a> |
        <a href="addCar.html">Add Car</a> |
        <a href="updateCar.html">Update Car</a> |
        <a href="removeCar.html">Remove Car</a>
        </nav>

        <hr>

        <?php
            
        $user = 'root';
        $password = '';
        $db = 'carbusiness';

        $conn = new mysqli('localhost', $user, $password, $db) or die("Unable to connect");

        $sql = "INSERT INTO Cars (Model, EnginePower, Fuel, Price, Color, Year)";
        $sql .= "VALUES ('".$_POST["Model"]."',".$_POST["EnginePower"].",'".$_POST["Fuel"]."',".$_POST["Price"].",'".$_POST["Color"]."',".$_POST["Year"].")";

        $result = $conn->query($sql);
        $conn->close();

        echo "The car was added successfuly!";

        ?>

    </body>
</html>