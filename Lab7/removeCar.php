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

        $sql = "DELETE FROM Cars WHERE ID=".$_POST["ID"];

        $result = $conn->query($sql);
        $conn->close();

        echo "The car was removed successfuly!";

        ?>

    </body>
</html>