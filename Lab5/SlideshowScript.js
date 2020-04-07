var imageSrc = ["img1.jpg", "img2.jpg", "img3.jpg", "img4.jpg", "img5.jpg"]

var time = document.getElementById("time").value;
var timeValue = time;
var imageIdx = 0;
var isPlaying = false;

function timerStep() {
    // Display the result in the element with id="timer"
    document.getElementById("timer").innerHTML = timeValue + "s";

    timeValue -= 1;
    if (timeValue < 0) {
        timeValue = time;

        imageIdx++;
        if(imageIdx >= imageSrc.length) {
            if(document.getElementById("replay").checked) {
                imageIdx=0;
                loadImage();
            } else {
                stopTimer();
            }
        } else {
            loadImage();
        }
    }
}

function loadImage() {
    document.getElementById("image").src = imageSrc[imageIdx];
}

function startTimer() {
    isPlaying = true;
    document.getElementById("button").value = "Stop";
    time = document.getElementById("time").value;
    timeValue = time;
    timer = setInterval(timerStep, 1000)
}

function stopTimer() {
    imageIdx = 0;
    loadImage();
    isPlaying = false;
    document.getElementById("button").value = "Play";
    document.getElementById("timer").innerHTML = "Slideshow stopped";
    clearInterval(timer);
}

function toggleSlideshow() {
    isPlaying?stopTimer():startTimer();
}