# System description

This is a centralized mutual exclusion system. The system comprises three components.
1. A Resource Server, which contains a "resource" that only one process can access at a time.
2. A Coordinator, which controls access to the resource on a **first-come-first-served** basis.
3. A set of Client processes that may request access to the resource.

# Notes

* This project may or may not receive updates, to better reflect my own skills in programming.
* There are also some ideas I may have to improve how the project works overall (possibly by utilizing thread control instead of **while(true)** loops to preserve CPU usage). **while(true)** loops were used as I was not familiar with threads enough at the time.
* This project was made by me at the university as part of a module's project. Modules were basically subjects we had to learn, in case you're not familiar with the term.
* The system description used here is (mostly) taken from the description provided to us by the university.
