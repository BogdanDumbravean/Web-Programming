$(function(){

    // Variables
    const quizContainer = $('#quiz');
    const resultsContainer = $('#results');
    const submitButton = $('#submit');
    const finishButton = $('#finish');
    var myQuestions = [];

    // Pagination
    const previousButton = $("#previous");
    const nextButton = $("#next");
    var slides = [];
    var currentSlide = 0;
    var pageQuestionNr = 1;

    // Functions
    function getQuiz(callbackFunction) {
        //console.log(questionCount);
        $.getJSON(
            "QuizController",
            { action: 'getQuiz'},
            callbackFunction
        );
    }
    function getQuestionsPerPage(callbackFunction) {
        //console.log(questionCount);
        $.getJSON(
            "QuizController",
            { action: 'getQuestionsPerPage'},
            callbackFunction
        );
    }
    function finishQuiz() {
        $.post(
            "QuizController2",
            {},
            function(){}
        );
    }

    function checkHighscore(score, callbackFunction) {
        $.getJSON(
            "QuizController",
            { action: 'highscore', score: score},
            callbackFunction
        );
    }

    function populateQuestions() {
        // empty previous stuff
        resetQuiz();

        getQuiz(function(questions) {
            //console.log('pageQuestions:',pageQuestions);
            console.log('questions:',questions);
            //console.log("pageQuestions:",pageQuestions);
            questions.forEach(function(q){
                let answers = {};
                // Index for the answers
                let idx = "a".charCodeAt(0);
                let correctAnswer = "a";

                q.answers.forEach(function (a) {
                    answers[String.fromCharCode(idx)] = a.content;
                    if(a.isCorrect) {
                        correctAnswer = String.fromCharCode(idx);
                    }
                    idx++;
                });
                // Add question to quiz questions
                myQuestions.push({
                    question: q.content,
                    answers: answers,
                    correctAnswer: correctAnswer//questions[name].correctAnswer
                });
                //console.log(myQuestions);
            });
            getQuestionsPerPage(function (nr) {
                pageQuestionNr = nr;
                startQuiz();
            });
        } );
    }

    function resetQuiz() {
        // Empty previous information
        slides = [];
        currentSlide = 0;
        myQuestions = [];
        quizContainer.empty();
        resultsContainer.empty();
        previousButton.css('display', 'none');
        nextButton.css('display', 'none');
    }

    function startQuiz(){
        // variable to store the HTML output
        let output = [];

        var pageQCount = 0;
        // for each question...
        myQuestions.forEach((currentQuestion, questionNumber) => {
            // variable to store the list of possible answers
            let answers = [];
            // and for each available answer...
            for (letter in currentQuestion.answers) {
                // ...add an HTML radio button
                answers.push(
                    `<label>
                    <input type="radio" name="question${questionNumber}" value="${letter}">
                    ${letter} :
                    ${currentQuestion.answers[letter]}
                    </label>`
                );
            }

            // add this question and its answers to the output
            if (pageQCount === 0) {
                output.push(`<div class="slide">`)
            }
            output.push(
                `<div class="question"> ${currentQuestion.question} </div>
                <div class="answers"> ${answers.join("")} </div>`
            );
            pageQCount++;
            if(pageQCount === pageQuestionNr || questionNumber == myQuestions.length - 1) {
                output.push(`</div>`)
                pageQCount = 0;
            }
        });

        // finally combine our output list into one string of HTML and put it on the page
        quizContainer.html(output.join(''));
        // populate slides
        $(".slide").each(function () {
            slides.push($(this));
        });
        // Show the first slide
        showSlide(currentSlide);
    }

    function showSlide(n) {
        slides[currentSlide].removeClass('active-slide');
        slides[n].addClass('active-slide');
        currentSlide = n;
        if(currentSlide === 0){
            previousButton.css('display', 'none');
        }
        else{
            previousButton.css('display', 'inline-block');
        }
        if(currentSlide === slides.length-1){
            nextButton.css('display', 'none');
            submitButton.css('display', 'inline-block');
        }
        else{
            nextButton.css('display', 'inline-block');
            submitButton.css('display', 'none');
        }
    }

    function showNextSlide() {
        showSlide(currentSlide + 1);
    }

    function showPreviousSlide() {
        showSlide(currentSlide - 1);
    }

    function showResults(){
        // gather answer containers from our quiz
        let answerContainers = [];
        $(".answers").each(function () {
            answerContainers.push($(this));
        });
        // keep track of user's answers
        let numCorrect = 0;

        // for each question...
        myQuestions.forEach( (currentQuestion, questionNumber) => {
            // find selected answer
            const answerContainer = answerContainers[questionNumber];
            const selector = `input[name=question${questionNumber}]:checked`;
            const userAnswer = $(selector).val();

            // if answer is correct
            if(userAnswer === currentQuestion.correctAnswer){
                // add to the number of correct answers
                numCorrect++;
                // color the answers green
                answerContainers[questionNumber].css('color', 'lightgreen');
            }
            // if answer is wrong or blank
            else{
                // color the answers red
                answerContainers[questionNumber].css('color', 'red');
            }
        });
        // show number of correct answers out of total
        checkHighscore(numCorrect, function(highscore) {
            resultsContainer.html(`${numCorrect} out of ${myQuestions.length} (Highscore: ${highscore})`);
        });
        finishButton.css('display', 'inline-block');
    }

    populateQuestions();

    // Events
    $('#questionNr').change(populateQuestions);
    $('#start').click(startQuiz);
    finishButton.click(finishQuiz);
    submitButton.click(showResults);
    previousButton.click(showPreviousSlide);
    nextButton.click(showNextSlide);
});