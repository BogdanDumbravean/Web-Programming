
<?php

    $user = 'root';
    $password = '';
    $db = 'carbusiness';

    $conn = new mysqli('localhost', $user, $password, $db) or die("Unable to connect");

    function listCars() {
        global $conn;

        $sql = "SELECT * FROM cars";
        if($_POST["category"] != "All") {
            $sql .= " WHERE ". $_POST["category"] ."='" . $_POST["input"] . "'";
        }
        
        $result = $conn->query($sql);

        echo "<table><tr><th>ID</th><th>Model</th><th>Engine Power</th><th>Fuel</th><th>Price</th><th>Color</th><th>Year</th></tr>";

        if (is_object($result) && $result->num_rows > 0) {
            // output data of each row
            while($row = $result->fetch_assoc()) {

                echo "<tr><td>" . $row["ID"]. "</td><td>" 
                    . $row["Model"]. "</td><td>" 
                    . $row["EnginePower"]. "</td><td>" 
                    . $row["Fuel"]. "</td><td>" 
                    . $row["Price"]. "</td><td>" 
                    . $row["Color"]. "</td><td>" 
                    . $row["Year"]. "</td></tr>";
            }
        } else {
            echo "0 results";
        }
        echo "</table>";
    }
    
    listCars();

    $conn->close();

?>
