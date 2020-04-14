$(document).ready(function() {
    var emptyCell="16";
    pics = new Array(17);
    
    for(i=1; i<17; i++) {
        gasit = true;
        while (gasit==true) {
            x = 1 + Math.floor(Math.random() * 1000) % 16;
            gasit = false;
            for (j=1; j<i; j++)
                if (pics[j]==x) gasit = true;
        }
        pics[i] = x;
        if (x==16) emptyCell = i;
    }

    var cell;
    for(i=1; i<17; i++) {
        img = "<img src=";
        if (i!=emptyCell)
            img += "puzzleImage/image_part_"+pics[i]+".jpg>";
        else
            img += "puzzleImage/empty.jpg>";

        cell = '#' + i.toString()
        $(cell).append(img);
    }

    $('td').click(function(event) {
        cellID = parseInt(this.id);
        if (cellID == emptyCell) return;

        rest = cellID % 4;
        topPos = (cellID > 4) ? cellID-4 : -1;
        bottomPos = (cellID < 13) ? cellID+4 : -1;
        leftPos = (rest != 1) ? cellID-1 : -1;
        rightPos = (rest > 0) ? cellID+1 : -1;
        
        if (emptyCell!=topPos && emptyCell!=bottomPos && emptyCell!=leftPos && emptyCell!=rightPos)
            return; 

        cell1 = $("#" + emptyCell);
        img1 = $("#" + emptyCell + " img");
        img = $("#" + cellID + " img");
        $(this).empty();
        cell1.empty();
        
        $(this).append(img1);
        cell1.append(img);
        emptyCell = cellID;
    });
});